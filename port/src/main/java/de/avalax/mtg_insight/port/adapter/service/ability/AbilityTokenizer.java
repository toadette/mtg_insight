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
        if (description == "") {
            return abilities;
        }
        String[] split = description.split(",");
        if (split.length > 1) {
            for (String abilityString : split) {
                abilities.add(getAbility(abilityString.trim()));
            }
        } else {
            abilities.add(getAbility(description));
        }
        return abilities;
    }

    private Ability getAbility(String abilityString) {
        if (containsOrEquals("First strike",abilityString)) {
            return Ability.FIRST_STRIKE;
        }
        if (containsOrEquals("hexproof",abilityString)) {
            return Ability.HEXPROOF;
        }
        if (containsOrEquals("Vigilance",abilityString)) {
            return Ability.VIGILANCE;
        }
        if (containsOrEquals("Flying",abilityString)) {
            return Ability.FLYING;
        }
        if (containsOrEquals("deathtouch",abilityString)) {
            return Ability.DEATHTOUCH;
        }
        if (containsOrEquals("indestructible",abilityString)) {
            return Ability.INDESTRUCTIBLE;
        }
        if (containsOrEquals("defender",abilityString)) {
            return Ability.DEFENDER;
        }
        if (containsOrEquals("double strike",abilityString)) {
            return Ability.DOUBLE_STRIKE;
        }
        if (containsOrEquals("flash",abilityString)) {
            return Ability.FLASH;
        }
        if (containsOrEquals("intimidate",abilityString)) {
            return Ability.INTIMIDATE;
        }
        if (containsOrEquals("landwalk",abilityString)) {
            return Ability.LANDWALK;
        }
        if (containsOrEquals("lifelink",abilityString)) {
            return Ability.LIFELINK;
        }
        if (containsOrEquals("protection",abilityString)) {
            return Ability.PROTECTION;
        }
        if (containsOrEquals("reach",abilityString)) {
            return Ability.REACH;
        }
        if (containsOrEquals("shroud", abilityString)) {
            return Ability.SHROUD;
        }
        if (containsOrEquals("trample", abilityString)) {
            return Ability.TRAMPLE;
        }
        if (containsOrEquals("haste",abilityString)) {
            return Ability.HASTE;
        }
        return Ability.FIRST_STRIKE;
    }
    private boolean containsOrEquals(String token,String string){
        return token.equalsIgnoreCase(string) || string.toLowerCase().contains(token);
    }
    private String getCleanString(String description) {
        return description.replace(NEWLINE, "").replace(RETURN, "").replace(TAB, "");
    }
}
