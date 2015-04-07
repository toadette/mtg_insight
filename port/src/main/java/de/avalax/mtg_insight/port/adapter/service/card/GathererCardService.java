package de.avalax.mtg_insight.port.adapter.service.card;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaCostToken;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class GathererCardService implements CardService {

    public static final String ENCODING = "UTF-8";
    private final String host = "http://api.mtgdb.info/cards/";
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
            doc = Jsoup.connect("http://gatherer.wizards.com/Pages/Search/Default.aspx?name=" + getCardNameForSearch(cardname)).get();
//            String power=cardFromJson.get("power").toString();;
//            String toughness=cardFromJson.get("toughness").toString();;
//            String loyalty=cardFromJson.get("loyalty").toString();;
            String type = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_typeRow").children().get(1).text();
            String name = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow").children().get(1).text();
            String description = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow").children().get(1).toString();
            List<Color> colorOfCard = getColorOfCard(doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_manaRow").children().get(1));
            ConvertedManaCost convertedManaCost = getConvertedManaCost(doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_manaRow").children().get(1));
            List<Ability> abilities = getAbilities(description);
            Card card = new CardCreator().createCardFromType(type, name, colorOfCard, convertedManaCost, "0", "0", "0", abilities);
            return card;
        } catch (Exception e) {
            throw new CardNotFoundException(new Exception("Card not found"));
        }
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

    private void test(String url) throws IOException {
        Document doc;
        doc = Jsoup.connect(url).get();
        System.out.println(doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow").children().get(1).text());
        System.out.println(doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_typeRow").children().get(1).text());
        Element manaRow = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_manaRow").children().get(1);
        for (int i = 0; i < manaRow.children().size(); i++) {
            System.out.print(manaRow.children().get(i).attr("alt") + " ");
        }
        System.out.println();
        Element description = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow").children().get(1);
        for (int i = 0; i < description.children().size(); i++) {
            System.out.println(description.children().get(i).text());
        }
        Element powerToughness = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_ptRow");
        if (powerToughness != null) {
            System.out.println(powerToughness.children().get(1).text());
        }
    }

    private List<Ability> getAbilities(String description) {
        return abilityTokenizer.getAbilitiesFromDescription(description);
    }

    private List<Color> getColorOfCard(Element manaRow) {
        HashMap<String, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < manaRow.children().size(); i++) {
            String token = manaRow.children().get(i).attr("alt");
            if (colorMap.get(token) == null && !token.matches("\\d")) {
                if(token.matches(""))
                colorMap.put(token, i);
            }
        }
        return colorMatcher.getColorFromArray(colorMap.keySet().toArray());
    }

    private ConvertedManaCost getConvertedManaCost(Element manaRow) {
        String cmc = new String();
        for (int i = 0; i < manaRow.children().size(); i++) {
            String token = manaRow.children().get(i).attr("alt");
            if (token.matches("\\d")) {
                cmc += token;
            } else {
                cmc += token.substring(0, 1).toUpperCase();
            }
        }
        List<ManaCost> convertedManaCost = new ArrayList<>();
        for (ManaCostToken token : manaTokenizer.get(cmc)) {
            convertedManaCost.addAll(token.manaCost());
        }
        return new ConvertedManaCost(cmc, Collections.EMPTY_LIST);
    }
}
