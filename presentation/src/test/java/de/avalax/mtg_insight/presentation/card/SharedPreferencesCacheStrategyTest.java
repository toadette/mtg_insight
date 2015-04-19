package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Collections;

import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class SharedPreferencesCacheStrategyTest {

    private String jsonForCard(Card card) {
        Object[] jsonObject = {card.getClass().getSimpleName(), card};
        return gson.toJson(jsonObject);
    }

    private String jsonForCard(Planeswalker card) {
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

    @Before
    public void setUp() throws Exception {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        sharedPreferences = context.getSharedPreferences("mtg_insight.cards", Context.MODE_PRIVATE);
        cacheStrategy = new SharedPreferencesCacheStrategy(sharedPreferences);
        gson = new Gson();
    }

    @Test
    public void putIntoCache_shouldStoreObjectInSharedPreferences() throws Exception {
        Card card = new CardBuilder("cardname").build();

        cacheStrategy.put("cardname", card);

        assertThat(sharedPreferences.getString("cardname", null), equalTo("[\"GenericCard\"," + gson.toJson(card) + "]"));
    }

    @Test
    public void unknownCardFromCache_shouldReturnNull() throws Exception {
        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, nullValue());
    }

    @Test
    public void getGenericCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        addGenericCardToCache("cardname");

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(GenericCard.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
    }

    @Test
    public void getCreatureCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        CreatureBody creatureBody = new CreatureBody(12, 24);
        Creature card = (Creature) new CardBuilder("cardname").creatureCard(creatureBody, Collections.<Ability>emptyList()).build();
        sharedPreferences.edit().putString("cardname", jsonForCard(card)).apply();

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(Creature.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
        assertThat(((Creature) cardFromCache).creatureBody().power(), equalTo(12));
        assertThat(((Creature) cardFromCache).creatureBody().toughness(), equalTo(24));
    }

    @Test
    public void getPlaneswalkerCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(6);
        Planeswalker card = (Planeswalker) new CardBuilder("cardname").planeswalkerCard(loyaltyPoints).build();
        sharedPreferences.edit().putString("cardname", jsonForCard(card)).apply();

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(Planeswalker.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
        assertThat(((Planeswalker) cardFromCache).loyaltyPoints().loyaltyPoints(), equalTo(6));
    }

    @Test
    public void getLandCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        Card card = new CardBuilder("cardname").landCard().build();
        sharedPreferences.edit().putString("cardname", jsonForCard(card)).apply();

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(Land.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
    }

    @Test
    public void writeManyCardsIntoCache_shouldReturnAllCardsFromCache() throws Exception {
        addGenericCardToCache("cardname 1");
        addGenericCardToCache("cardname 2");
        addGenericCardToCache("cardname 3");
        addGenericCardToCache("cardname 4");
        addGenericCardToCache("cardname 5");
        addGenericCardToCache("cardname 6");
        addGenericCardToCache("cardname 7");
        addGenericCardToCache("cardname 8");
        addGenericCardToCache("cardname 9");
        addGenericCardToCache("cardname 12");
        addGenericCardToCache("cardname 11");
        addGenericCardToCache("cardname 10");

        assertGenericCardExistsInCache("cardname 1");
        assertGenericCardExistsInCache("cardname 2");
        assertGenericCardExistsInCache("cardname 3");
        assertGenericCardExistsInCache("cardname 4");
        assertGenericCardExistsInCache("cardname 5");
        assertGenericCardExistsInCache("cardname 6");
        assertGenericCardExistsInCache("cardname 7");
        assertGenericCardExistsInCache("cardname 8");
        assertGenericCardExistsInCache("cardname 9");
        assertGenericCardExistsInCache("cardname 10");
        assertGenericCardExistsInCache("cardname 11");
        assertGenericCardExistsInCache("cardname 12");
    }

    private void assertGenericCardExistsInCache(String cardname) {
        assertThat(cacheStrategy.get(cardname), instanceOf(GenericCard.class));
        assertThat(cacheStrategy.get(cardname).name(), equalTo(cardname));
    }

    private void addGenericCardToCache(String cardname) {
        Card card = new CardBuilder(cardname).build();
        sharedPreferences.edit().putString(cardname, jsonForCard(card)).apply();
    }
}