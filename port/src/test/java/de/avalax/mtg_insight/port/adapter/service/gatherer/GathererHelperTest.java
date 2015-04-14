package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.port.adapter.service.TestHelper;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class GathererHelperTest {
    private String type;
    private String name;
    private String description;
    private String mana;
    private List<String> manaList;
    private String powerToughness;

    public void testCase1() throws Exception {
        loadTestdataFromResource(getClass().getResourceAsStream("1_testcase.properties"));
    }

    public void testCase2() throws Exception {
        loadTestdataFromResource(getClass().getResourceAsStream("2_testcase.properties"));
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
        for (String str : mana.split(";")) {
            manaList.add(str);
        }
    }

    @Test
    public void getCard_shouldReturnNarset() throws Exception {
        testCase1();
        GathererHelper gathererHelper = new GathererHelper(new AbilityTokenizer(), new ColorMatcher(), new ManaTokenizer());
        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);
        TestHelper.assertCard("Narset, Enlightened Master", card, Creature.class);
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

    @Test
    public void getCard_shouldReturnFigure() throws Exception {
        testCase2();
        GathererHelper gathererHelper = new GathererHelper(new AbilityTokenizer(), new ColorMatcher(), new ManaTokenizer());
        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);
        TestHelper.assertCard("Figure of Destiny", card, Creature.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(1));
        TestHelper.assertMana(card, 0, 0, Mana.RED);
        TestHelper.assertMana(card, 0, 1, Mana.WHITE);
        TestHelper.assertCardColor(card, 2, new Color[]{Color.RED, Color.WHITE});
        TestHelper.assertCreature((Creature) card, 1, 1);
    }


}