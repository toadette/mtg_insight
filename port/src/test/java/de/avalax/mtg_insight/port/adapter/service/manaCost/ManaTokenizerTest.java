package de.avalax.mtg_insight.port.adapter.service.manaCost;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaCostToken;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class ManaTokenizerTest {

    private void assertNoTokens(String manaCostString) {
        List<ManaCostToken> manaCostTokens = manaTokenizer.get(manaCostString);
        assertThat(manaCostTokens, emptyCollectionOf(ManaCostToken.class));
    }

    private void assertToken(String manaCostString, String... expectedManaCostString) {
        List<ManaCostToken> manaCostTokens = manaTokenizer.get(manaCostString);
        assertThat(manaCostTokens, hasSize(expectedManaCostString.length));
        for (int i = 0; i < expectedManaCostString.length; i++) {
            assertThat(manaCostTokens.get(i).manaString(), equalTo(expectedManaCostString[i]));
        }
    }

    private ManaTokenizer manaTokenizer;

    @Before
    public void setUp() throws Exception {
        manaTokenizer = new ManaTokenizer();
    }

    @Test
    public void emptyString_shouldReturnNoTokens() throws Exception {
        assertNoTokens("");
        assertNoTokens(null);
    }

    @Test
    public void singleMana_shouldReturnTokenList() throws Exception {
        assertToken("W", "W");
        assertToken("5", "5");
        assertToken("WW", "W", "W");
        assertToken("3WUR", "3", "W", "U", "R");
    }

    @Test
    public void hybridMana_shouldReturnTokenList() throws Exception {
        assertToken("{W/R}", "W/R");
        assertToken("3{W/R}", "3", "W/R");
        assertToken("{W/R}3", "W/R", "3");
        assertToken("{G/B}{G/B}", "G/B", "G/B");
        assertToken("{U/R}{U/R}{W/R}", "U/R", "U/R", "W/R");
        assertToken("{2/R}", "2/R");
    }

    @Test
    public void phyrexianMana_shouldReturnTokenList() throws Exception {
        assertToken("{RP}", "RP");
        assertToken("{GP}", "GP");
        assertToken("3{UP}", "3", "UP");
        assertToken("{UP}3", "UP", "3");
        assertToken("{UP}{WP}", "UP", "WP");
        assertToken("{W/R}3{WP}", "W/R", "3", "WP");


    }
}