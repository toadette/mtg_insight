package de.avalax.mtg_insight.domain.model.card;

public class Creature extends Permanent {

    private final CreatureBody creatureBody;

    Creature(String name, CreatureBody creatureBody) {
        super(name);
        this.creatureBody = creatureBody;
    }

    public CreatureBody creatureBody() {
        return creatureBody;
    }
}
