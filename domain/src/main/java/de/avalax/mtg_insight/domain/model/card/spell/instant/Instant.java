package de.avalax.mtg_insight.domain.model.card.spell.instant;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.CardColor;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.spell.Spell;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Instant extends Spell {
    public Instant(String name, Image image, List<CardColor> cardColors, List<ManaCost> convertedManaCost, int count) {
        super(name, image, cardColors, convertedManaCost, count);
    }
}
