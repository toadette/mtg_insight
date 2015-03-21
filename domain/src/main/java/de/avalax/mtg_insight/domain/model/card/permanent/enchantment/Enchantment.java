package de.avalax.mtg_insight.domain.model.card.permanent.enchantment;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.permanent.Permanent;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Enchantment extends Permanent {
    public Enchantment(String name, Image image, List<Color> cardColors, List<ManaCost> convertedManaCost) {
        super(name, image, cardColors, convertedManaCost);
    }
}
