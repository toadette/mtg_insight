package de.avalax.mtg_insight.port.adapter.service.card;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaCostToken;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class MtgDBCardService implements CardService {

    public static final String ENCODING = "UTF-8";
    private final String host = "http://api.mtgdb.info/cards/";
    private ManaTokenizer manaTokenizer;
    private ColorMatcher colorMatcher;
    private AbilityTokenizer abilityTokenizer;


    public MtgDBCardService(ManaTokenizer manaTokenizer, ColorMatcher colorMatcher, AbilityTokenizer abilityTokenizer) {
        this.manaTokenizer = manaTokenizer;
        this.colorMatcher = colorMatcher;
        this.abilityTokenizer = abilityTokenizer;
    }

    @Override
    public Card cardFromCardname(String cardname) throws CardNotFoundException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getCardUrl(cardname).openStream());
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(inputStreamReader);
            if (jsonArray == null | jsonArray.size() == 0) {
                throw new CardNotFoundException(new Exception("Card not found"));
            }
            JSONObject cardFromJson = (JSONObject) jsonArray.get(0);
            String power=cardFromJson.get("power").toString();;
            String toughness=cardFromJson.get("toughness").toString();;
            String loyalty=cardFromJson.get("loyalty").toString();;
            String type = cardFromJson.get("type").toString();
            String name = cardFromJson.get("name").toString();
            String description= cardFromJson.get("description").toString();
            List<Color> colorOfCard = getColorOfCard(cardFromJson);
            ConvertedManaCost convertedManaCost = getConvertedManaCost(cardFromJson);
            List<Ability> abilities= getAbilities(description);
            Card card = new CardCreator().createCardFromType(type, name, colorOfCard, convertedManaCost,power,toughness,loyalty, abilities);
            inputStreamReader.close();
            return card;
        } catch (Exception e) {
            throw new CardNotFoundException(new Exception("Card not found"));
        }
    }

    private List<Ability> getAbilities(String description) {
        return abilityTokenizer.getAbilitiesFromDescription(description);
    }

    private List<Color> getColorOfCard(JSONObject cardFromJson) {
        JSONArray colorArray = (JSONArray) cardFromJson.get("colors");
        return colorMatcher.getColorFromArray(colorArray.toArray());
    }

    private ConvertedManaCost getConvertedManaCost(JSONObject cardFromJson) {
        List<ManaCost> convertedManaCost = new ArrayList<>();
        for (ManaCostToken token : manaTokenizer.get(cardFromJson.get("manaCost").toString())) {
            convertedManaCost.addAll(token.manaCost());
        }
        return new ConvertedManaCost(cardFromJson.get("manaCost").toString(), convertedManaCost);
    }

    private URL getCardUrl(String cardname) throws MalformedURLException, UnsupportedEncodingException {
        return new URL(host + URLEncoder.encode(cardname, ENCODING));
    }

}
