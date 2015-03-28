package de.avalax.mtg_insight.domain.model.card.permanent.planeswalker;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.permanent.Permanent;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class Planeswalker  extends Permanent {
    public Planeswalker(String name, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        super(name, cardColors, convertedManaCost);
    }
}
