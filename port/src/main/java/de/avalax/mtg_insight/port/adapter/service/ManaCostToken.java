package de.avalax.mtg_insight.port.adapter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class ManaCostToken {
    private static final String COLORLESS_MANA = "^\\d$";
    private static final String HYBRID_MANA = "^[A-Z2]/[A-Z2]$";
    private String manaString;

    public ManaCostToken(String manaString) {
        this.manaString = manaString;
    }

    public String manaString() {
        return manaString;
    }

    public List<ManaCost> manaCost() {
        if (manaString == null || manaString.isEmpty()) {
            return Collections.emptyList();
        }

        List<ManaCost> manaCosts = new ArrayList<>();
        if (manaString.matches(COLORLESS_MANA)) {
            for (int i = 0; i < Integer.parseInt(manaString); i++) {
                manaCosts.add(new ManaCost(Collections.singletonList(manaCostFromManaString(manaString))));
            }
        } else if (manaString.matches(HYBRID_MANA)) {
            String[] hybridMana = manaString.split("/");
            manaCosts.add(new ManaCost(Arrays.asList(hybridManaCostFromManaString(hybridMana[0]), hybridManaCostFromManaString(hybridMana[1]))));
        } else {
            manaCosts.add(new ManaCost(Collections.singletonList(manaCostFromManaString(manaString))));
        }
        return manaCosts;
    }

    private Mana manaCostFromManaString(String manaString) {
        if ("W".equals(manaString)) {
            return Mana.WHITE;
        }
        if ("U".equals(manaString)) {
            return Mana.BLUE;
        }
        if ("R".equals(manaString)) {
            return Mana.RED;
        }
        if ("B".equals(manaString)) {
            return Mana.BLACK;
        }
        if ("G".equals(manaString)) {
            return Mana.GREEN;
        }
        return Mana.COLORLESS;

    }
    private Mana hybridManaCostFromManaString(String manaString) {
        if ("2".equals(manaString)) {
            return Mana.HYBRID_TWOCOLORLESS;
        }
        return manaCostFromManaString(manaString);

    }
}
