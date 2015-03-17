package de.avalax.mtg_insight.domain.model.deck;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;

public class BasicDeck implements Deck {

    private Deckname name;
    private List<Card> cardsOfDeck;

    public BasicDeck(Deckname name, List<Card> cardsOfDeck) {
        this.name = name;
        this.cardsOfDeck = cardsOfDeck;
    }

    @Override
    public Deckname name() {
        return name;
    }

    @Override
    public List<Card> cardsOfDeck() {
        return cardsOfDeck;
    }
}
