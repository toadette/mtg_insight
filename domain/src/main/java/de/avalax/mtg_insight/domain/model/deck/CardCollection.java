package de.avalax.mtg_insight.domain.model.deck;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.Permanent;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Spell;

public class CardCollection implements Iterable<Card> {
    private List<Card> deck;
    private List<Creature> creatures;
    private List<Spell> spells;
    private List<Permanent> permanents;
    private List<Planeswalker> planeswalkers;
    private List<Land> lands;

    public CardCollection() {
        this.deck = new ArrayList<>();
        this.creatures = new ArrayList<>();
        this.spells = new ArrayList<>();
        this.permanents = new ArrayList<>();
        this.planeswalkers = new ArrayList<>();
        this.lands = new ArrayList<>();
    }

    @Override
    public Iterator<Card> iterator() {
        return deck.iterator();
    }

    public void addAll(List<Card> deck) {
        for (Card card : deck) {
            add(card);
        }
    }

    public void add(Card card) {
        deck.add(card);
        if (card instanceof Creature) {
            creatures.add((Creature) card);
        } else if (card instanceof Spell) {
            spells.add((Spell) card);
        } else if (card instanceof Planeswalker) {
            planeswalkers.add((Planeswalker) card);
        } else if (card instanceof Land) {
            lands.add((Land) card);
        } else if (card instanceof Permanent) {
            permanents.add((Permanent) card);
        }
    }

    public List<Creature> creatures() {
        return creatures;
    }

    public List<Spell> spells() {
        return spells;
    }

    public List<Permanent> permanents() {
        return permanents;
    }

    public List<Planeswalker> planeswalkers() {
        return planeswalkers;
    }

    public List<Land> lands() {
        return lands;
    }

    public int size() {
        return deck.size();
    }
}
