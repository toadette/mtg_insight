package de.avalax.mtg_insight.domain.model.card;

public class CreatureBody {

    private final int power;
    private final int toughness;

    public CreatureBody(int power, int toughness) {
        this.power = power;
        this.toughness = toughness;
    }

    public int power() {
        return power;
    }

    public int toughness() {
        return toughness;
    }
}
