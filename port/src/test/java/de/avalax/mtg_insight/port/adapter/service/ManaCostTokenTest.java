package de.avalax.mtg_insight.port.adapter.service;

import org.junit.Test;

import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class ManaCostTokenTest {

    private void assertNoManaCost(ManaCostToken manaCostToken) {
        assertThat(manaCostToken.manaCost(), emptyCollectionOf(ManaCost.class));
        assertThat(manaCostToken.manaCost(), emptyCollectionOf(ManaCost.class));
    }

    private void assertMana(ManaCostToken manaCostToken, Mana... mana) {
        assertThat(manaCostToken.manaCost(), hasSize(mana.length));
        for (int i=0;i<mana.length;i++) {
            assertThat(manaCostToken.manaCost().get(i).mana(), hasSize(1));
            assertThat(manaCostToken.manaCost().get(i).mana(), hasItems(mana));
        }
    }

    private void assertHybridMana(ManaCostToken manaCostToken, Mana... mana) {
        assertThat(manaCostToken.manaCost(), hasSize(1));
        assertThat(manaCostToken.manaCost().get(0).mana(), hasSize(mana.length));
        assertThat(manaCostToken.manaCost().get(0).mana(), hasItems(mana));
    }

    @Test
    public void noManaCost_shouldReturnManaCostWithoutMana() throws Exception {
        assertNoManaCost(new ManaCostToken(null));
        assertNoManaCost(new ManaCostToken(""));
    }

    @Test
    public void singleManaToken_shouldReturnManaCostWithOneMana() throws Exception {
        assertMana(new ManaCostToken("W"), Mana.WHITE);
        assertMana(new ManaCostToken("R"), Mana.RED);
        assertMana(new ManaCostToken("G"), Mana.GREEN);
        assertMana(new ManaCostToken("B"), Mana.BLACK);
        assertMana(new ManaCostToken("U"), Mana.BLUE);
    }

    @Test
    public void colorlessManaToken_shouldReturnManaCostForColorlessNumber() throws Exception {
        assertMana(new ManaCostToken("0"));
        assertMana(new ManaCostToken("1"), Mana.COLORLESS);
        assertMana(new ManaCostToken("2"), Mana.COLORLESS, Mana.COLORLESS);
        assertMana(new ManaCostToken("9"), Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS, Mana.COLORLESS);
    }

    @Test
    public void hybridMana_shouldReturnManaCostWithTwoColoredMana() throws Exception {
        assertHybridMana(new ManaCostToken("W/R"), Mana.WHITE, Mana.RED);
        assertHybridMana(new ManaCostToken("B/U"), Mana.BLACK, Mana.BLUE);
        assertHybridMana(new ManaCostToken("2/U"), Mana.HYBRID_TWOCOLORLESS, Mana.BLUE);
    }
}