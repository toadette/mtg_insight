package de.avalax.mtg_insight.domain.model.card;

public class Planeswalker extends Permanent {


    private final LoyaltyPoints loyaltyPoints;

    public Planeswalker(String cardName, LoyaltyPoints loyaltyPoints) {
        super(cardName);
        this.loyaltyPoints = loyaltyPoints;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }
}
