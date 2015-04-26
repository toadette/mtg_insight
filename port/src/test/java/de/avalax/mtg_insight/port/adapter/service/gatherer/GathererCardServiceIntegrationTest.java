package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCard;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCardColor;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertColorlessCard;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertConvertedManaCostOfZero;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCreature;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertMana;


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

        assertCard(card, cardname, Creature.class);
        assertMana(card, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.BLUE, Mana.RED, Mana.WHITE);
        assertCardColor(card, Color.BLUE, Color.RED, Color.WHITE);
        assertCreature(card, 3, 2);
    }

    @Test
    public void cardFromCardname_shouldReturnMultipleResult() throws Exception {
        String cardname = "Forest";

        Card card = cardService.cardFromCardname(cardname);

        assertCard(card, cardname, Land.class);
        assertConvertedManaCostOfZero(card);
        assertColorlessCard(card);
    }
}