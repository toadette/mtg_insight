package de.avalax.mtg_insight.domain.model.card.permanent;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.BasicCard;
import de.avalax.mtg_insight.domain.model.card.CardColor;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class Permanent extends BasicCard {

    public Permanent(String name, Image image, List<CardColor> cardColors, List<ManaCost> convertedManaCost) {
        super(name, image, cardColors, convertedManaCost);
    }
}
