package de.avalax.mtg_insight.domain.model.mana;

import java.util.List;

public class ManaCost {
    private List<Mana> mana;

    public ManaCost(List<Mana> mana) {

        this.mana = mana;
    }

    public List<Mana> mana() {
        return mana;
    }
}
