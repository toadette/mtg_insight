package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ConvertedManaCostToStringTest {
    private ConvertedManaCostToString convertedManaCostToString;

    @Before
    public void setUp() throws Exception {
        convertedManaCostToString = new ConvertedManaCostToString();
    }

    @Test
    public void convertedManaCost_shouldHaveStringRepresentation() throws Exception {
        assertThat(convertedManaCostToString.convertToString(Collections.<ManaCost>emptyList()), equalTo("0"));
        //TODO: cmc to string
        //assertThat(convertedManaCostToString.convertToString(cmc(manaCost(Mana.RED))), equalTo("R"));
    }

    private List<ManaCost> cmc(ManaCost... manaCost) {
        return Arrays.asList(manaCost);
    }

    private ManaCost manaCost(Mana... mana) {
        return new ManaCost(Arrays.asList(mana));
    }
}