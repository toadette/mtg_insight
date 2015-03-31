package de.avalax.mtg_insight.domain.model.card.permanent;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class Permanent extends GenericCard {

    public Permanent(String name, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        super(name, cardColors, convertedManaCost);
    }
}
