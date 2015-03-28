package de.avalax.mtg_insight.domain.model.card.spell.sorcery;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.spell.Spell;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class Sorcery extends Spell {
    public Sorcery(String name, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        super(name, cardColors, convertedManaCost);
    }
}
