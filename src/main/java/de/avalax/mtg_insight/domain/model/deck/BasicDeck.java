package de.avalax.mtg_insight.domain.model.deck;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;

public class BasicDeck implements Deck {
    @Override
    public List<Card> cardsOfDeck() {
        throw new RuntimeException("Not implemented");
    }
}
