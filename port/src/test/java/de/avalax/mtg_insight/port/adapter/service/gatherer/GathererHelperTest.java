package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Before;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Artifact;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Sorcery;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.exception.CardCorruptedException;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCard;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCardColor;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertColorlessCard;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertConvertedManaCostOfZero;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCreature;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertHybridMana;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertMana;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertPlaneswalker;

public class GathererHelperTest {
    private GathererHelper gathererHelper;

    private Card loadCardFromProperties(String resource) throws Exception {
        PropertyReader.CardProperties cardProperties = new PropertyReader().loadCardPropertiesFromResource(resource);
        return gathererHelper.getCard(cardProperties.type, cardProperties.name, cardProperties.description, cardProperties.manaList, cardProperties.powerToughness);
    }

    @Before
    public void setUp() throws Exception {
        gathererHelper = new GathererHelper(new AbilityTokenizer(), new ColorMatcher(), new ManaTokenizer());
    }

    @Test(expected = CardCorruptedException.class)
    public void cardFromCardname_ShouldCardNotFoundException() throws Exception {
        gathererHelper.getCard("", "hahahakeineKarte", "", null, "");
    }

    @Test
    public void getCard_shouldReturnCreatureWithSingleMana() throws Exception {
        Card card = loadCardFromProperties("1_testcase.properties");

        assertCard(card, "Narset, Enlightened Master", Creature.class);
        assertMana(card, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.BLUE, Mana.RED, Mana.WHITE);
        assertCardColor(card, Color.BLUE, Color.RED, Color.WHITE);
        assertCreature(card, 3, 2);
    }

    @Test
    public void getCard_shouldReturnCreatureWithHybridMana() throws Exception {
        Card card = loadCardFromProperties("2_testcase.properties");

        assertCard(card, "Figure of Destiny", Creature.class);
        assertHybridMana(card, new Mana[]{Mana.RED, Mana.WHITE});
        assertCardColor(card, Color.RED, Color.WHITE);
        assertCreature(card, 1, 1);
    }

    @Test
    public void cardFromCardname_ShouldReturnInstantWithSingleMana() throws Exception {
        Card card = loadCardFromProperties("4_testcase.properties");

        assertCard(card, "Lightning Strike", Instant.class);
        assertMana(card, Mana.COLORLESS, Mana.RED);
        assertCardColor(card, Color.RED);
    }

    @Test
    public void getCard_shouldReturnPlaneswalker() throws Exception {
        Card card = loadCardFromProperties("3_testcase.properties");

        assertCard(card, "Elspeth, Sun's Champion", Planeswalker.class);
        assertMana(card, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.WHITE, Mana.WHITE);
        assertCardColor(card, Color.WHITE);
        assertPlaneswalker(card, 4);
    }

    @Test
    public void cardFromCardname_ShouldReturnSorcery() throws Exception {
        Card card = loadCardFromProperties("5_testcase.properties");

        assertCard(card, "Anger of the Gods", Sorcery.class);
        assertMana(card, Mana.COLORLESS, Mana.RED, Mana.RED);
        assertCardColor(card, Color.RED);
    }

    @Test
    public void cardFromCardname_ShouldReturnLand() throws Exception {
        Card card = loadCardFromProperties("6_testcase.properties");

        assertCard(card, "Swamp", Land.class);
        assertConvertedManaCostOfZero(card);
        assertColorlessCard(card);
    }

    @Test
    public void cardFromCardname_ShouldReturnArtifact() throws Exception {
        Card card = loadCardFromProperties("7_testcase.properties");

        assertCard(card, "Godsend", Artifact.class);
        assertMana(card, Mana.COLORLESS, Mana.WHITE, Mana.WHITE);
        assertCardColor(card, Color.WHITE);
    }
}