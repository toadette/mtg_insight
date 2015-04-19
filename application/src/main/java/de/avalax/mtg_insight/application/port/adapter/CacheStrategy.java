package de.avalax.mtg_insight.application.port.adapter;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;

public interface CacheStrategy {
    void put(Card card);

    Card get(String cardname) throws CardNotFoundException;
}
