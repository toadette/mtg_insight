package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.card.CardService;
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
    public Card cardFromCardname(String cardname) throws CardNotFoundException {
        try {
            Document doc = Jsoup.connect(host + getCardNameForSearch(cardname)).get();
            GathererHelper gathererHelper = new GathererHelper(abilityTokenizer, colorMatcher, manaTokenizer);
            return gathererHelper.getCard(getType(doc), getName(doc), getDescription(doc), getManaFromElement(getMana(doc)), getPowerToughness(doc));
        } catch (Exception e) {
            throw new CardNotFoundException(new Exception("Card not found"));
        }
    }

    private Element getElementById(Document doc, String id) {
        return doc.body().getElementById(id).children().get(1);
    }

    private Element getMana(Document doc) {
        return getElementById(doc, GathererConstants.MANA);
    }

    private String getPowerToughness(Document doc) {
        return getElementById(doc, GathererConstants.POWER_TOUGHNESS).text();
    }

    private String getDescription(Document doc) {
        return getElementById(doc, GathererConstants.DESCRIPTION_TEXT).toString();
    }

    private String getName(Document doc) {
        return getElementById(doc, GathererConstants.NAME).text();
    }

    private String getType(Document doc) {
        return getElementById(doc, GathererConstants.TYPE).text();
    }

    private List<String> getManaFromElement(Element manaRow) {
        List<String> manaList = new ArrayList<>();
        for (int i = 0; i < manaRow.children().size(); i++) {
            manaList.add(manaRow.children().get(i).attr("alt"));
        }
        return manaList;
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
