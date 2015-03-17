package de.avalax.mtg_insight.domain.model.card.spell;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.BasicCard;
import de.avalax.mtg_insight.domain.model.card.CardColor;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Spell extends BasicCard {
    public Spell(String name, Image image, List<CardColor> cardColors, List<ManaCost> convertedManaCost, int count) {
        super(name, image, cardColors, convertedManaCost, count);
    }
}
