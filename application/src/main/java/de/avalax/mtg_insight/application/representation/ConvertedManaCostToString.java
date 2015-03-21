package de.avalax.mtg_insight.application.representation;

import java.util.List;

import de.avalax.mtg_insight.domain.model.mana.ManaCost;

public class ConvertedManaCostToString {
    public String convertToString(List<ManaCost> manaCosts) {
        if (manaCosts == null || manaCosts.isEmpty()) {
            return "0";
        }
        String cmc = "";
        for (ManaCost manaCost : manaCosts) {
            cmc += manaCost.manaAsString();
        }
        return cmc;
    }
}
