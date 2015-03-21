package de.avalax.mtg_insight.domain.model.card.permanent.planeswalker;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.permanent.Permanent;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Planeswalker  extends Permanent {
    public Planeswalker(String name, Image image, List<Color> cardColors, List<ManaCost> convertedManaCost) {
        super(name, image, cardColors, convertedManaCost);
    }
}
