package de.avalax.mtg_insight.port.adapter.service;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class TestHelper {
    private static void assertMana(Card card, int manaCostPosition, int manaPosition, Mana mana) {
        assertThat(card.convertedManaCost().manaCostAsList().get(manaCostPosition).mana().get(manaPosition), equalTo(mana));
    }

    public static void assertMana(Card card, Mana... mana) {
        for (int i=0;i<mana.length;i++) {
            assertMana(card,i,0,mana[i]);
        }
    }

    public static void assertHybridMana(Card card, Mana[]... hybridMana) {
        for (int i=0;i<hybridMana.length;i++) {
            for (int j=0;j<hybridMana[i].length;j++) {
                assertMana(card,i,j,hybridMana[i][j]);
            }
        }
    }

    public static void assertCard(String cardname, Card card, Class<?> cardType) {
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo(cardname));
        assertThat(card, instanceOf(cardType));
    }

    public static void assertCardColor(Card card, int numberOfCardColors, Color[] cardColors) {
        assertThat(card.colorOfCard(), hasSize(numberOfCardColors));
        assertThat(card.colorOfCard(), hasItems(cardColors));
    }

    public static void assertCreature(Creature creature, int power, int toughness) {
        assertThat(creature.creatureBody().power(), equalTo(power));
        assertThat(creature.creatureBody().toughness(), equalTo(toughness));
    }

    public static void assertPlaneswalker(Planeswalker card, int loyalty) {
        assertThat(card.loyaltyPoints().loyaltyPoints(), equalTo(loyalty));
    }
}
