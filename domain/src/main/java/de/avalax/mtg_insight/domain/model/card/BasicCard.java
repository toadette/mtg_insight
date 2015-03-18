package de.avalax.mtg_insight.domain.model.card;

import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class BasicCard implements Card {

    private final String name;
    private final Image image;
    private final List<CardColor> cardColors;
    private final List<ManaCost> convertedManaCost;

    public BasicCard(String name, Image image, List<CardColor> cardColors, List<ManaCost> convertedManaCost) {

        this.name = name;
        this.image = image;
        this.cardColors = cardColors;
        this.convertedManaCost = convertedManaCost;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Image image() {
        return image;
    }

    @Override
    public List<CardColor> colorOfCard() {
        return cardColors;
    }

    @Override
    public List<ManaCost> convertedManaCost() {
        return convertedManaCost;
    }
}
