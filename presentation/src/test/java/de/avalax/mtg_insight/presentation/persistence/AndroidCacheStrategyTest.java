package de.avalax.mtg_insight.presentation.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CardRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk=18)
public class AndroidCacheStrategyTest {
    private CacheStrategy cacheStrategy;

    private CardRepository cardRepository;

    @Before
    public void setUp() throws Exception {
        cardRepository = mock(CardRepository.class);
        cacheStrategy = new AndroidCacheStrategy(cardRepository);
    }

    @Test
    public void putCardIntoCache_shouldStoreObjectInSharedPreferences() throws Exception {
        Card card = new CardBuilder("cardname").landCard().build();

        cacheStrategy.put(card);

        verify(cardRepository).save(card);
    }

    @Test
    public void unknownCardFromCache_shouldReturnNull() throws Exception {
        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, nullValue());
    }

    @Test
    public void genericCardFromCache_shouldNotStoredInSharedPreference() throws Exception {
        cacheStrategy.put(new CardBuilder("cardname").build());

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, nullValue());
    }

    @Test
    public void cardFromCache_shouldReturnCardFromRepository() throws Exception {
        Card card = new CardBuilder("cardname").landCard().build();
        when(cardRepository.load("cardname")).thenReturn(card);

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, equalTo(card));
    }
}