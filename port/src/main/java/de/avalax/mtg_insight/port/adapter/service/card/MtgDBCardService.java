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


    public MtgDBCardService(ManaTokenizer manaTokenizer, ColorMatcher colorMatcher) {
        this.manaTokenizer = manaTokenizer;
        this.colorMatcher = colorMatcher;
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
            Card card = new CardBuilder().createCardFromType(cardFromJson.get("type").toString(),cardFromJson.get("name").toString(),getColorOfCard(cardFromJson),getConvertedManaCost(cardFromJson));
//TODO: parse Description?    cardFromJson.get("description");
            inputStreamReader.close();
            return card;
        } catch (Exception e) {
            throw new CardNotFoundException(new Exception("Card not found"));
        }
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
