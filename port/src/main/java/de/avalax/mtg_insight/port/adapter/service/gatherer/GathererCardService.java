package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.card.CardCreator;
import de.avalax.mtg_insight.port.adapter.service.card.CardService;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaCostToken;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class GathererCardService implements CardService {

    public static final String ENCODING = "UTF-8";
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
            Document doc;
            doc = Jsoup.connect(host + getCardNameForSearch(cardname)).get();
            String type = doc.body().getElementById(GathererConstants.TYPE).children().get(1).text();
            String name = doc.body().getElementById(GathererConstants.NAME).children().get(1).text();
            String description = doc.body().getElementById(GathererConstants.DESCRIPTION_TEXT).children().get(1).toString();
            Element manaRow = doc.body().getElementById(GathererConstants.MANA).children().get(1);
            List<String> mana = getManaFromElement(manaRow);
            GathererHelper gathererHelper = new GathererHelper(abilityTokenizer, colorMatcher, manaTokenizer);
            return gathererHelper.getCard(type, name, description, mana);
        } catch (Exception e) {
            throw new CardNotFoundException(new Exception("Card not found"));
        }
    }

    private List<String> getManaFromElement(Element manaRow) {
        List<String> manaList=new ArrayList<>();
        for (int i = 0; i < manaRow.children().size(); i++) {
            String alt = manaRow.children().get(i).attr("alt");
            manaList.add(alt);
            System.out.println(alt);
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
