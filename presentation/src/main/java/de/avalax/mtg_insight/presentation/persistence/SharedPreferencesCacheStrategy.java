package de.avalax.mtg_insight.presentation.persistence;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.domain.model.card.Artifact;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Enchantment;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Sorcery;

public class SharedPreferencesCacheStrategy implements CacheStrategy {
    private final Gson gson;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesCacheStrategy(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        gson = new Gson();
    }

    @Override
    public void put(String cardname, Card card) {
        if (card.getClass().getSimpleName().equals("GenericCard")) {
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(cardname, jsonFromCard(card));
        if (!editor.commit()) {
            Log.d("unable to cache", cardname);
        }
    }

    private String jsonFromCard(Card card) {
        Object[] json = {card.getClass().getSimpleName(), card};
        return gson.toJson(json);
    }

    @Override
    public Card get(String cardname) {
        String jsonCard = sharedPreferences.getString(cardname, null);
        if (jsonCard == null) {
            Log.d("card not in cache", cardname);
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
            case "Artifact":
                card = gson.fromJson(element.get(1), Artifact.class);
                break;
            case "Enchantment":
                card = gson.fromJson(element.get(1), Enchantment.class);
                break;
            case "Instant":
                card = gson.fromJson(element.get(1), Instant.class);
                break;
            case "Sorcery":
                card = gson.fromJson(element.get(1), Sorcery.class);
                break;
            default:
                card = gson.fromJson(element.get(1), GenericCard.class);
        }
        return card;
    }
}
