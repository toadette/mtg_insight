package de.avalax.mtg_insight.presentation.persistence;

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
import de.avalax.mtg_insight.domain.model.card.Artifact;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.Enchantment;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Sorcery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class SharedPreferencesCacheStrategyTest {

    private void assertGenericCardExistsInCache(String cardname) {
        assertThat(cacheStrategy.get(cardname), instanceOf(Instant.class));
        assertThat(cacheStrategy.get(cardname).name(), equalTo(cardname));
    }

    private void assertCorrectCardTypeStored(Card card, Class<?> type) {
        sharedPreferences.edit().putString("cardname", jsonForCard(card)).apply();

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(type));
        assertThat(cardFromCache.name(), equalTo("cardname"));
    }

    private void addInstantIntoCache(String cardname) {
        Card card = new CardBuilder(cardname).instantCard().build();
        cacheStrategy.put(cardname, card);
    }

    private String jsonForCard(Card card) {
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
    public void putCardIntoCache_shouldStoreObjectInSharedPreferences() throws Exception {
        Card card = new CardBuilder("cardname").landCard().build();

        cacheStrategy.put("cardname", card);

        assertThat(sharedPreferences.getString("cardname", null), equalTo("[\"Land\"," + gson.toJson(card) + "]"));
    }

    @Test
    public void unknownCardFromCache_shouldReturnNull() throws Exception {
        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, nullValue());
    }

    @Test
    public void genericCardFromCache_shouldNotStoredInSharedPreference() throws Exception {
        cacheStrategy.put("cardname", new CardBuilder("cardname").build());

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, nullValue());
    }

    @Test
    public void getCreatureCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        CreatureBody creatureBody = new CreatureBody(12, 24);
        Creature card = (Creature) new CardBuilder("cardname").creatureCard(creatureBody, Collections.<Ability>emptyList()).build();
        cacheStrategy.put("cardname", card);

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
        cacheStrategy.put("cardname", card);

        Card cardFromCache = cacheStrategy.get("cardname");

        assertThat(cardFromCache, instanceOf(Planeswalker.class));
        assertThat(cardFromCache.name(), equalTo("cardname"));
        assertThat(((Planeswalker) cardFromCache).loyaltyPoints().loyaltyPoints(), equalTo(loyaltyPoints.loyaltyPoints()));
    }

    @Test
    public void differentCardTypes_shouldReturnFromSharedPreferences() throws Exception {
        assertCorrectCardTypeStored(new CardBuilder("cardname").landCard().build(), Land.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").artifactCard().build(), Artifact.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").enchantmentCard().build(), Enchantment.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").instantCard().build(), Instant.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").sorceryCard().build(), Sorcery.class);
    }

    @Test
    public void writeManyCardsIntoCache_shouldReturnAllCardsFromCache() throws Exception {
        addInstantIntoCache("cardname 1");
        addInstantIntoCache("cardname 2");
        addInstantIntoCache("cardname 3");
        addInstantIntoCache("cardname 4");
        addInstantIntoCache("cardname 5");
        addInstantIntoCache("cardname 6");
        addInstantIntoCache("cardname 7");
        addInstantIntoCache("cardname 8");
        addInstantIntoCache("cardname 9");
        addInstantIntoCache("cardname 12");
        addInstantIntoCache("cardname 11");
        addInstantIntoCache("cardname 10");

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
}