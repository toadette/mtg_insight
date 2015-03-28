package de.avalax.mtg_insight.domain.model.deck;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;

public interface Deck {
    Deckname name();

    List<Card> deck();

    List<Card> sideboard();
}
