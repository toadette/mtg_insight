package de.avalax.mtg_insight.domain.model.card;

import java.util.List;

public class Creature extends Permanent {

    private final CreatureBody creatureBody;
    private final List<Ability> abilities;

    Creature(String name, CreatureBody creatureBody, List<Ability> abilities) {
        super(name);
        this.creatureBody = creatureBody;
        this.abilities = abilities;
    }

    public CreatureBody creatureBody() {
        return creatureBody;
    }

    public List<Ability> abilities() {
        return abilities;
    }
}
