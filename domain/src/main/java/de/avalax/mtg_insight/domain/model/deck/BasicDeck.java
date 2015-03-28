package de.avalax.mtg_insight.domain.model.deck;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;

public class BasicDeck implements Deck {

    private Deckname name;
    private List<Card> deck;
    private List<Card> sideboard;

    public BasicDeck(Deckname name, List<Card> deck,List<Card> sideboard) {
        this.name = name;
        this.deck = deck;
        this.sideboard = sideboard;
    }

    @Override
    public Deckname name() {
        return name;
    }

    @Override
    public List<Card> deck() {
        return deck;
    }

    @Override
    public List<Card> sideboard() {
        return sideboard;
    }
}
