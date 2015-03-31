package de.avalax.mtg_insight.port.adapter.service.card;

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

public class CardBuilderTest {

    @Test
    public void createCardWithoutType_shouldReturnGenericCard() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        ConvertedManaCost convertedManaCost = new ConvertedManaCost(null, Collections.<ManaCost>emptyList());
        List<Color> cardColors = Collections.<Color>emptyList();
        Card cardFromType = cardBuilder.createCardFromType(null, "Kartenname", cardColors, convertedManaCost);
        assertThat(cardFromType, instanceOf(GenericCard.class));
        assertThat(cardFromType.convertedManaCost(), equalTo(convertedManaCost));
        assertThat(cardFromType.colorOfCard(), equalTo(cardColors));
    }

    @Test
    public void createCardFromTypeCreature_shouldReturnCreature() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Creature", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Creature.class));
    }

    @Test
    public void createCardFromTypeLegendaryCreature_shouldReturnCreature() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Legendary Creature", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Creature.class));
    }

    @Test
    public void createCardFromTypeArtifact_shouldReturnArtifact() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Artifact", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Artifact.class));
    }

    @Test
    public void createCardFromTypeEnchantment_shouldReturnEnchantment() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Enchantment", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Enchantment.class));
    }

    @Test
    public void createCardFromTypeLegendaryEnchantmentArtifact_shouldReturnArtifact() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Legendary Enchantment Artifact", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Artifact.class));
    }

    @Test
    public void createCardFromTypeEnchantmentCreature_shouldReturnCreature() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Enchantment Creature", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Creature.class));
    }

    @Test
    public void createCardFromTypeLand_shouldReturnLand() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Land", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Land.class));
    }

    @Test
    public void createCardFromTypeLegendaryLand_shouldReturnLand() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Legendary Land", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Land.class));
    }

    @Test
    public void createCardFromTypePlaneswalker_shouldReturnPlaneswalker() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Planeswalker", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Planeswalker.class));
    }

    @Test
    public void createCardFromTypeInstant_shouldReturnInstant() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Instant", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Instant.class));
    }

    @Test
    public void createCardFromTypeSorcery_shouldReturnSorcery() throws Exception {
        CardBuilder cardBuilder = new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Sorcery", "Kartenname", null, null);
        assertThat(cardFromType, instanceOf(Sorcery.class));
    }
}