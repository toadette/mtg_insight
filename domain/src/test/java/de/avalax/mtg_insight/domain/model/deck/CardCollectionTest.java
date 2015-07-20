package de.avalax.mtg_insight.domain.model.deck;

import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.card.Permanent;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Spell;

import static org.assertj.core.api.Assertions.assertThat;


public class CardCollectionTest {

    @Test
    public void emptyDeck_shouldHAveNothingToIterate() throws Exception {
        CardCollection cardCollection = new CardCollection();

        assertThat(cardCollection.size()).isZero();
        assertThat(cardCollection).isEmpty();
        assertThat(cardCollection.creatures()).isEmpty();
        assertThat(cardCollection.spells()).isEmpty();
        assertThat(cardCollection.permanents()).isEmpty();
        assertThat(cardCollection.planeswalkers()).isEmpty();
        assertThat(cardCollection.lands()).isEmpty();
    }

    @Test
    public void deckWithTwoGenericCards_shouldIterateTwoCards() throws Exception {
        CardCollection cardCollection = new CardCollection();
        Card card1 = new CardBuilder("1").build();
        Card card2 = new CardBuilder("2").build();
        cardCollection.add(card1);
        cardCollection.add(card2);

        assertThat(cardCollection.size()).isEqualTo(2);
        assertThat(cardCollection).containsExactly(card1, card2);
        assertThat(cardCollection.creatures()).isEmpty();
        assertThat(cardCollection.spells()).isEmpty();
        assertThat(cardCollection.permanents()).isEmpty();
        assertThat(cardCollection.planeswalkers()).isEmpty();
        assertThat(cardCollection.lands()).isEmpty();
    }

    @Test
    public void deckWithOneCreature_shouldReturnCreatures() throws Exception {
        CardCollection cardCollection = new CardCollection();
        Card card1 = new CardBuilder("1").creatureCard(new CreatureBody(2, 2), null).build();
        cardCollection.add(card1);

        assertThat(cardCollection.size()).isEqualTo(1);
        assertThat(cardCollection).containsExactly(card1);
        assertThat(cardCollection.creatures()).containsExactly((Creature) card1);
        assertThat(cardCollection.spells()).isEmpty();
        assertThat(cardCollection.permanents()).isEmpty();
        assertThat(cardCollection.planeswalkers()).isEmpty();
        assertThat(cardCollection.lands()).isEmpty();
    }

    @Test
    public void deckWithSpells_shouldReturnSpells() throws Exception {
        CardCollection cardCollection = new CardCollection();
        Card card1 = new CardBuilder("1").instantCard().build();
        Card card2 = new CardBuilder("2").sorceryCard().build();
        cardCollection.add(card1);
        cardCollection.add(card2);

        assertThat(cardCollection.size()).isEqualTo(2);
        assertThat(cardCollection).containsExactly(card1, card2);
        assertThat(cardCollection.creatures()).isEmpty();
        assertThat(cardCollection.spells()).containsExactly((Spell) card1, (Spell) card2);
        assertThat(cardCollection.permanents()).isEmpty();
        assertThat(cardCollection.planeswalkers()).isEmpty();
        assertThat(cardCollection.lands()).isEmpty();
    }

    @Test
    public void deckWithPermanents_shouldIteratePermanents() throws Exception {
        CardCollection cardCollection = new CardCollection();
        Card card1 = new CardBuilder("1").artifactCard().build();
        Card card2 = new CardBuilder("2").enchantmentCard().build();
        cardCollection.add(card1);
        cardCollection.add(card2);

        assertThat(cardCollection.size()).isEqualTo(2);
        assertThat(cardCollection).containsExactly(card1, card2);
        assertThat(cardCollection.creatures()).isEmpty();
        assertThat(cardCollection.spells()).isEmpty();
        assertThat(cardCollection.permanents()).containsExactly((Permanent) card1, (Permanent) card2);
        assertThat(cardCollection.planeswalkers()).isEmpty();
        assertThat(cardCollection.lands()).isEmpty();
    }

    @Test
    public void deckWithPlaneswalker_shouldIterateOnePlaneswalker() throws Exception {
        CardCollection cardCollection = new CardCollection();
        Card card1 = new CardBuilder("1").planeswalkerCard(new LoyaltyPoints(2)).build();
        cardCollection.add(card1);

        assertThat(cardCollection).containsExactly(card1);
        assertThat(cardCollection.creatures()).isEmpty();
        assertThat(cardCollection.spells()).isEmpty();
        assertThat(cardCollection.permanents()).isEmpty();
        assertThat(cardCollection.planeswalkers()).containsExactly((Planeswalker) card1);
        assertThat(cardCollection.lands()).isEmpty();
    }

    @Test
    public void deckWithLands_shouldIterateOneLand() throws Exception {
        CardCollection cardCollection = new CardCollection();
        Card card1 = new CardBuilder("1").landCard().build();
        cardCollection.add(card1);

        assertThat(cardCollection.size()).isEqualTo(1);
        assertThat(cardCollection).containsExactly(card1);
        assertThat(cardCollection.creatures()).isEmpty();
        assertThat(cardCollection.spells()).isEmpty();
        assertThat(cardCollection.permanents()).isEmpty();
        assertThat(cardCollection.planeswalkers()).isEmpty();
        assertThat(cardCollection.lands()).containsExactly((Land) card1);
    }
}