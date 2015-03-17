package de.avalax.mtg_insight.domain.model.card.permanent.creature;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.CardColor;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.permanent.Permanent;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Creature extends Permanent {
    public Creature(String name, Image image, List<CardColor> cardColors, List<ManaCost> convertedManaCost, int count) {
        super(name, image, cardColors, convertedManaCost, count);
    }
}
