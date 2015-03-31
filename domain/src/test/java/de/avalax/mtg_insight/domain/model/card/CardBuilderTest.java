package de.avalax.mtg_insight.domain.model.card;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.permanent.artifact.Artifact;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.permanent.enchantment.Enchantment;
import de.avalax.mtg_insight.domain.model.card.permanent.land.Land;
import de.avalax.mtg_insight.domain.model.card.permanent.planeswalker.Planeswalker;
import de.avalax.mtg_insight.domain.model.card.spell.instant.Instant;
import de.avalax.mtg_insight.domain.model.card.spell.sorcery.Sorcery;
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

    @Test
    public void creatureGiven_shouldBuildCreatureCard() throws Exception {
        CreatureBody creatureBody = new CreatureBody() {
        };
        CardBuilder sameCardBuilder = cardBuilder.creatureCard(creatureBody);

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Creature.class);
    }

    @Test
    public void artifactGiven_shouldBuildArtifactCard() throws Exception {
        CardBuilder sameCardBuilder = cardBuilder.artifactCard();

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Artifact.class);
    }

    @Test
    public void landGiven_shouldBuildLandCard() throws Exception {
        CardBuilder sameCardBuilder = cardBuilder.landCard();

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Land.class);
    }

    @Test
    public void enchantmentGiven_shouldBuildEnchantmentCard() throws Exception {
        CardBuilder sameCardBuilder = cardBuilder.enchantmentCard();

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Enchantment.class);
    }

    @Test
    public void planeswalkerGiven_shouldBuildPlaneswalkerCard() throws Exception {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints() {
        };
        CardBuilder sameCardBuilder = cardBuilder.planeswalkerCard(loyaltyPoints);

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Planeswalker.class);
    }

    @Test
    public void instantGiven_shouldBuildInstantCard() throws Exception {
        CardBuilder sameCardBuilder = cardBuilder.instantCard();

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Instant.class);
    }

    @Test
    public void sorceryGiven_shouldBuildSorceryCard() throws Exception {
        CardBuilder sameCardBuilder = cardBuilder.sorceryCard();

        Card card = cardBuilder.build();

        assertThat(sameCardBuilder).isEqualTo(cardBuilder);
        assertThat(card).isInstanceOf(Sorcery.class);
    }
}