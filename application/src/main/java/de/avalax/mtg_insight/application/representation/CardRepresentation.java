package de.avalax.mtg_insight.application.representation;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;

public class CardRepresentation {
    public CardRepresentation(Card card) {
    }

    public int count() {
        return 2;
    }

    public String name() {
        return "fake name";
    }

    public String convertedManaCost() {
        return "ABC";
    }
}
