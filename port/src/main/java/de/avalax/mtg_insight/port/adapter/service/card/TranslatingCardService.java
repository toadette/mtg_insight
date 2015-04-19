package de.avalax.mtg_insight.port.adapter.service.card;

import de.avalax.mtg_insight.domain.model.card.Card;

public class TranslatingCardService {
    private JsonInCardAdapter jsonInCardAdapter;

    private CardInJsonAdapter cardInJsonAdapter;

    public TranslatingCardService() {
        this.jsonInCardAdapter = new JsonInCardAdapter();
        this.cardInJsonAdapter = new CardInJsonAdapter();
    }

    public Card cardFromJson(String jsonString) {
        return jsonInCardAdapter.createFromJson(jsonString);
    }

    public String jsonFromCard(Card card) {
        return cardInJsonAdapter.fromCard(card);
    }
}
