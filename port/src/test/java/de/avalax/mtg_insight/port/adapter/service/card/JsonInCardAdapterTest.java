package de.avalax.mtg_insight.port.adapter.service.card;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Artifact;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.Enchantment;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Sorcery;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class JsonInCardAdapterTest {
    private void assertCorrectCardTypeStored(Card card, Class<?> type) {
        String json = cardInJsonAdapter.fromCard(card);
        Card cardFromJson = jsonInCardAdapter.createFromJson(json);

        assertThat(cardFromJson, instanceOf(type));
        assertThat(cardFromJson.name(), equalTo("cardname"));
    }

    private JsonInCardAdapter jsonInCardAdapter;
    private CardInJsonAdapter cardInJsonAdapter;

    @Before
    public void setUp() throws Exception {
        jsonInCardAdapter = new JsonInCardAdapter();
        cardInJsonAdapter = new CardInJsonAdapter();
    }

    @Test
    public void getCreatureCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        CreatureBody creatureBody = new CreatureBody(12, 24);
        String json = cardInJsonAdapter.fromCard(new CardBuilder("cardname").creatureCard(creatureBody, Collections.<Ability>emptyList()).build());

        Card card = jsonInCardAdapter.createFromJson(json);

        assertThat(card, instanceOf(Creature.class));
        assertThat(card.name(), equalTo("cardname"));
        assertThat(((Creature) card).creatureBody().power(), equalTo(12));
        assertThat(((Creature) card).creatureBody().toughness(), equalTo(24));
    }

    @Test
    public void getPlaneswalkerCardFromCache_shouldReturnStoredSharedPreference() throws Exception {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(6);
        String json = cardInJsonAdapter.fromCard(new CardBuilder("cardname").planeswalkerCard(loyaltyPoints).build());

        Card card = jsonInCardAdapter.createFromJson(json);

        assertThat(card, instanceOf(Planeswalker.class));
        assertThat(card.name(), equalTo("cardname"));
        assertThat(((Planeswalker) card).loyaltyPoints().loyaltyPoints(), equalTo(loyaltyPoints.loyaltyPoints()));
    }

    @Test
    public void differentCardTypes_shouldReturnFromSharedPreferences() throws Exception {
        assertCorrectCardTypeStored(new CardBuilder("cardname").build(), GenericCard.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").landCard().build(), Land.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").artifactCard().build(), Artifact.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").enchantmentCard().build(), Enchantment.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").instantCard().build(), Instant.class);
        assertCorrectCardTypeStored(new CardBuilder("cardname").sorceryCard().build(), Sorcery.class);
    }
}