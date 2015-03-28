package de.avalax.mtg_insight.port.adapter.service.card;

import org.junit.Test;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.artifact.Artifact;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.card.permanent.enchantment.Enchantment;
import de.avalax.mtg_insight.domain.model.card.permanent.land.Land;
import de.avalax.mtg_insight.domain.model.card.permanent.planeswalker.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.spell.instant.Instant;
import de.avalax.mtg_insight.domain.model.card.spell.sorcery.Sorcery;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class CardBuilderTest {

    @Test
    public void createCardFromTypeCreature_shouldReturnCreature() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Creature","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Creature.class));
    }
    @Test
    public void createCardFromTypeLegendaryCreature_shouldReturnCreature() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Legendary Creature","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Creature.class));
    }
    @Test
    public void createCardFromTypeArtifact_shouldReturnArtifact() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Artifact","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Artifact.class));
    }
    @Test
    public void createCardFromTypeEnchantment_shouldReturnEnchantment() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Enchantment","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Enchantment.class));
    }
    @Test
    public void createCardFromTypeLegendaryEnchantmentArtifact_shouldReturnArtifact() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Legendary Enchantment Artifact","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Artifact.class));
    }
    @Test
    public void createCardFromTypeEnchantmentCreature_shouldReturnCreature() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Enchantment Creature","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Creature.class));
    }
    @Test
    public void createCardFromTypeLand_shouldReturnLand() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Land","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Land.class));
    }
    @Test
    public void createCardFromTypeLegendaryLand_shouldReturnLand() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Legendary Land","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Land.class));
    }
    @Test
    public void createCardFromTypePlaneswalker_shouldReturnPlaneswalker() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Planeswalker","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Planeswalker.class));
    }
    @Test
    public void createCardFromTypeInstant_shouldReturnInstant() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Instant","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Instant.class));
    }
    @Test
    public void createCardFromTypeSorcery_shouldReturnSorcery() throws Exception {
        CardBuilder cardBuilder=new CardBuilder();
        Card cardFromType = cardBuilder.createCardFromType("Sorcery","Kartenname",null,null);
        assertThat(cardFromType, instanceOf(Sorcery.class));
    }
}