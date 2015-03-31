package de.avalax.mtg_insight.domain.model.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class GenericCard implements Card {

    private final String name;
    private List<Color> cardColors;
    private ConvertedManaCost convertedManaCost;

    GenericCard(String name) {
        this.name = name;
        this.cardColors = new ArrayList<>();
        this.convertedManaCost = new ConvertedManaCost("", Collections.<ManaCost>emptyList());
    }

    @Deprecated
    public GenericCard(String name, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        this.name = name;
        this.cardColors = cardColors;
        this.convertedManaCost = convertedManaCost;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<Color> colorOfCard() {
        return cardColors;
    }

    @Override
    public ConvertedManaCost convertedManaCost() {
        return convertedManaCost;
    }

    void colorOfCard(List<Color> cardColors) {
        this.cardColors = cardColors;
    }

    void convertedManaCost(ConvertedManaCost convertedManaCost) {
        this.convertedManaCost = convertedManaCost;
    }
}
