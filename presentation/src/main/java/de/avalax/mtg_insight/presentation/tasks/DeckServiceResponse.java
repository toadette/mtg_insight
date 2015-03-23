package de.avalax.mtg_insight.presentation.tasks;

import de.avalax.mtg_insight.domain.model.deck.Deck;

public interface DeckServiceResponse {
    void processFinish(Deck deck);
}
