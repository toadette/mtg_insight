package de.avalax.mtg_insight.application.calculation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.cardDrawing.CardDrawingCalculator;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardCalculationServiceTest {
    @Mock
    private CardDrawingCalculator cardDrawingCalculator;

    @InjectMocks
    private CardCalculationService cardCalculationService;

    @Mock
    private Card card;

    @Mock
    private CardCollection cards;

    @Test
    public void calculateCardDrawPercentage_shouldReturnPercentageOfStartingHand() throws Exception {
        when(cardDrawingCalculator.cardDrawPercentage(CardCalculationService.STARTING_HAND_CARDS, cards, card)).thenReturn(42.0);

        double cardDrawPercentage = cardCalculationService.cardDrawPercentage(1, card);

        assertThat(cardDrawPercentage).isEqualTo(42.0);
    }
}