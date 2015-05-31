package de.avalax.mtg_insight.port.adapter.service.ability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;


public class AbilityTokenizer {

    public static final String NEWLINE = "\\n";
    public static final String RETURN = "\r";
    public static final String TAB = "\t";

    public List<Ability> getAbilitiesFromDescription(String description) {
        return getAbilities(getCleanString(description));
    }

    private List<Ability> getAbilities(String description) {
        List<Ability> abilities = new ArrayList<>();
        String[] split = description.split(",");
        if (split.length > 0) {
            for (String abilityString : split) {
                abilities.add(getAbility(abilityString.trim()));
            }
        }else{
            abilities.add(getAbility(description));
        }
        return abilities;
    }

    private Ability getAbility(String abilityString) {
        if ("First strike".equalsIgnoreCase(abilityString)) {
            return Ability.FIRST_STRIKE;
        }
        if ("hexproof".equalsIgnoreCase(abilityString)) {
            return Ability.HEXPROOF;
        }
        if("Vigilance".equalsIgnoreCase(abilityString)){
            return Ability.VIGILANCE;
        }
        return Ability.FIRST_STRIKE;
    }

    private String getCleanString(String description) {
        return description.replace(NEWLINE, "").replace(RETURN, "").replace(TAB, "");
    }
}
