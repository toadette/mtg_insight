package de.avalax.mtg_insight.domain.model.card;

import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public interface Card {
    String name();

    Image image();

    List<CardColor> colorOfCard();

    List<ManaCost> convertedManaCost();
}
