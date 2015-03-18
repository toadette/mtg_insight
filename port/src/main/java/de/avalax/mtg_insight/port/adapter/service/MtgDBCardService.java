package de.avalax.mtg_insight.port.adapter.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;

public class MtgDBCardService implements CardService {

    private String host = "http://api.mtgdb.info/cards/";

    @Override
    public Card cardFromCardname(String cardname) throws IOException, ParseException {
        String encodedCardname = URLEncoder.encode("Narset, Enlightened Master", "UTF-8");
        URL cardUrl=new URL(host+encodedCardname);
        InputStreamReader inputStreamReader = new InputStreamReader(cardUrl.openStream());
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(inputStreamReader);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Card card=new Creature(jsonObject.get("name").toString(),null,null,null);
        return  card;
    }
}
