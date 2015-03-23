package de.avalax.mtg_insight.domain.model.mana;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ConvertedManaCostTest {
    @Test
    public void toString_shouldReturnManaCost() throws Exception {
        ConvertedManaCost convertedManaCost = new ConvertedManaCost("GGG", null);

        assertThat(convertedManaCost.toString(), equalTo("GGG"));
    }

    @Test
    public void manaCostAsList_shouldReturnManaCostAsList() throws Exception {
        List<ManaCost> manaCostList = new ArrayList<>();
        ConvertedManaCost convertedManaCost = new ConvertedManaCost("GGG", manaCostList);

        assertThat(convertedManaCost.manaCostAsList(), equalTo(manaCostList));
    }
}