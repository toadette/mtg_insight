package de.avalax.mtg_insight.port.adapter.service.card;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import de.avalax.mtg_insight.domain.model.card.Artifact;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Enchantment;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Sorcery;

class JsonInCardAdapter {
    private final Gson gson;

    public JsonInCardAdapter() {
        gson = new Gson();
    }

    public Card createFromJson(String jsonCard) {
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
