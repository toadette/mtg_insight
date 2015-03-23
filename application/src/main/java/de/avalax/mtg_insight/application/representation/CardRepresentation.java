package de.avalax.mtg_insight.application.representation;

import de.avalax.mtg_insight.domain.model.card.Card;

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
}
