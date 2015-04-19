package de.avalax.mtg_insight.port.adapter.service.card;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class CardInJsonAdapterTest {

    private Gson gson;

    private CardInJsonAdapter cardInJsonAdapter;

    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        cardInJsonAdapter = new CardInJsonAdapter();
    }

    @Test
    public void putCardIntoCache_shouldStoreObjectInSharedPreferences() throws Exception {
        Card card = new CardBuilder("cardname").landCard().build();

        String json = cardInJsonAdapter.fromCard(card);

        assertThat(json, equalTo("[\"Land\"," + gson.toJson(card) + "]"));
    }
}