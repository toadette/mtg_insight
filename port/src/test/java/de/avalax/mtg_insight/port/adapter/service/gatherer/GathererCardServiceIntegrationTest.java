package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.port.adapter.service.TestHelper;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;


public class GathererCardServiceIntegrationTest {


    private GathererCardService cardService;

    @Before
    public void setUp() throws Exception {
        cardService = new GathererCardService(new ManaTokenizer(), new ColorMatcher(), new AbilityTokenizer());
    }

    @Test
    public void cardFromCardname_shouldReturnCreatureWithSingleMana() throws Exception {
        String cardname = "Narset, Enlightened Master";
        Card card = cardService.cardFromCardname(cardname);
        TestHelper.assertCard(cardname, card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
        TestHelper.assertMana(card, 0, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 1, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 2, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 3, 0, Mana.BLUE);
        TestHelper.assertMana(card, 4, 0, Mana.RED);
        TestHelper.assertMana(card, 5, 0, Mana.WHITE);
        TestHelper.assertCardColor(card, 3, new Color[]{Color.BLUE, Color.RED, Color.WHITE});
        TestHelper.assertCreature((Creature) card, 3, 2);
    }

}