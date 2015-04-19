package de.avalax.mtg_insight.domain.model.card;

public class LoyaltyPoints {
    private int loyaltyPoints;

    public LoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public int loyaltyPoints() {
        return loyaltyPoints;
    }
}
