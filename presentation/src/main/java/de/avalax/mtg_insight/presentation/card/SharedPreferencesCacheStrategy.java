package de.avalax.mtg_insight.presentation.card;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;

public class SharedPreferencesCacheStrategy implements CacheStrategy {

    private final Gson gson;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesCacheStrategy(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        gson = new Gson();
    }

    @Override
    public void put(String cardname, Card card) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cardname, jsonFromCard(card));
        editor.apply();
    }

    private String jsonFromCard(Card card) {
        switch (card.getClass().getSimpleName()) {
            case "Creature":
                return jsonFromCard((Creature) card);
            default:
                Object[] json = {card.getClass().getSimpleName(), card};
                return gson.toJson(json);
        }
    }

    private String jsonFromCard(Creature creature) {
        Object[] json = {creature.getClass().getSimpleName(), creature};
        return gson.toJson(json);
    }

    @Override
    public Card get(String cardname) {
        long startTime = System.currentTimeMillis();
        String jsonCard = sharedPreferences.getString(cardname, null);
        if (jsonCard == null) {
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000;
            Log.d(cardname + " missed", String.valueOf(duration));
            return null;
        }
        JsonArray element = gson.fromJson(jsonCard, JsonArray.class);
        String cardType = gson.fromJson(element.get(0), String.class);
        Card card;
        switch (cardType) {
            case "Creature":
                card = gson.fromJson(element.get(1), Creature.class);
                break;
            case "Planeswalker":
                card = gson.fromJson(element.get(1), Planeswalker.class);
                break;
            case "Land":
                card = gson.fromJson(element.get(1), Land.class);
                break;
            default:
                card = gson.fromJson(element.get(1), GenericCard.class);
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        Log.d(cardname + " hit", String.valueOf(duration));
        return card;
    }
}
