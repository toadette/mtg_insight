package de.avalax.mtg_insight.application.representation;

import java.util.Comparator;

public class CardRepresentationComparator implements Comparator<CardRepresentation> {
    @Override
    public int compare(CardRepresentation o1, CardRepresentation o2) {
        if (o1.convertedManaCostAsInteger() > o2.convertedManaCostAsInteger()) {
            return 1;
        }
        if (o1.convertedManaCostAsInteger() < o2.convertedManaCostAsInteger()) {
            return -1;
        }
        return o1.name().compareTo(o2.name());
    }
}
