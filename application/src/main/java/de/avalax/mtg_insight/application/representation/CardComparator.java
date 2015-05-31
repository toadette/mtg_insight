package de.avalax.mtg_insight.application.representation;

import java.util.Comparator;

import de.avalax.mtg_insight.domain.model.card.Card;

public class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        int compare = Integer.compare(o1.convertedManaCost().manaCostAsList().size(), o2.convertedManaCost().manaCostAsList().size());
        if (compare != 0) {
            return compare;
        }
        return o1.name().compareTo(o2.name());
    }
}
