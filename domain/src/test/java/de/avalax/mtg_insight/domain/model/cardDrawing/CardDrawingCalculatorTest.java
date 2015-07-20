package de.avalax.mtg_insight.domain.model.cardDrawing;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;

import static org.assertj.core.api.Assertions.assertThat;

public class CardDrawingCalculatorTest {
    private void assertDrawPercentage(int cardsDrawnBefore, double expectedDrawPercentage, String cardName, CardCollection cards) {
        double drawPercentage = cardDrawingCalculator.cardDrawPercentage(cardsDrawnBefore, cards, new CardBuilder(cardName).build());

        assertThat(drawPercentage).isEqualTo(expectedDrawPercentage);
    }

    private void assertDrawPercentage(double expectedDrawPercentage, String cardName, CardCollection cards) {
        double drawPercentage = cardDrawingCalculator.cardDrawPercentage(1, cards, new CardBuilder(cardName).build());

        assertThat(drawPercentage).isEqualTo(expectedDrawPercentage);
    }

    private CardCollection cards(CardCollection... cards) {
        CardCollection cardCollection = new CardCollection();
        for (CardCollection cardsColl : cards) {
            for (Card card : cardsColl) {
                cardCollection.add(card);
            }
        }
        return cardCollection;
    }

    private CardCollection cards(String... cards) {
        CardCollection cardCollection = new CardCollection();
        for (String card : cards) {
            cardCollection.add(new CardBuilder(card).build());
        }
        return cardCollection;
    }

    private CardCollection cards(String card, int times) {
        CardCollection cardCollection = new CardCollection();
        for (int i = 0; i < times; i++) {
            cardCollection.add(new CardBuilder(card).build());
        }
        return cardCollection;
    }

    private CardDrawingCalculator cardDrawingCalculator;

    @Before
    public void setUp() throws Exception {
        cardDrawingCalculator = new CardDrawingCalculator();
    }

    @Test
    public void singleCards_shouldReturnDrawingPercentage() throws Exception {
        assertDrawPercentage(0.00, "unknownCardInEmptyDeck", new CardCollection());
        assertDrawPercentage(0.00, "unknownCard", cards("aCard", "anotherCard"));
        assertDrawPercentage(100.00, "singleCardInDeck", cards("singleCardInDeck"));
    }

    @Test
    public void multipleCards_shouldReturnDrawingPercentage() throws Exception {
        assertDrawPercentage(100.00, "onlyCardIdDeck", cards("onlyCardIdDeck", 2));
        assertDrawPercentage(50.00, "oneOfTwoCardsInDeck", cards("oneOfTwoCardsInDeck", "anotherCard"));
        assertDrawPercentage(10.00, "tenPercentOfDeck", cards(cards("tenPercentOfDeck", 6), cards("anotherCard", 54)));
        assertDrawPercentage(50.00, "fiftyPercentOfDeck", cards(cards("fiftyPercentOfDeck", 30), cards("anotherCard", 30)));
    }

    @Test
    public void percentageAfterMultipleTurns_shouldReturnDrawingPercentage() throws Exception {
        assertDrawPercentage(2, 100.00, "oneOfTwoCardsInDeck", cards("oneOfTwoCardsInDeck", "anotherCard"));
        assertDrawPercentage(3, 60.00, "oneOfTwoCardsInDeck", cards(cards("oneOfTwoCardsInDeck", 1), cards("anotherCard", 4)));
        assertDrawPercentage(7, 39.95, "playset", cards(cards("playset", 4), cards("anotherCard", 56)));
        assertDrawPercentage(7, 97.84, "landCard", cards(cards("anotherCard", 36), cards("landCard", 24)));
    }
}