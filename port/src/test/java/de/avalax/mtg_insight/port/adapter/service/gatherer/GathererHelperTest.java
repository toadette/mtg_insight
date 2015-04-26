package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import de.avalax.mtg_insight.port.adapter.service.TestHelper;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCard;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCardColor;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertCreature;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertMana;
import static de.avalax.mtg_insight.port.adapter.service.TestHelper.assertPlaneswalker;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class GathererHelperTest {
    private GathererHelper gathererHelper;
    private String type;
    private String name;
    private String description;
    private String mana;
    private List<String> manaList;
    private String powerToughness;

    private void loadTestCase(String number) throws Exception {
        loadTestdataFromResource(getClass().getResourceAsStream(number + "_testcase.properties"));
    }

    private void loadTestdataFromResource(InputStream resourceAsStream) throws IOException {
        manaList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
        String line = br.readLine();
        for (int cnt = 0; line != null; cnt++, line = br.readLine()) {
            if (cnt == 0) {
                name = line;
            } else if (cnt == 1) {
                type = line;
            } else if (cnt == 2) {
                mana = line;
            } else if (cnt == 3) {
                powerToughness = line;
            } else {
                description += line;
            }
        }
        Collections.addAll(manaList, mana.split(";"));
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
        loadTestCase("1");

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard("Narset, Enlightened Master", card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
        assertMana(card, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.BLUE, Mana.RED, Mana.WHITE);
        assertCardColor(card, 3, new Color[]{Color.BLUE, Color.RED, Color.WHITE});
        assertCreature((Creature) card, 3, 2);
    }

    @Test
    public void getCard_shouldReturnCreatureWithHybridMana() throws Exception {
        loadTestCase("2");

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard("Figure of Destiny", card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(1));
        TestHelper.assertHybridMana(card, new Mana[]{Mana.RED, Mana.WHITE});

        assertCardColor(card, 2, new Color[]{Color.RED, Color.WHITE});
        assertCreature((Creature) card, 1, 1);
    }

    @Test
    public void cardFromCardname_ShouldReturnInstantWithSingleMana() throws Exception {
        loadTestCase("4");
        String cardName = "Lightning Strike";

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard(cardName, card, Instant.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(2));
        assertMana(card, Mana.COLORLESS, Mana.RED);
        assertCardColor(card, 1, new Color[]{Color.RED});
    }

    @Test
    public void getCard_shouldReturnPlaneswalker() throws Exception {
        loadTestCase("3");

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard("Elspeth, Sun's Champion", card, Planeswalker.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
        assertMana(card, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.WHITE, Mana.WHITE);
        assertCardColor(card, 1, new Color[]{Color.WHITE});
        assertPlaneswalker(((Planeswalker) card), 4);
    }

    @Test
    public void cardFromCardname_ShouldReturnSorcery() throws Exception {
        loadTestCase("5");
        String cardName = "Anger of the Gods";

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard(cardName, card, Sorcery.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(3));
        assertMana(card, Mana.COLORLESS, Mana.RED, Mana.RED);
        assertCardColor(card, 1, new Color[]{Color.RED});
    }

    @Test
    public void cardFromCardname_ShouldReturnLand() throws Exception {
        loadTestCase("6");
        String cardName = "Swamp";

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard(cardName, card, Land.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(0));
        assertCardColor(card, 0, new Color[]{});
    }

    @Test
    public void cardFromCardname_ShouldReturnArtifact() throws Exception {
        loadTestCase("7");
        String cardName = "Godsend";

        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);

        assertCard(cardName, card, Artifact.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(3));
        assertMana(card, Mana.COLORLESS, Mana.WHITE, Mana.WHITE);
        assertCardColor(card, 1, new Color[]{Color.WHITE});
    }
}