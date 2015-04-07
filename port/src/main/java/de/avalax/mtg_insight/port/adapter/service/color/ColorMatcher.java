package de.avalax.mtg_insight.port.adapter.service.color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;

public class ColorMatcher {
    public List<Color> getColorFromArray(Object[] colorArray) {
        List<Color> colorList = new ArrayList<>();
        for (Object color : colorArray) {
            Color newColor = getColor(color.toString());
            if (newColor != null) {
                colorList.add(newColor);
            }
        }
        return Collections.unmodifiableList(colorList);
    }

    private Color getColor(String colorString) {
        if (colorString.equalsIgnoreCase("white")) {
            return Color.WHITE;
        }
        if (colorString.equalsIgnoreCase("blue")) {
            return Color.BLUE;
        }
        if (colorString.equalsIgnoreCase("black")) {
            return Color.BLACK;
        }
        if (colorString.equalsIgnoreCase("green")) {
            return Color.GREEN;
        }
        if (colorString.equalsIgnoreCase("red")) {
            return Color.RED;
        }
        return null;
    }
}
