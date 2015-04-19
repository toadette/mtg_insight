package de.avalax.mtg_insight.domain.model.card;

import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;

public interface CardRepository {
    void save(Card card);

    Card load(String cardName) throws CardNotFoundException;

    void delete(Card card);
}
