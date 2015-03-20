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
import java.util.Arrays;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class MtgDBCardService implements CardService {

    public static final String ENCODING = "UTF-8";
    private final String host = "http://api.mtgdb.info/cards/";

    @Override
    public Card cardFromCardname(String cardname) throws IOException, ParseException {
        InputStreamReader inputStreamReader = new InputStreamReader(getCardUrl(cardname).openStream());
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(inputStreamReader);
        JSONObject cardFromJson = (JSONObject) jsonArray.get(0);
        Card card = new Creature(cardFromJson.get("name").toString(), null, null, getConvertedManaCost(cardFromJson));
        cardFromJson.get("description");
        cardFromJson.get("type");
        inputStreamReader.close();
        return card;
    }

    private List<ManaCost> getConvertedManaCost(JSONObject cardFromJson) {
        List<ManaCost> convertedManaCost = new ArrayList<>();
        String manaCost = cardFromJson.get("manaCost").toString();
        char[] manaCostArray = manaCost.toCharArray();
        if (String.valueOf(manaCostArray[0]).equals("{")) {
            List<Mana> manaList = new ArrayList<>();
            for (int i = 1; i < manaCostArray.length; i++) {
                String entry = String.valueOf(manaCostArray[i]);
                if (!entry.equals("/") && !entry.equals("{") && !entry.equals("}")) {
                    manaList.addAll(getMana(entry));
                }
                if(entry.equals("}")){
                    convertedManaCost.add(new ManaCost(manaList));
                    manaList=new ArrayList<>();
                }
            }
        } else {
            for (int i = 0; i < manaCostArray.length; i++) {
                List<Mana> manaList = getMana(String.valueOf(manaCostArray[i]));
                if (manaList.size() > 1) {
                    for (Mana mana : manaList) {
                        convertedManaCost.add(new ManaCost(Arrays.asList(mana)));
                    }
                } else {
                    convertedManaCost.add(new ManaCost(manaList));
                }

            }
        }

        System.out.println("Manacost: " + manaCost);
        return convertedManaCost;
    }

    private List<Mana> handleMultiColored(char[] manaCostArray, int i) {
        int count = i + 1;
        List<Mana> manaList = getMana(String.valueOf(manaCostArray[count]));
        ++count;
        char currentMana = manaCostArray[count];
        if (String.valueOf(currentMana).equals("/")) {
            ++count;
            manaList.addAll(getMana(String.valueOf(currentMana)));
        }
        return manaList;
    }

    private List<Mana> getMana(String entry) {
        if (entry.equals("U")) {
            return Arrays.asList(Mana.BLUE);
        }
        if (entry.equals("R")) {
            return Arrays.asList(Mana.RED);
        }
        if (entry.equals("W")) {
            return Arrays.asList(Mana.WHITE);
        }
        if (entry.equals("B")) {
            return Arrays.asList(Mana.BLACK);
        }
        if (entry.equals("G")) {
            return Arrays.asList(Mana.GREEN);
        }
        List<Mana> manaList = new ArrayList<>();
        int countOfColorlessMana = Integer.parseInt(entry);
        for (int i = 0; i < countOfColorlessMana; i++) {
            manaList.add(Mana.COLORLESS);
        }
        return manaList;
        //TODO:phyrexian parsen: 2 Leben oder Farbe-> nur Farbe wichtig
    }

    private URL getCardUrl(String cardname) throws MalformedURLException, UnsupportedEncodingException {
        return new URL(host + URLEncoder.encode(cardname, ENCODING));
    }
}
