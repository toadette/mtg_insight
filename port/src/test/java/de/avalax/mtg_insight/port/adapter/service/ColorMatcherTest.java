package de.avalax.mtg_insight.port.adapter.service;


import org.junit.Test;

import java.lang.reflect.Array;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class ColorMatcherTest {
    private void assertColors(List<Color> color,int numberOfColors, Color... cardColor) {
        assertThat(color, notNullValue());
        assertThat(color, hasSize(numberOfColors));
        assertThat(color, hasItems(cardColor));
    }

    @Test
    public void getColorFromColorString_shouldReturnOneColor() throws Exception {
        List<Color> color = new ColorMatcher().getColorFromArray(new String[]{"red"});
        assertColors(color,1, Color.RED);
    }

    @Test
    public void getColorFromColorString_shouldReturnTwoColors() throws Exception {
        List<Color> color = new ColorMatcher().getColorFromArray(new String[]{"white", "blue"});
        assertColors(color,2,Color.WHITE,Color.BLUE);
    }
}
