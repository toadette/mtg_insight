package de.avalax.mtg_insight.domain.model.card.spell;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class Spell extends GenericCard {
    public Spell(String name, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        super(name, cardColors, convertedManaCost);
    }
}
