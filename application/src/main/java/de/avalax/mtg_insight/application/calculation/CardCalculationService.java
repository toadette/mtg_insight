package de.avalax.mtg_insight.application.calculation;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.cardDrawing.CardDrawingCalculator;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;

public class CardCalculationService {

    public static final int STARTING_HAND_CARDS = 7;

    private CardDrawingCalculator cardDrawingCalculator;
    private CardCollection cards;

    public CardCalculationService(CardDrawingCalculator cardDrawingCalculator, CardCollection cards) {
        this.cardDrawingCalculator = cardDrawingCalculator;
        this.cards = cards;
    }

    public double cardDrawPercentage(int turn, Card card) {
        //TODO: dynamic HandSize via UI?
        return cardDrawingCalculator.cardDrawPercentage(STARTING_HAND_CARDS + turn - 1, cards, card);
    }
}
