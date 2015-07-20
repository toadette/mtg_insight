package de.avalax.mtg_insight.domain.model.cardDrawing;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;

public class CardDrawingCalculator {

    public double cardDrawingCalculator(CardCollection cards, Card card) {
        if (cards.size() == 0) {
            return 0;
        }
        double cardCount = 0;
        for (Card c : cards) {
            if (c.equals(card)) {
                cardCount++;
            }
        }
        return 100.0 - (((cards.size() - cardCount) / cards.size()) * 100.0);
    }
}
