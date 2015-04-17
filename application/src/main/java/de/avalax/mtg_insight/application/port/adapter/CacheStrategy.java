package de.avalax.mtg_insight.application.port.adapter;

import de.avalax.mtg_insight.domain.model.card.Card;

public interface CacheStrategy {
    void put(String cardname, Card card);

    Card get(String cardname);
}
