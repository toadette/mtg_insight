package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class GathererManaCostTest {

    private GathererManaCost gathererManaCost;

    @Before
    public void setUp() throws Exception {
        gathererManaCost = new GathererManaCost();
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnRed() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Red");
        assertThat(expectedResult, equalTo("R"));
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnBlue() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Blue");
        assertThat(expectedResult, equalTo("U"));
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnBlack() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Black");
        assertThat(expectedResult, equalTo("B"));
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnColorless() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("3");
        assertThat(expectedResult, equalTo("3"));
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnHybridMana() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Red or White");
        assertThat(expectedResult, equalTo("{R/W}"));
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnHybridMana2() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Two or Blue");
        assertThat(expectedResult, equalTo("{2/U}"));
    }

    @Test
    public void testGetManaStringFromGatherer_shouldReturnPhyrexianMana() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Phyrexian Black");
        assertThat(expectedResult, equalTo("{PB}"));
    }
    @Test
    public void testGetManaStringFromGatherer_shouldReturnPhyrexianMana2() throws Exception {
        String expectedResult = gathererManaCost.getManaStringFromGatherer("Phyrexian Blue");
        assertThat(expectedResult, equalTo("{PU}"));
    }
}