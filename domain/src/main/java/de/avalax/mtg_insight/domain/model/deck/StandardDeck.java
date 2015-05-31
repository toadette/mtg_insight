package de.avalax.mtg_insight.domain.model.deck;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;

public class StandardDeck implements Deck {

    private Deckname name;
    private CardCollection deck;
    private List<Card> sideboard;

    public StandardDeck(Deckname name, List<Card> deck, List<Card> sideboard) {
        this.name = name;
        this.deck = new CardCollection();
        this.deck.addAll(deck);
        this.sideboard = sideboard;
    }

    @Override
    public Deckname name() {
        return name;
    }

    @Override
    public CardCollection deck() {
        return deck;
    }

    @Override
    public List<Card> sideboard() {
        return sideboard;
    }
}
