package de.avalax.mtg_insight.port.adapter.service.card;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class CardCreator {
    private static final String CARD_TYPE_ARTIFACT = "Artifact";
    private static final String CARD_TYPE_CREATURE = "Creature";
    private static final String CARD_TYPE_ENCHANTMENT = "Enchantment";
    private static final String CARD_TYPE_LAND = "Land";
    private static final String CARD_TYPE_PLANESWALKER = "Planeswalker";
    private static final String CARD_TYPE_INSTANT = "Instant";
    private static final String CARD_TYPE_SORCERY = "Sorcery";


    public Card createCardFromType(String type, String name, List<Color> cardColors, ConvertedManaCost convertedManaCost, final String power, final String toughness, final String loyalty, List<Ability> abilities) {
        CardBuilder cardBuilder = new CardBuilder(name);
        cardBuilder.convertedManaCost(convertedManaCost);
        cardBuilder.cardColors(cardColors);
        if (isCardFromType(type, CARD_TYPE_CREATURE)) {
            CreatureBody creatureBody = new CreatureBody(power.equals("*") ? -1 : Integer.valueOf(power), toughness.equals("*") ? -1 : Integer.valueOf(toughness));
            return cardBuilder.creatureCard(creatureBody, abilities).build();
        }
        if (isCardFromType(type, CARD_TYPE_ARTIFACT)) {
            return cardBuilder.artifactCard().build();
        }
        if (isCardFromType(type, CARD_TYPE_LAND)) {
            return cardBuilder.landCard().build();
        }
        if (isCardFromType(type, CARD_TYPE_ENCHANTMENT)) {
            return cardBuilder.enchantmentCard().build();
        }
        if (isCardFromType(type, CARD_TYPE_PLANESWALKER)) {
            LoyaltyPoints loyaltyPoints = new LoyaltyPoints(Integer.valueOf(loyalty));
            return cardBuilder.planeswalkerCard(loyaltyPoints).build();
        }
        if (isCardFromType(type, CARD_TYPE_INSTANT)) {
            return cardBuilder.instantCard().build();
        }
        if (isCardFromType(type, CARD_TYPE_SORCERY)) {
            return cardBuilder.sorceryCard().build();
        }
        return cardBuilder.build();
    }

    private boolean isCardFromType(String cardType, String type) {
        return cardType != null && (cardType.equals(type) || cardType.contains(type));
    }
}
