package de.avalax.mtg_insight.domain.model.card;

import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;

public class CardBuilder {
    private String cardName;
    private List<Color> colors;
    private ConvertedManaCost cmc;

    public CardBuilder(String cardName) {
        this.cardName = cardName;
        this.colors = Collections.emptyList();
    }

    public Card build() {
        GenericCard card = new GenericCard(cardName);
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
}
