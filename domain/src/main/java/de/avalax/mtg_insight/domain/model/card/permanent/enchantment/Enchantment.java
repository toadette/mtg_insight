package de.avalax.mtg_insight.domain.model.card.permanent.enchantment;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.CardColor;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.permanent.Permanent;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Enchantment extends Permanent {
    public Enchantment(String name, Image image, List<CardColor> cardColors, List<ManaCost> convertedManaCost, int count) {
        super(name, image, cardColors, convertedManaCost, count);
    }
}
