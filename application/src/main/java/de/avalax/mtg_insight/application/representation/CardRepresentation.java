package de.avalax.mtg_insight.application.representation;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;

public class CardRepresentation {
    private static final String STAR_PT = "*";
    private static final int INITIAL_COUNT_OF_CARD = 1;
    private Card card;
    private int countOfCard;
    private String drawPercentage;

    public CardRepresentation(Card card) {
        this.card = card;
        this.countOfCard = INITIAL_COUNT_OF_CARD;
    }

    public void setCountOfCard(int countOfCard) {
        this.countOfCard = countOfCard;
    }

    public int count() {
        return countOfCard;
    }

    public String name() {
        return card.name();
    }

    public String convertedManaCost() {
        return card.convertedManaCost().toString();
    }

    public CardColorRepresentation color() {
        if (isColorless()) {
            return CardColorRepresentation.COLORLESS;
        }
        if (isMulticolored()) {
            return CardColorRepresentation.MULTICOLOR;
        }
        return cardColorRepresentationForFirstColor();
    }

    private CardColorRepresentation cardColorRepresentationForFirstColor() {
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

    private boolean isColorless() {
        return card.colorOfCard().isEmpty();
    }

    private boolean isMulticolored() {
        return card.colorOfCard().size() > 1;
    }

    public String powerToughness() {
        if (isCreature()) {
            String power = bodyAttribute(creatureBody().power());
            String toughness = bodyAttribute(creatureBody().toughness());
            return powerToughnessString(power, toughness);
        }
        if (isPlaneswalker()) {
            int loyaltyPoints = loyaltyPoints().loyaltyPoints();
            return loyaltyPointsString(loyaltyPoints);
        }
        return "";
    }

    private String loyaltyPointsString(int loyaltyPoints) {
        return String.valueOf(loyaltyPoints);
    }

    private LoyaltyPoints loyaltyPoints() {
        return ((Planeswalker) card).loyaltyPoints();
    }

    private String powerToughnessString(String power, String toughness) {
        return power + " / " + toughness;
    }

    private boolean isCreature() {
        return card instanceof Creature;
    }

    public boolean isCreatureOrPlaneswalker() {
        return isCreature() || isPlaneswalker();
    }

    private boolean isPlaneswalker() {
        return card instanceof Planeswalker;
    }

    private String bodyAttribute(int powerToughness) {
        return powerToughness == -1 ? STAR_PT : String.valueOf(powerToughness);
    }

    private CreatureBody creatureBody() {
        return ((Creature) card).creatureBody();
    }

    public int convertedManaCostAsInteger() {
        return card.convertedManaCost().manaCostAsList().size();
    }

    public void setDrawPercentage(double drawPercentage) {
        this.drawPercentage = String.valueOf(drawPercentage);
    }

    public String getDrawPercentage() {
        return drawPercentage;
    }
}
