package de.avalax.mtg_insight.domain.model.card.spell.sorcery;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.spell.Spell;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Sorcery extends Spell {
    public Sorcery(String name, Image image, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        super(name, image, cardColors, convertedManaCost);
    }
}
