package de.avalax.mtg_insight.domain.model.card;

import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public interface Card {
    String name();

    List<Color> colorOfCard();

    ConvertedManaCost convertedManaCost();
}
