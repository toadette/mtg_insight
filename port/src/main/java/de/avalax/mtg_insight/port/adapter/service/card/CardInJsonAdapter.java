package de.avalax.mtg_insight.port.adapter.service.card;

import com.google.gson.Gson;

import de.avalax.mtg_insight.domain.model.card.Card;

public class CardInJsonAdapter {

    private final Gson gson;

    public CardInJsonAdapter() {
        gson = new Gson();
    }

    public String fromCard(Card card) {
        return jsonFromCard(card);
    }

    private String jsonFromCard(Card card) {
        Object[] json = {card.getClass().getSimpleName(), card};
        return gson.toJson(json);
    }
}
