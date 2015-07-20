package de.avalax.mtg_insight.domain.model.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
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
        //TODO: forbid null
        this.convertedManaCost = convertedManaCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericCard that = (GenericCard) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "GenericCard{" +
                "name='" + name + '\'' +
                '}';
    }
}
