package de.avalax.mtg_insight.application.port.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.bechte.junit.runners.context.HierarchicalContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@RunWith(HierarchicalContextRunner.class)
public class CachedCardServiceTest {
    private static final String UNKNOWN_CARD = "unknown card";
    private CardService cardService;
    private Card card;
    private CacheStrategy cacheStrategy;
    private CardService realCardService;

    @Before
    public void setUp() throws Exception {
        cacheStrategy = mock(CacheStrategy.class);
        realCardService = mock(CardService.class);
        cardService = new CachedCardService(realCardService, cacheStrategy);
    }

    @Test(expected = CardNotFoundException.class)
    public void invalidInput_shouldHaveNoInteractions() throws Exception {
        assertThat(cardService.cardFromCardname(null)).isNull();
        assertThat(cardService.cardFromCardname("")).isNull();
        verifyNoMoreInteractions(cacheStrategy, realCardService);
    }

    @Test(expected = CardNotFoundException.class)
    public void unknownCard_shouldReturnNullFromService() throws Exception {
        when(realCardService.cardFromCardname(UNKNOWN_CARD)).thenThrow(new CardNotFoundException());

        cardService.cardFromCardname(UNKNOWN_CARD);
    }

    public class cardnameKnownInRealCardService {
        private static final String CARDNAME = "known card 1";

        @Before
        public void setUp() throws Exception {
            card = new CardBuilder(CARDNAME).build();
            when(realCardService.cardFromCardname(CARDNAME)).thenReturn(card);
        }

        @Test
        public void unknownCardInCache_shouldBeReturnAndAddedToTheCache() throws Exception {
            Card cardFromCardname = cardService.cardFromCardname(CARDNAME);

            assertThat(cardFromCardname).isEqualTo(card);
            verify(cacheStrategy).put(CARDNAME, CachedCardServiceTest.this.card);
        }

        @Test
        public void knownCardInCache_shouldReturnCardFromCacheAndDoNotAskRealCardService() throws Exception {
            when(cacheStrategy.get(CARDNAME)).thenReturn(card);

            Card cardFromCardname = cardService.cardFromCardname(CARDNAME);

            assertThat(cardFromCardname).isEqualTo(card);
            verifyNoMoreInteractions(realCardService);
        }
    }
}