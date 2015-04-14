package de.avalax.mtg_insight.port.adapter.service.gatherer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.card.CardCreator;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaCostToken;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class GathererHelper {


    //TODO: Loyaltypoints
    //TODO:description
    private AbilityTokenizer abilityTokenizer;
    private ColorMatcher colorMatcher;
    private ManaTokenizer manaTokenizer;

    public GathererHelper(AbilityTokenizer abilityTokenizer, ColorMatcher colorMatcher, ManaTokenizer manaTokenizer) {
        this.abilityTokenizer = abilityTokenizer;
        this.colorMatcher = colorMatcher;
        this.manaTokenizer = manaTokenizer;
    }

    public Card getCard(String type, String name, String description, List<String> manaRow, String powerToughness) {
        List<Color> colorOfCard = getColorOfCard(manaRow);
        ConvertedManaCost convertedManaCost = getConvertedManaCost(manaRow);
        String power = getPowerFromString(powerToughness);
        String toughness = getToughnessFromString(powerToughness);
        List<Ability> abilities = null;
        return new CardCreator().createCardFromType(type, name, colorOfCard, convertedManaCost, power, toughness, powerToughness, abilities);
    }

    private String getToughnessFromString(String powerToughness) {
        return powerToughness.substring(powerToughness.indexOf("/") + 1, powerToughness.length()).trim();
    }

    private String getPowerFromString(String powerToughness) {
        return powerToughness.substring(0, 1).trim();
    }


    private List<Ability> getAbilities(String description) {
        return abilityTokenizer.getAbilitiesFromDescription(description);
    }

    private List<Color> getColorOfCard(List<String> mana) {
        return colorMatcher.getColorFromArray(getColorArray(mana));
    }

    private Object[] getColorArray(List<String> mana) {
        List<String> colorList = new ArrayList<>();
        HashMap<String, String> colorMap = new HashMap<>();
        for (int i = 0; i < mana.size(); i++) {
            String manaStr = mana.get(i);
            if (manaStr.matches(".*or.*")) {
                int index = manaStr.indexOf("or");
                String mana1 = manaStr.substring(0, index - 1).trim();
                String mana2 = manaStr.substring(index + 2, manaStr.length()).trim();
                if (colorMap.get(mana1) == null) {
                    colorMap.put(mana1, mana1);
                }
                if (colorMap.get(mana2) == null) {
                    colorMap.put(mana2, mana2);
                }
            } else {
                if (colorMap.get(manaStr) == null) {
                    colorMap.put(manaStr, manaStr);
                }
            }
        }
        return colorMap.keySet().toArray();
    }

    private ConvertedManaCost getConvertedManaCost(List<String> manaRow) {
        String cmc = getConvertedManaCostString(manaRow);
        List<ManaCost> convertedManaCost = new ArrayList<>();
        for (ManaCostToken token : manaTokenizer.get(cmc)) {
            convertedManaCost.addAll(token.manaCost());
        }
        return new ConvertedManaCost(cmc, convertedManaCost);
    }

    private String getConvertedManaCostString(List<String> manaRow) {
        GathererManaCost gathererManaCost = new GathererManaCost();
        StringBuilder stringBuilder = new StringBuilder();
        for (String mana : manaRow) {
            stringBuilder.append(gathererManaCost.getManaStringFromGatherer(mana));
        }
        return stringBuilder.toString();
    }
}
