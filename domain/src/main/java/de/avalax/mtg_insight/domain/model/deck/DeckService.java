package de.avalax.mtg_insight.domain.model.deck;

import java.util.List;

public interface DeckService {
    Deck deckFromDeckname(Deckname deck);

    List<Deckname> decknames();
}
