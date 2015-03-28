package de.avalax.mtg_insight.port.adapter.service.card;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.artifact.Artifact;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.card.permanent.enchantment.Enchantment;
import de.avalax.mtg_insight.domain.model.card.permanent.land.Land;
import de.avalax.mtg_insight.domain.model.card.permanent.planeswalker.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.spell.instant.Instant;
import de.avalax.mtg_insight.domain.model.card.spell.sorcery.Sorcery;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class CardBuilder {
    private static final String CARD_TYPE_ARTIFACT = "Artifact";
    private static final String CARD_TYPE_CREATURE = "Creature";
    private static final String CARD_TYPE_ENCHANTMENT = "Enchantment";
    private static final String CARD_TYPE_LAND = "Land";
    private static final String CARD_TYPE_PLANESWALKER = "Planeswalker";
    private static final String CARD_TYPE_INSTANT = "Instant";
    private static final String CARD_TYPE_SORCERY = "Sorcery";


    public Card createCardFromType(String type, String name, List<Color> cardColors, ConvertedManaCost convertedManaCost) {
        if (isCardFromType(type, CARD_TYPE_CREATURE)) {
            return new Creature(name, cardColors, convertedManaCost);
        }
        if (isCardFromType(type, CARD_TYPE_ARTIFACT)) {
            return new Artifact(name, cardColors, convertedManaCost);
        }
        if (isCardFromType(type, CARD_TYPE_LAND)) {
            return new Land(name, cardColors, convertedManaCost);
        }
        if (isCardFromType(type, CARD_TYPE_ENCHANTMENT)) {
            return new Enchantment(name, cardColors, convertedManaCost);
        }
        if(isCardFromType(type,CARD_TYPE_PLANESWALKER)){
            return new Planeswalker(name,cardColors,convertedManaCost);
        }
        if(isCardFromType(type,CARD_TYPE_INSTANT)){
            return new Instant(name,cardColors,convertedManaCost);
        }
        if(isCardFromType(type,CARD_TYPE_SORCERY)){
            return new Sorcery(name,cardColors,convertedManaCost);
        }
        return null;
    }

    private boolean isCardFromType(String cardType, String type) {
        return cardType.equals(type) | cardType.contains(type);
    }
}
