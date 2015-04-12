package de.avalax.mtg_insight.port.adapter.service.mtgdb;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class MtgDBCardServiceTest {

    private void assertMana(Card card, int manaCostPosition, int manaPosition, Mana mana) {
        assertThat(card.convertedManaCost().manaCostAsList().get(manaCostPosition).mana().get(manaPosition), equalTo(mana));
    }

    private void assertCard(String cardname, Card card, Class<?> cardType) {
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo(cardname));
        assertThat(card, instanceOf(cardType));
    }

    private void assertCardColor(Card card, int numberOfCardColors, Color[] cardColors) {
        assertThat(card.colorOfCard(), hasSize(numberOfCardColors));
        assertThat(card.colorOfCard(), hasItems(cardColors));
    }

    private void assertCreature(Creature creature, int power, int toughness) {
        assertThat(creature.creatureBody().power(), equalTo(power));
        assertThat(creature.creatureBody().toughness(), equalTo(toughness));
    }

    private void assertPlaneswalker(Planeswalker card, int loyalty) {
        assertThat(card.loyaltyPoints().loyaltyPoints(),equalTo(loyalty));
    }

    private de.avalax.mtg_insight.port.adapter.service.mtgdb.MtgDBCardService mtgDBCardService;

    @Before
    public void setUp() throws Exception {
        mtgDBCardService = new de.avalax.mtg_insight.port.adapter.service.mtgdb.MtgDBCardService(new ManaTokenizer(), new ColorMatcher(),new de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer());
    }

    @Test
    public void cardFromCardname_shouldReturnCreatureWithSingleMana() throws Exception {
        String cardname = "Narset, Enlightened Master";
        Card card = mtgDBCardService.cardFromCardname(cardname);
        assertCard(cardname, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.COLORLESS);
        assertMana(card, 2, 0, Mana.COLORLESS);
        assertMana(card, 3, 0, Mana.BLUE);
        assertMana(card, 4, 0, Mana.RED);
        assertMana(card, 5, 0, Mana.WHITE);
        assertCardColor(card, 3, new Color[]{Color.BLUE, Color.RED, Color.WHITE});
        assertCreature((Creature) card, 3, 2);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithSingleMana2() throws Exception {
        String cardName = "Brimaz, King of Oreskos";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(3));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.WHITE);
        assertMana(card, 2, 0, Mana.WHITE);
        assertCardColor(card, 1, new Color[]{Color.WHITE});
        assertCreature((Creature) card, 3, 4);
    }

    @Test
    public void cardFromCardname_shouldReturnCreatureWithTwoHybridMana() throws Exception {
        String cardName = "Frostburn Weird";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(2));
        assertMana(card, 0, 0, Mana.BLUE);
        assertMana(card, 0, 1, Mana.RED);
        assertMana(card, 1, 0, Mana.BLUE);
        assertMana(card, 1, 1, Mana.RED);
        assertCardColor(card, 2, new Color[]{Color.RED, Color.BLUE});
        assertCreature((Creature) card, 1, 4);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithOneHybridMana() throws Exception {
        String cardName = "Figure of Destiny";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(1));
        assertMana(card, 0, 0, Mana.RED);
        assertMana(card, 0, 1, Mana.WHITE);
        assertCardColor(card, 2, new Color[]{Color.RED, Color.WHITE});
        assertCreature((Creature) card, 1, 1);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithHybridTwoColorlessMana() throws Exception {
        String cardName = "Reaper King";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(5));
        assertMana(card, 0, 0, Mana.HYBRID_TWOCOLORLESS);
        assertMana(card, 0, 1, Mana.WHITE);
        assertMana(card, 1, 0, Mana.HYBRID_TWOCOLORLESS);
        assertMana(card, 1, 1, Mana.BLUE);
        assertMana(card, 2, 0, Mana.HYBRID_TWOCOLORLESS);
        assertMana(card, 2, 1, Mana.BLACK);
        assertMana(card, 3, 0, Mana.HYBRID_TWOCOLORLESS);
        assertMana(card, 3, 1, Mana.RED);
        assertMana(card, 4, 0, Mana.HYBRID_TWOCOLORLESS);
        assertMana(card, 4, 1, Mana.GREEN);
        assertCardColor(card, 5, new Color[]{Color.WHITE, Color.BLACK, Color.BLUE, Color.GREEN, Color.RED});
        assertCreature((Creature) card, 6, 6);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithPhyrexianMana() throws Exception {
        String cardName = "Pith Driller";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(5));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.COLORLESS);
        assertMana(card, 2, 0, Mana.COLORLESS);
        assertMana(card, 3, 0, Mana.COLORLESS);
        assertMana(card, 4, 0, Mana.BLACK);
        assertMana(card, 4, 1, Mana.PHYREXIAN);
        assertCardColor(card, 1, new Color[]{Color.BLACK});
        assertCreature((Creature) card, 2, 4);
    }

    @Test(expected = CardNotFoundException.class)
    public void cardFromCardname_ShouldCardNotFoundException() throws Exception {
        mtgDBCardService.cardFromCardname("hahahakeineKarte");
    }

    @Test
    public void cardFromCardname_ShouldReturnInstantWithSingleMana() throws Exception {
        String cardName = "Lightning Strike";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Instant.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(2));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.RED);
        assertCardColor(card, 1, new Color[]{Color.RED});
    }

    @Test
    public void cardFromCardname_ShouldReturnPlanesWalkerWithSingleManaAndLoyaltyPoints() throws Exception {
        String cardName = "Elspeth, Sun's Champion";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Planeswalker.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.COLORLESS);
        assertMana(card, 2, 0, Mana.COLORLESS);
        assertMana(card, 3, 0, Mana.COLORLESS);
        assertMana(card, 4, 0, Mana.WHITE);
        assertMana(card, 5, 0, Mana.WHITE);
        assertCardColor(card, 1, new Color[]{Color.WHITE});
        assertPlaneswalker((Planeswalker) card, 4);
    }

    @Test
    public void cardFromCardname_ShouldReturnPlanesWalkerWithColorlessManaAndLoyaltyPoints() throws Exception {
        String cardName = "Ugin, the Spirit Dragon";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Planeswalker.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(8));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.COLORLESS);
        assertMana(card, 2, 0, Mana.COLORLESS);
        assertMana(card, 3, 0, Mana.COLORLESS);
        assertMana(card, 4, 0, Mana.COLORLESS);
        assertMana(card, 5, 0, Mana.COLORLESS);
        assertMana(card, 6, 0, Mana.COLORLESS);
        assertMana(card, 7, 0, Mana.COLORLESS);
        assertCardColor(card, 0, new Color[]{});
        assertPlaneswalker((Planeswalker) card, 7);
    }

}