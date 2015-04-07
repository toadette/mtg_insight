package de.avalax.mtg_insight.application.representation;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;

public class CardRepresentation {
    private Card card;
    private int count;

    public CardRepresentation(Card card) {
        this.card = card;
        this.count = 1;
    }

    public void setCountOfCard(int count) {
        this.count = count;
    }

    public int count() {
        return count;
    }

    public String name() {
        return card.name();
    }

    public String convertedManaCost() {
        if (card.convertedManaCost() == null) {
            return "";
        }
        return card.convertedManaCost().toString();
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
            default:
                return CardColorRepresentation.COLORLESS;
        }
    }

    public boolean isCreature() {
        return card instanceof Creature;
    }

    public String power() {
        return isCreature() ? String.valueOf(creatureBody().power()) : null;
    }

    public String toughness() {
        return isCreature() ? String.valueOf(creatureBody().toughness()) : null;
    }

    private CreatureBody creatureBody() {
        return ((Creature) card).creatureBody();
    }
}
