package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.jsoup.nodes.Element;

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


    private AbilityTokenizer abilityTokenizer;
    private ColorMatcher colorMatcher;
    private ManaTokenizer manaTokenizer;

    public GathererHelper(AbilityTokenizer abilityTokenizer, ColorMatcher colorMatcher, ManaTokenizer manaTokenizer) {
        this.abilityTokenizer = abilityTokenizer;
        this.colorMatcher = colorMatcher;
        this.manaTokenizer = manaTokenizer;
    }

    private List<Ability> getAbilities(String description) {
        return abilityTokenizer.getAbilitiesFromDescription(description);
    }

    private List<Color> getColorOfCard(Element manaRow) {
        HashMap<String, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < manaRow.children().size(); i++) {
            String token = manaRow.children().get(i).attr("alt");
            if (colorMap.get(token) == null && !token.matches("\\d")) {
                colorMap.put(token, i);
            }
        }
        return colorMatcher.getColorFromArray(colorMap.keySet().toArray());
    }

    private ConvertedManaCost getConvertedManaCost(Element manaRow) {
        String cmc = new String();
        List<ManaCost> convertedManaCost = new ArrayList<>();
        for (ManaCostToken token : manaTokenizer.get(cmc)) {
            convertedManaCost.addAll(token.manaCost());
        }
        return new ConvertedManaCost(cmc, convertedManaCost);
    }

    public Card getCard(String type, String name, String description, List<String> manaRow) {
        List<Color> colorOfCard = null;
        ConvertedManaCost convertedManaCost = null;
        List<Ability> abilities = null;
        return new CardCreator().createCardFromType(type, name, colorOfCard, convertedManaCost, "0", "0", "0", abilities);
    }
}
