package de.avalax.mtg_insight.domain.model.card;

import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class BasicCard implements Card {
    @Override
    public String name() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Image image() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<CardColor> colorOfCard() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<ManaCost> convertedManaCost() {
        throw new RuntimeException("Not implemented");
    }
}
