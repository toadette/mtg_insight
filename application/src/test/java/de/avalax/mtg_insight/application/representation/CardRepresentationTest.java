package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CreatureBody;
import de.avalax.mtg_insight.domain.model.card.LoyaltyPoints;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.bechte.junit.runners.context.HierarchicalContextRunner;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class CardRepresentationTest {

    private CardRepresentation cardRepresentation;
    private List<Color> colors;
    private String name;
    private ConvertedManaCost convertedManaCost;

    @Before
    public void setUp() throws Exception {
        colors = new ArrayList<>();
        Card card = new Card() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public List<Color> colorOfCard() {
                return colors;
            }

            @Override
            public ConvertedManaCost convertedManaCost() {
                return convertedManaCost;
            }
        };
        cardRepresentation = new CardRepresentation(card);
    }

    @Test
    public void cardName_shouldReturnedFromCardRepresentation() throws Exception {
        name = "cardName";
        assertThat(cardRepresentation.name(), equalTo(name));
    }

    @Test
    public void initialCountOfCard_shouldReturnOne() throws Exception {
        assertThat(cardRepresentation.count(), equalTo(1));
    }

    @Test
    public void changeCountOfCard_shouldReturn4() throws Exception {
        cardRepresentation.setCountOfCard(4);
        assertThat(cardRepresentation.count(), equalTo(4));
    }

    @Test
    public void nullInstance_shouldReturnEmptyConvertedString() throws Exception {
        convertedManaCost = null;
        assertThat(cardRepresentation.convertedManaCost(), equalTo(""));
    }

    @Test
    public void convertedManaCostWitManaCost_shouldReturnConvertedString() throws Exception {
        convertedManaCost = new ConvertedManaCost("cmcToString", null);
        assertThat(cardRepresentation.convertedManaCost(), equalTo("cmcToString"));
    }

    @Test
    public void genericCard_shouldBeNoCreature() throws Exception {
        boolean isCreature = cardRepresentation.hasPowerToughness();
        assertThat(isCreature, equalTo(Boolean.FALSE));
    }

    @Test
    public void genericCard_shouldReturnEmptyPowerToughness() throws Exception {
        String creatureBody = cardRepresentation.powerToughness();
        assertThat(creatureBody, isEmptyString());
    }

    public class color {
        @Test
        public void noColor_shouldReturnColorColorless() throws Exception {
            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.COLORLESS));
        }

        @Test
        public void redColor_shouldReturnColorRed() throws Exception {
            colors.add(Color.RED);

            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.RED));
        }

        @Test
        public void greenColor_shouldReturnColorGreen() throws Exception {
            colors.add(Color.GREEN);

            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.GREEN));
        }

        @Test
        public void whiteColor_shouldReturnColorWhite() throws Exception {
            colors.add(Color.WHITE);

            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.WHITE));
        }

        @Test
        public void blueColor_shouldReturnColorBlue() throws Exception {
            colors.add(Color.BLUE);

            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.BLUE));
        }

        @Test
        public void blackColor_shouldReturnColorBlack() throws Exception {
            colors.add(Color.BLACK);

            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.BLACK));
        }

        @Test
        public void multicolorColor_shouldReturnMulticolor() throws Exception {
            colors.add(Color.RED);
            colors.add(Color.GREEN);

            assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.MULTICOLOR));
        }
    }

    public class creatureRepresentation {
        public static final int CREATURE_POWER = 42;
        public static final int CREATURE_TOUGHNESS = 21;
        private CardBuilder cardBuilder;
        private CreatureBody creatureBody;

        @Before
        public void setUp() throws Exception {
            cardBuilder = new CardBuilder("creature");
            creatureBody = new CreatureBody(CREATURE_POWER, CREATURE_TOUGHNESS);
            Card creature = cardBuilder.creatureCard(creatureBody, null).build();
            cardRepresentation = new CardRepresentation(creature);
        }

        @Test
        public void creatureRepresentation_shouldBeACreature() throws Exception {
            boolean isCreature = cardRepresentation.hasPowerToughness();
            assertThat(isCreature, equalTo(Boolean.TRUE));
        }

        @Test
        public void creatureRepresentation_shouldReturnCreaturePower() throws Exception {
            String creatureBodyFromRepresentation = cardRepresentation.powerToughness();
            assertThat(creatureBodyFromRepresentation, equalTo(CREATURE_POWER + " / " + CREATURE_TOUGHNESS));
        }

    }

    public class creatureRepresentationWithStarPowerToughness {
        public static final int CREATURE_POWER = -1;
        public static final int CREATURE_TOUGHNESS = -1;
        private CardBuilder cardBuilder;
        private CreatureBody creatureBody;

        @Before
        public void setUp() throws Exception {
            cardBuilder = new CardBuilder("creature");
            creatureBody = new CreatureBody(CREATURE_POWER, CREATURE_TOUGHNESS);
            Card creature = cardBuilder.creatureCard(creatureBody, null).build();
            cardRepresentation = new CardRepresentation(creature);
        }

        @Test
        public void creatureRepresentation_shouldBeACreature() throws Exception {
            boolean hasPowerToughness = cardRepresentation.hasPowerToughness();
            assertThat(hasPowerToughness, equalTo(Boolean.TRUE));
        }

        @Test
        public void creatureRepresentation_shouldReturnCreatureStarPower() throws Exception {
            String creatureBodyFromRepresentation = cardRepresentation.powerToughness();
            assertThat(creatureBodyFromRepresentation, equalTo("* / *"));
        }
    }

    public class planeswalkerRepresentationWithLoyaltyPoints {
        public static final int LOYALTY_POINTS = 42;

        @Before
        public void setUp() throws Exception {
            Card planeswalker = new CardBuilder("planeswalker").planeswalkerCard(new LoyaltyPoints(LOYALTY_POINTS)).build();
            cardRepresentation = new CardRepresentation(planeswalker);
        }

        @Test
        public void planeswalkerRepresentation_shouldHavePowerToughness() throws Exception {
            boolean hasPowerToughness = cardRepresentation.hasPowerToughness();
            assertThat(hasPowerToughness, equalTo(Boolean.TRUE));
        }

        @Test
        public void planeswalkerRepresentation_shouldReturnLoyaltyPoints() throws Exception {
            String creatureBodyFromRepresentation = cardRepresentation.powerToughness();
            assertThat(creatureBodyFromRepresentation, equalTo(String.valueOf(LOYALTY_POINTS)));
        }
    }
}