package de.avalax.mtg_insight.domain.model.cardDrawing;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;

import static org.assertj.core.api.Assertions.assertThat;

public class CardDrawingCalculatorTest {
    private void assertDrawPercentage(double expectedDrawPercentage, String cardName, CardCollection cards) {
        double drawPercentage = cardDrawingCalculator.cardDrawingCalculator(cards, new CardBuilder(cardName).build());

        assertThat(drawPercentage).isEqualTo(expectedDrawPercentage);
    }

    private CardCollection cards(String... cards) {
        CardCollection cardCollection = new CardCollection();
        for (String card : cards) {
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
        assertDrawPercentage(0.00, "unknownCardInEmptyDeck", cards());
        assertDrawPercentage(0.00, "unknownCard", cards("aCard", "anotherCard"));
        assertDrawPercentage(100.00, "singleCardInDeck", cards("singleCardInDeck"));
        assertDrawPercentage(100.00, "onlyCardIdDeck", cards("onlyCardIdDeck", "onlyCardIdDeck"));
        assertDrawPercentage(50.00, "oneOfTwoCardsInDeck", cards("oneOfTwoCardsInDeck", "anotherCard"));
    }
}