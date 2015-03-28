package de.avalax.mtg_insight.domain.model.deck;

import java.util.List;

import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;

public interface DeckService {
    Deck deckFromDeckname(Deckname deck) throws DeckNotFoundException;
    //TODO: remove decknames
    List<Deckname> decknames();
}
