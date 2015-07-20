package de.avalax.mtg_insight.domain.model.cardDrawing;

import java.math.BigDecimal;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;

public class CardDrawingCalculator {

    public double cardDrawPercentage(int cardsToDraw, CardCollection cards, Card card) {
        if (cards.size() == 0) {
            return 0;
        }
        int cardCount = cardCount(card, cards);
        double drawPercentage = 100.0 - (multiplicator(1, cardsToDraw, cards, cardCount)) * 100.0;
        return roundHalfUp(drawPercentage);
    }

    private int cardCount(Card card, CardCollection cards) {
        int cardsInCollection = 0;
        for (Card c : cards) {
            if (c.equals(card)) {
                cardsInCollection++;
            }
        }
        return cardsInCollection;
    }

    private double multiplicator(double percentage, double cardsToDraw, CardCollection cards, double cardCountInCollection) {
        double cardsLeftToDraw = cardsToDraw - 1;
        double newPercentage = percentage * (cards.size() - cardCountInCollection - cardsLeftToDraw) / (cards.size() - cardsLeftToDraw);
        if (cardsLeftToDraw == 0) {
            return newPercentage;
        } else {
            return multiplicator(newPercentage, cardsLeftToDraw, cards, cardCountInCollection);
        }
    }

    private double roundHalfUp(double drawPercentage) {
        BigDecimal bd = new BigDecimal(Double.toString(drawPercentage));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
