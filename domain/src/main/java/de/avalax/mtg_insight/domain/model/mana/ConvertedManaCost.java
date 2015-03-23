package de.avalax.mtg_insight.domain.model.mana;

import java.util.List;

public class ConvertedManaCost {
    private final String manaCost;
    private final List<ManaCost> manaCostList;

    public ConvertedManaCost(String manaCost, List<ManaCost> manaCostList) {
        this.manaCost = manaCost;
        this.manaCostList = manaCostList;
    }

    public List<ManaCost> manaCostAsList() {
        return manaCostList;
    }

    @Override
    public String toString() {
        return manaCost;
    }
}
