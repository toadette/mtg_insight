package de.avalax.mtg_insight.domain.model.mana;

import java.util.Collections;
import java.util.List;

public class ManaCost {
    private List<Mana> mana;
    private String manaString;

    public ManaCost(List<Mana> mana, String manaString) {
        this.mana = mana;
        this.manaString = manaString;
    }

    public List<Mana> mana() {
        return Collections.unmodifiableList(mana);
    }

    public String manaAsString() {
        return manaString;
    }
}
