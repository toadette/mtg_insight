package de.avalax.mtg_insight.domain.model.card.permanent.creature;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.permanent.Permanent;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Creature extends Permanent {
    public Creature(String name, Image image, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        super(name, image, cardColors, convertedManaCost);
    }
}
