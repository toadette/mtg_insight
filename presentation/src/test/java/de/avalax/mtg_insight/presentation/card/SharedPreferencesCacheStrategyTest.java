package de.avalax.mtg_insight.presentation.card;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.GenericCard;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class SharedPreferencesCacheStrategyTest {
    private String jsonForCard(GenericCard card) {
        Object[] jsonObject = {card.getClass().getSimpleName(), card};
        return gson.toJson(jsonObject);
    }

    private String jsonForCard(Creature card) {
        Object[] jsonObject = {card.getClass().getSimpleName(), card};
        return gson.toJson(jsonObject);
    }

    private CacheStrategy cacheStrategy;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private android.content.SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Before
    public void setUp() throws Exception {
        gson = new Gson();
        sharedPreferences = mock(SharedPreferences.class);
        cacheStrategy = new SharedPreferencesCacheStrategy(sharedPreferences);
        editor = mock(android.content.SharedPreferences.Editor.class);
        when(sharedPreferences.edit()).thenReturn(editor);
    }

    @Test
    public void putIntoCache_shouldStoreObjectInSharedPreferences() throws Exception {
        Card card = new CardBuilder("cardname").build();

        cacheStrategy.put("cardname", card);

        verify(editor).putString("cardname", "[\"GenericCard\"," + gson.toJson(card) + "]");
        verify(editor).apply();
    }

    @Test
    public void unknownCardFromCache_shouldReturnNull() throws Exception {
        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, nullValue());
    }

    @Test
    public void getGenericCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        GenericCard card = (GenericCard) new CardBuilder("cardname").build();
        when(sharedPreferences.getString("cardname", null)).thenReturn(jsonForCard(card));

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(GenericCard.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
    }

    @Test
    public void getCreatureCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        CreatureBody creatureBody = new CreatureBody(12, 24);
        Creature card = (Creature) new CardBuilder("cardname").creatureCard(creatureBody, Collections.<Ability>emptyList()).build();
        when(sharedPreferences.getString("cardname", null)).thenReturn(jsonForCard(card));

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(Creature.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
        assertThat(((Creature) cardFromCache).creatureBody().power(), equalTo(12));
        assertThat(((Creature) cardFromCache).creatureBody().toughness(), equalTo(24));
    }
}