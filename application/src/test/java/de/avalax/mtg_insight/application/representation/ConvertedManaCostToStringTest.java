package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ConvertedManaCostToStringTest {
    private ConvertedManaCostToString convertedManaCostToString;

    private List<ManaCost> cmc(ManaCost... manaCost) {
        return Arrays.asList(manaCost);
    }

    private ManaCost manaString(String manaString) {
        return new ManaCost(Collections.<Mana>emptyList(), manaString);
    }

    @Before
    public void setUp() throws Exception {
        convertedManaCostToString = new ConvertedManaCostToString();
    }

    @Test
    public void convertedManaCost_shouldHaveStringRepresentation() throws Exception {
        assertThat(convertedManaCostToString.convertToString(null), equalTo("0"));
        assertThat(convertedManaCostToString.convertToString(Collections.<ManaCost>emptyList()), equalTo("0"));
        assertThat(convertedManaCostToString.convertToString(cmc(manaString("R"))), equalTo("R"));
        assertThat(convertedManaCostToString.convertToString(cmc(manaString("R"),manaString("{G/R}"),manaString("3"))), equalTo("R{G/R}3"));
    }
}