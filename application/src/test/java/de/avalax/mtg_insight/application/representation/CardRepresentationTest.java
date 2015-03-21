package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;
import de.bechte.junit.runners.context.HierarchicalContextRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(HierarchicalContextRunner.class)
public class CardRepresentationTest {

    private CardRepresentation cardRepresentation;
    private List<Color> colors;
    private String name;

    @Before
    public void setUp() throws Exception {
        colors = new ArrayList<>();
        Card card = new Card() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public Image image() {
                return null;
            }

            @Override
            public List<Color> colorOfCard() {
                return colors;
            }

            @Override
            public List<ManaCost> convertedManaCost() {
                return null;
            }
        };
        cardRepresentation = new CardRepresentation(card, new ConvertedManaCostToString() {
            @Override
            public String convertToString(List<ManaCost> manaCosts) {
                return "cmcToString";
            }
        });
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
    public void convertedManaCost_shouldReturnConvertedString() throws Exception {
        assertThat(cardRepresentation.convertedManaCost(), equalTo("cmcToString"));
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
}