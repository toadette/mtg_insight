package de.avalax.mtg_insight.port.adapter.service;

import java.util.List;

import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;

public class MtgJsonDeckService implements DeckService {
    @Override
    public Deck deckFromDeckname(Deckname deck) {
        return null;
    }

    @Override
    public List<Deckname> decknames() {
        return null;
    }
}
