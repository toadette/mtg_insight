package de.avalax.mtg_insight.port.adapter.service;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.mana.Mana;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class MtgDBCardServiceTest {

    private void assertMana(Card card, int manaCostPosition, int manaPosition, Mana mana) {
        assertThat(card.convertedManaCost().get(manaCostPosition).mana().get(manaPosition), equalTo(mana));
    }

    private void assertCard(String cardname, Card card, Class<Creature> cardType) {
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo(cardname));
        assertThat(card, instanceOf(cardType));
    }

    private MtgDBCardService mtgDBCardService;

    @Before
    public void setUp() throws Exception {
        mtgDBCardService = new MtgDBCardService(new ManaTokenizer());
    }

    @Test
    public void cardFromCardname_shouldReturnCreatureWithSingleMana() throws Exception {
        String cardname = "Narset, Enlightened Master";
        Card card = mtgDBCardService.cardFromCardname(cardname);
        assertCard(cardname, card, Creature.class);
        assertThat(card.convertedManaCost(), hasSize(6));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.COLORLESS);
        assertMana(card, 2, 0, Mana.COLORLESS);
        assertMana(card, 3, 0, Mana.BLUE);
        assertMana(card, 4, 0, Mana.RED);
        assertMana(card, 5, 0, Mana.WHITE);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithSingleMana2() throws Exception {
        String cardName = "Brimaz, King of Oreskos";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost(), hasSize(3));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.WHITE);
        assertMana(card, 2, 0, Mana.WHITE);
    }

    @Test
    public void cardFromCardname_shouldReturnCreatureWithTwoHybridMana() throws Exception {
        String cardName = "Frostburn Weird";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost(), hasSize(2));
        assertMana(card, 0, 0, Mana.BLUE);
        assertMana(card, 0, 1, Mana.RED);
        assertMana(card, 1, 0, Mana.BLUE);
        assertMana(card, 1, 1, Mana.RED);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithOneHybridMana() throws Exception {
        String cardName = "Figure of Destiny";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost(), hasSize(1));
        assertMana(card, 0, 0, Mana.RED);
        assertMana(card, 0, 1, Mana.WHITE);
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithHybridTwoColorlessMana() throws Exception {
        String cardName = "Reaper King";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName, card, Creature.class);
        assertThat(card.convertedManaCost(), hasSize(5));
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
    }

    @Test
    public void cardFromCardname_ShouldReturnCreatureWithPhyrexianMana() throws Exception {
        String cardName = "Pith Driller";
        Card card = mtgDBCardService.cardFromCardname(cardName);
        assertCard(cardName,card, Creature.class);
        assertThat(card.convertedManaCost(), hasSize(5));
        assertMana(card, 0, 0, Mana.COLORLESS);
        assertMana(card, 1, 0, Mana.COLORLESS);
        assertMana(card, 2, 0, Mana.COLORLESS);
        assertMana(card, 3, 0, Mana.COLORLESS);
        assertMana(card, 4, 0, Mana.BLACK);
        assertMana(card, 4, 1, Mana.PHYREXIAN);
    }
}