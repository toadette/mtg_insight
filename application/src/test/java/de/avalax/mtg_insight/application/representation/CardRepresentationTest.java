package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class CardRepresentationTest {

    private CardRepresentation cardRepresentation;
    private List<Mana> colorOfCard;

    @Before
    public void setUp() throws Exception {
        colorOfCard = new ArrayList<>();
        Card card = new Card() {
            @Override
            public String name() {
                return null;
            }

            @Override
            public Image image() {
                return null;
            }

            @Override
            public List<Mana> colorOfCard() {
                return colorOfCard;
            }

            @Override
            public List<ManaCost> convertedManaCost() {
                return null;
            }
        };
        cardRepresentation = new CardRepresentation(card);
    }

    @Test
    public void emptyColorOfCard_shouldReturnColorColorless() throws Exception {
        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.COLORLESS));
    }

    @Test
    public void phyrexianColorOfCard_shouldReturnColorColorless() throws Exception {
        colorOfCard.add(Mana.PHYREXIAN);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.COLORLESS));
    }

    @Test
    public void colorlessColorOfCard_shouldReturnColorColorless() throws Exception {
        colorOfCard.add(Mana.COLORLESS);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.COLORLESS));
    }

    @Test
    public void redColorOfCard_shouldReturnColorRed() throws Exception {
        colorOfCard.add(Mana.RED);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.RED));
    }

    @Test
    public void greenColorOfCard_shouldReturnColorGreen() throws Exception {
        colorOfCard.add(Mana.GREEN);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.GREEN));
    }

    @Test
    public void whiteColorOfCard_shouldReturnColorWhite() throws Exception {
        colorOfCard.add(Mana.WHITE);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.WHITE));
    }

    @Test
    public void blueColorOfCard_shouldReturnColorBlue() throws Exception {
        colorOfCard.add(Mana.BLUE);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.BLUE));
    }

    @Test
    public void blackColorOfCard_shouldReturnColorBlack() throws Exception {
        colorOfCard.add(Mana.BLACK);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.BLACK));
    }


    @Test
    public void MulticolorOfCard_shouldReturnMulticolor() throws Exception {
        colorOfCard.add(Mana.RED);
        colorOfCard.add(Mana.GREEN);

        assertThat(cardRepresentation.color(), equalTo(CardColorRepresentation.MULTICOLOR));
    }
}