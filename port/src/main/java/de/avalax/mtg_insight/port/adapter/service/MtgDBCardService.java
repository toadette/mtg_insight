package de.avalax.mtg_insight.port.adapter.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

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
    public Card cardFromCardname(String cardname) throws IOException, ParseException {
        InputStreamReader inputStreamReader = new InputStreamReader(getCardUrl(cardname).openStream());
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(inputStreamReader);
        JSONObject cardFromJson = (JSONObject) jsonArray.get(0);
        Card card = new Creature(cardFromJson.get("name").toString(), null, getColorOfCard(cardFromJson), getConvertedManaCost(cardFromJson));
//TODO: parse CardType        cardFromJson.get("type");
//TODO: parse Description?    cardFromJson.get("description");
        inputStreamReader.close();
        return card;
    }

    private List<Color> getColorOfCard(JSONObject cardFromJson) {
        JSONArray colorArray= (JSONArray) cardFromJson.get("colors");
        return colorMatcher.getColorFromArray(colorArray.toArray());
    }

    private List<ManaCost> getConvertedManaCost(JSONObject cardFromJson) {
        List<ManaCost> convertedManaCost = new ArrayList<>();
        for (ManaCostToken token : manaTokenizer.get(cardFromJson.get("manaCost").toString())) {
            convertedManaCost.addAll(token.manaCost());
        }
        return convertedManaCost;
    }

    private URL getCardUrl(String cardname) throws MalformedURLException, UnsupportedEncodingException {
        return new URL(host + URLEncoder.encode(cardname, ENCODING));
    }

}
