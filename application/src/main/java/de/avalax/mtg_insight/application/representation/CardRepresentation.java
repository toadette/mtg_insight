package de.avalax.mtg_insight.application.representation;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.mana.Mana;

public class CardRepresentation {
    private Card card;

    public CardRepresentation(Card card) {
        this.card = card;
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

    public CardColorRepresentation color() {
        if (card.colorOfCard().isEmpty()) {
            return CardColorRepresentation.COLORLESS;
        }
        if (card.colorOfCard().size() > 1) {
            return CardColorRepresentation.MULTICOLOR;
        }
        switch (card.colorOfCard().get(0)) {
            case WHITE:
                return CardColorRepresentation.WHITE;
            case BLUE:
                return CardColorRepresentation.BLUE;
            case BLACK:
                return CardColorRepresentation.BLACK;
            case RED:
                return CardColorRepresentation.RED;
            case GREEN:
                return CardColorRepresentation.GREEN;
            case COLORLESS:
            case PHYREXIAN:
                default:
                return CardColorRepresentation.COLORLESS;
        }
    }
}
