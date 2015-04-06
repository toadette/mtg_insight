package de.avalax.mtg_insight.domain.model.card;

import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class CardBuilder {
    private String cardName;
    private List<Color> colors;
    private ConvertedManaCost cmc;
    private CreatureBody creatureBody;
    private Class<?> cardType;
    private LoyaltyPoints loyaltyPoints;
    private List<Ability> abilities;

    public CardBuilder(String cardName) {
        this.cardName = cardName;
        this.colors = Collections.emptyList();
        this.cardType = GenericCard.class;
    }

    public Card build() {
        GenericCard card;
        if (cardType.equals(Creature.class)) {
            card = new Creature(cardName, creatureBody,abilities);
        } else if (cardType.equals(Artifact.class)) {
            card = new Artifact(cardName);
        } else if (cardType.equals(Land.class)) {
            card = new Land(cardName);
        } else if (cardType.equals(Enchantment.class)) {
            card = new Enchantment(cardName);
        } else if (cardType.equals(Planeswalker.class)) {
            card = new Planeswalker(cardName,loyaltyPoints);
        } else if (cardType.equals(Instant.class)) {
            card = new Instant(cardName);
        } else if (cardType.equals(Sorcery.class)) {
            card = new Sorcery(cardName);
        } else {
            card = new GenericCard(cardName);
        }

        card.colorOfCard(colors);
        card.convertedManaCost(cmc);
        return card;
    }

    public CardBuilder cardColors(List<Color> colors) {
        this.colors = colors;
        return this;
    }

    public CardBuilder convertedManaCost(ConvertedManaCost cmc) {
        this.cmc = cmc;
        return this;
    }

    public CardBuilder creatureCard(CreatureBody creatureBody, List<Ability> abilities) {
        this.abilities = abilities;
        this.cardType = Creature.class;
        this.creatureBody = creatureBody;
        return this;
    }

    public CardBuilder artifactCard() {
        this.cardType = Artifact.class;
        return this;
    }

    public CardBuilder landCard() {
        this.cardType = Land.class;
        return this;
    }

    public CardBuilder enchantmentCard() {
        this.cardType = Enchantment.class;
        return this;
    }

    public CardBuilder planeswalkerCard(LoyaltyPoints loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        this.cardType = Planeswalker.class;
        return this;
    }

    public CardBuilder instantCard() {
        this.cardType = Instant.class;
        return this;
    }

    public CardBuilder sorceryCard() {
        this.cardType = Sorcery.class;
        return this;
    }
}
