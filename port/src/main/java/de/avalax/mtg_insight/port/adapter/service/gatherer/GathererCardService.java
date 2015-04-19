package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.exception.CardCorruptedException;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class GathererCardService implements CardService {

    private final String host = "http://gatherer.wizards.com/Pages/Search/Default.aspx?name=";
    private final ManaTokenizer manaTokenizer;
    private final ColorMatcher colorMatcher;
    private final AbilityTokenizer abilityTokenizer;

    public GathererCardService(ManaTokenizer manaTokenizer, ColorMatcher colorMatcher, AbilityTokenizer abilityTokenizer) {

        this.manaTokenizer = manaTokenizer;
        this.colorMatcher = colorMatcher;
        this.abilityTokenizer = abilityTokenizer;
    }

    @Override
    public Card cardFromCardname(String cardname) throws CardNotFoundException, CardCorruptedException {
        try {
            Document doc = Jsoup.connect(host + getCardNameForSearch(cardname)).get();
            GathererHelper gathererHelper = new GathererHelper(abilityTokenizer, colorMatcher, manaTokenizer);
            return gathererHelper.getCard(getType(doc), getName(doc), getDescription(doc), getManaFromElement(getMana(doc)), getPowerToughness(doc));
        } catch (CardCorruptedException e) {
            throw new CardCorruptedException(e);
        } catch (Exception e) {
            throw new CardNotFoundException(e);
        }
    }

    private Element getElementById(Document doc, String id) {
        if (doc.body().getElementById(id) != null && doc.body().getElementById(id).children() != null && doc.body().getElementById(id).children().size() > 0) {
            return doc.body().getElementById(id).children().get(1);
        }
        return null;
    }

    private Element getMana(Document doc) {
        return getElementById(doc, GathererConstants.MANA);
    }

    private String getPowerToughness(Document doc) {
        Element elementById = getElementById(doc, GathererConstants.POWER_TOUGHNESS);
        if (elementById != null) {
            return elementById.text();
        }
        return "";
    }

    private String getDescription(Document doc) {
        Element elementById = getElementById(doc, GathererConstants.DESCRIPTION_TEXT);
        if (elementById != null) {
            return elementById.toString();
        }
        return "";
    }

    private String getName(Document doc) {
        return getElementById(doc, GathererConstants.NAME).text();
    }

    private String getType(Document doc) {
        return getElementById(doc, GathererConstants.TYPE).text();
    }

    private List<String> getManaFromElement(Element manaRow) {
        if (manaRow != null) {
            List<String> manaList = new ArrayList<>();
            for (int i = 0; i < manaRow.children().size(); i++) {
                manaList.add(manaRow.children().get(i).attr("alt"));
            }
            return manaList;
        }
        return Collections.emptyList();
    }

    private String getCardNameForSearch(String cardname) {
        String result = "";
        String[] split = cardname.split(" ");
        for (String str : split) {
            result += "+[";
            result += str;
            result += "]";
        }
        return result;
    }


}
