package de.avalax.mtg_insight.port.adapter.service.card;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.card.Artifact;
import de.avalax.mtg_insight.domain.model.card.Creature;
import de.avalax.mtg_insight.domain.model.card.Enchantment;
import de.avalax.mtg_insight.domain.model.card.Land;
import de.avalax.mtg_insight.domain.model.card.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.Instant;
import de.avalax.mtg_insight.domain.model.card.Sorcery;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class CardCreatorTest {
    private ConvertedManaCost convertedManaCost;
    private List<Color> cardColors;
    private CardCreator cardBuilder;

    private void assertCard(Card cardFromType, Class<?> type, ConvertedManaCost convertedManaCost, List<Color> cardColors) {
        assertThat(cardFromType, instanceOf(type));
        assertThat(cardFromType.convertedManaCost(), equalTo(convertedManaCost));
        assertThat(cardFromType.colorOfCard(), equalTo(cardColors));
    }

    @Before
    public void setUp() throws Exception {
        cardBuilder = new CardCreator();
        convertedManaCost = new ConvertedManaCost(null, Collections.<ManaCost>emptyList());
        cardColors = Collections.<Color>emptyList();
    }

    @Test
    public void createCardWithoutType_shouldReturnGenericCard() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType(null, "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, GenericCard.class, this.convertedManaCost, this.cardColors);
    }

    @Test
    public void createCardFromTypeCreature_shouldReturnCreature() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Creature", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Creature.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeLegendaryCreature_shouldReturnCreature() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Legendary Creature", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Creature.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeArtifact_shouldReturnArtifact() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Artifact", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Artifact.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeEnchantment_shouldReturnEnchantment() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Enchantment", "Kartenname",cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Enchantment.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeLegendaryEnchantmentArtifact_shouldReturnArtifact() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Legendary Enchantment Artifact", "Kartenname",cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Artifact.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeEnchantmentCreature_shouldReturnCreature() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Enchantment Creature", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Creature.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeLand_shouldReturnLand() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Land", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Land.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeLegendaryLand_shouldReturnLand() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Legendary Land", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Land.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypePlaneswalker_shouldReturnPlaneswalker() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Planeswalker", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Planeswalker.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeInstant_shouldReturnInstant() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Instant", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Instant.class, convertedManaCost, cardColors);
    }

    @Test
    public void createCardFromTypeSorcery_shouldReturnSorcery() throws Exception {
        Card cardFromType = cardBuilder.createCardFromType("Sorcery", "Kartenname", cardColors, convertedManaCost, "0", "0", "0");
        assertCard(cardFromType, Sorcery.class, convertedManaCost, cardColors);
    }
}