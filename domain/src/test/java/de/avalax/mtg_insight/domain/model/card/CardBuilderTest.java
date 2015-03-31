package de.avalax.mtg_insight.domain.model.card;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.assertj.core.api.Assertions.assertThat;


public class CardBuilderTest {
    private CardBuilder cardBuilder;
    private String newcardname;

    @Before
    public void setUp() throws Exception {
        newcardname = "newcardname";
        cardBuilder = new CardBuilder(newcardname);
    }

    @Test
    public void onlyCardNameGiven_shouldBuildGenericCardWithName() throws Exception {
        Card card = cardBuilder.build();

        assertThat(card).isInstanceOf(GenericCard.class);
        assertThat(card.name()).isEqualTo(newcardname);
        assertThat(card.colorOfCard()).isEmpty();
    }

    @Test
    public void colorGiven_shouldBuildGenericCardWithCardColorIncluded() throws Exception {
        List<Color> colors = Collections.emptyList();
        CardBuilder sameCardBuilder = cardBuilder.cardColors(colors);

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(GenericCard.class);
        assertThat(card.colorOfCard()).isEqualTo(colors);
    }

    @Test
    public void convertedMandCostGiven_shouldBuildGenericCardWithConvertedManaCostIncluded() throws Exception {
        ConvertedManaCost cmc = new ConvertedManaCost("manaCost", Collections.<ManaCost>emptyList());
        CardBuilder sameCardBuilder = cardBuilder.convertedManaCost(cmc);

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(GenericCard.class);
        assertThat(card.convertedManaCost()).isEqualTo(cmc);
    }
}