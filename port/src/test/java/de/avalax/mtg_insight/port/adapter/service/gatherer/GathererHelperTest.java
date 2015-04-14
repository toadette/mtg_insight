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
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
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

    public void testCase3() throws Exception {
        loadTestdataFromResource(getClass().getResourceAsStream("3_testcase.properties"));
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
    public void getCard_shouldReturnCreatureWithSingleMana() throws Exception {
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
    public void getCard_shouldReturnCreatureWithHybridMana() throws Exception {
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

    @Test
    public void getCard_shouldReturnPlaneswalker() throws Exception {
        testCase3();
        GathererHelper gathererHelper = new GathererHelper(new AbilityTokenizer(), new ColorMatcher(), new ManaTokenizer());
        Card card = gathererHelper.getCard(type, name, "description", manaList, powerToughness);
        TestHelper.assertCard("Elspeth, Sun's Champion", card, Planeswalker.class);
        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
        TestHelper.assertMana(card, 0, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 1, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 2, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 3, 0, Mana.COLORLESS);
        TestHelper.assertMana(card, 4, 0, Mana.WHITE);
        TestHelper.assertMana(card, 5, 0, Mana.WHITE);
        TestHelper.assertCardColor(card, 1, new Color[]{Color.WHITE});
        TestHelper.assertPlaneswalker(((Planeswalker) card), 4);
    }

    //    @Ignore
//    @Test
//    public void cardFromCardname_ShouldReturnCreatureWithSingleMana2() throws Exception {
//        String cardName = "Brimaz, King of Oreskos";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Creature.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(3));
//        assertMana(card, 0, 0, Mana.COLORLESS);
//        assertMana(card, 1, 0, Mana.WHITE);
//        assertMana(card, 2, 0, Mana.WHITE);
//        assertCardColor(card, 1, new Color[]{Color.WHITE});
////        assertCreature((Creature) card, 3, 4);
//    }
//    @Ignore
//    @Test
//    public void cardFromCardname_shouldReturnCreatureWithTwoHybridMana() throws Exception {
//        String cardName = "Frostburn Weird";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Creature.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(2));
//        assertMana(card, 0, 0, Mana.BLUE);
//        assertMana(card, 0, 1, Mana.RED);
//        assertMana(card, 1, 0, Mana.BLUE);
//        assertMana(card, 1, 1, Mana.RED);
//        assertCardColor(card, 2, new Color[]{Color.RED, Color.BLUE});
//        assertCreature((Creature) card, 1, 4);
//    }

//    @Test
//    public void cardFromCardname_ShouldReturnCreatureWithOneHybridMana() throws Exception {
//        String cardName = "Figure of Destiny";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Creature.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(1));
//        assertMana(card, 0, 0, Mana.RED);
//        assertMana(card, 0, 1, Mana.WHITE);
//        assertCardColor(card, 2, new Color[]{Color.RED, Color.WHITE});
//        assertCreature((Creature) card, 1, 1);
//    }
//    @Ignore
//    @Test
//    public void cardFromCardname_ShouldReturnCreatureWithHybridTwoColorlessMana() throws Exception {
//        String cardName = "Reaper King";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Creature.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(5));
//        assertMana(card, 0, 0, Mana.HYBRID_TWOCOLORLESS);
//        assertMana(card, 0, 1, Mana.WHITE);
//        assertMana(card, 1, 0, Mana.HYBRID_TWOCOLORLESS);
//        assertMana(card, 1, 1, Mana.BLUE);
//        assertMana(card, 2, 0, Mana.HYBRID_TWOCOLORLESS);
//        assertMana(card, 2, 1, Mana.BLACK);
//        assertMana(card, 3, 0, Mana.HYBRID_TWOCOLORLESS);
//        assertMana(card, 3, 1, Mana.RED);
//        assertMana(card, 4, 0, Mana.HYBRID_TWOCOLORLESS);
//        assertMana(card, 4, 1, Mana.GREEN);
//        assertCardColor(card, 5, new Color[]{Color.WHITE, Color.BLACK, Color.BLUE, Color.GREEN, Color.RED});
//        assertCreature((Creature) card, 6, 6);
//    }
//    @Ignore
//    @Test
//    public void cardFromCardname_ShouldReturnCreatureWithPhyrexianMana() throws Exception {
//        String cardName = "Pith Driller";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Creature.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(5));
//        assertMana(card, 0, 0, Mana.COLORLESS);
//        assertMana(card, 1, 0, Mana.COLORLESS);
//        assertMana(card, 2, 0, Mana.COLORLESS);
//        assertMana(card, 3, 0, Mana.COLORLESS);
//        assertMana(card, 4, 0, Mana.BLACK);
//        assertMana(card, 4, 1, Mana.PHYREXIAN);
//        assertCardColor(card, 1, new Color[]{Color.BLACK});
//        assertCreature((Creature) card, 2, 4);
//    }
//    @Ignore
//    @Test(expected = CardNotFoundException.class)
//    public void cardFromCardname_ShouldCardNotFoundException() throws Exception {
//        cardService.cardFromCardname("hahahakeineKarte");
//    }
//
//    @Ignore
//    @Test
//    public void cardFromCardname_ShouldReturnInstantWithSingleMana() throws Exception {
//        String cardName = "Lightning Strike";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Instant.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(2));
//        assertMana(card, 0, 0, Mana.COLORLESS);
//        assertMana(card, 1, 0, Mana.RED);
//        assertCardColor(card, 1, new Color[]{Color.RED});
//    }
//
//    @Ignore
//    @Test
//    public void cardFromCardname_ShouldReturnPlanesWalkerWithSingleManaAndLoyaltyPoints() throws Exception {
//        String cardName = "Elspeth, Sun's Champion";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Planeswalker.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(6));
//        assertMana(card, 0, 0, Mana.COLORLESS);
//        assertMana(card, 1, 0, Mana.COLORLESS);
//        assertMana(card, 2, 0, Mana.COLORLESS);
//        assertMana(card, 3, 0, Mana.COLORLESS);
//        assertMana(card, 4, 0, Mana.WHITE);
//        assertMana(card, 5, 0, Mana.WHITE);
//        assertCardColor(card, 1, new Color[]{Color.WHITE});
//        assertPlaneswalker((Planeswalker) card, 4);
//    }
//
//    @Ignore
//    @Test
//    public void cardFromCardname_ShouldReturnPlanesWalkerWithColorlessManaAndLoyaltyPoints() throws Exception {
//        String cardName = "Ugin, the Spirit Dragon";
//        Card card = cardService.cardFromCardname(cardName);
//        assertCard(cardName, card, Planeswalker.class);
//        assertThat(card.convertedManaCost().manaCostAsList(), hasSize(8));
//        assertMana(card, 0, 0, Mana.COLORLESS);
//        assertMana(card, 1, 0, Mana.COLORLESS);
//        assertMana(card, 2, 0, Mana.COLORLESS);
//        assertMana(card, 3, 0, Mana.COLORLESS);
//        assertMana(card, 4, 0, Mana.COLORLESS);
//        assertMana(card, 5, 0, Mana.COLORLESS);
//        assertMana(card, 6, 0, Mana.COLORLESS);
//        assertMana(card, 7, 0, Mana.COLORLESS);
//        assertCardColor(card, 0, new Color[]{});
//        assertPlaneswalker((Planeswalker) card, 7);
//    }


}