package de.avalax.mtg_insight.domain.model.card;

import de.avalax.mtg_insight.domain.model.exception.CardCorruptedException;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;

public interface CardService {
    Card cardFromCardname(String cardname) throws CardNotFoundException, CardCorruptedException;
}
