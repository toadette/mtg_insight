package de.avalax.mtg_insight.port.adapter.service.ability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;


public class AbilityTokenizer {
    public List<Ability> getAbilitiesFromDescription(String description) {
        if(description.contains("\\n")){
            return new ArrayList<>(2);
        }
        return Collections.emptyList();
    }
}
