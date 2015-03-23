package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.bechte.junit.runners.context.HierarchicalContextRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(HierarchicalContextRunner.class)
public class CardRepresentationToDrawableTest {
    @InjectMocks
    private CardRepresentationToDrawable cardRepresentationToDrawable;
    @Mock
    private Context context;
    @Mock
    private Drawable colorlessDrawable;
    @Mock
    private Drawable greenDrawable;
    @Mock
    private Resources resources;
    @Mock
    private Drawable blackDrawable;
    @Mock
    private Drawable whiteDrawable;
    @Mock
    private Drawable blueDrawable;
    @Mock
    private Drawable redDrawable;
    @Mock
    private Drawable multicolorDrawable;

    private Drawable drawable(int resource) {
        return ContextCompat.getDrawable(context, resource);
    }

    private CardRepresentation cardRepresentationFor(Color... colors) {
        final List<Color> colorOfCard = new ArrayList<>();
        Collections.addAll(colorOfCard, colors);
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
            public List<Color> colorOfCard() {
                return colorOfCard;
            }

            @Override
            public ConvertedManaCost convertedManaCost() {
                return null;
            }
        };
        return new CardRepresentation(card);
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(context.getResources()).thenReturn(resources);

    }

    public class backgroundDrawable {
        @Before
        public void setUp() throws Exception {
            when(resources.getDrawable(R.drawable.background_colorless)).thenReturn(colorlessDrawable);
            when(resources.getDrawable(R.drawable.background_green)).thenReturn(greenDrawable);
            when(resources.getDrawable(R.drawable.background_black)).thenReturn(blackDrawable);
            when(resources.getDrawable(R.drawable.background_white)).thenReturn(whiteDrawable);
            when(resources.getDrawable(R.drawable.background_blue)).thenReturn(blueDrawable);
            when(resources.getDrawable(R.drawable.background_red)).thenReturn(redDrawable);
            when(resources.getDrawable(R.drawable.background_multicolor)).thenReturn(multicolorDrawable);
        }

        @Test
        public void shouldReturnBackground() throws Exception {
            assertThat(cardRepresentationToDrawable.backgroundFrom(null), equalTo(drawable(R.drawable.background_colorless)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor()), equalTo(drawable(R.drawable.background_colorless)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor(Color.GREEN)), equalTo(drawable(R.drawable.background_green)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor(Color.BLACK)), equalTo(drawable(R.drawable.background_black)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor(Color.WHITE)), equalTo(drawable(R.drawable.background_white)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor(Color.BLUE)), equalTo(drawable(R.drawable.background_blue)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor(Color.RED)), equalTo(drawable(R.drawable.background_red)));
            assertThat(cardRepresentationToDrawable.backgroundFrom(cardRepresentationFor(Color.RED, Color.BLACK)), equalTo(drawable(R.drawable.background_multicolor)));
        }

    }

    public class windowBackgroundDrawable {
        @Before
        public void setUp() throws Exception {
            when(resources.getDrawable(R.drawable.window_colorless)).thenReturn(colorlessDrawable);
            when(resources.getDrawable(R.drawable.window_green)).thenReturn(greenDrawable);
            when(resources.getDrawable(R.drawable.window_black)).thenReturn(blackDrawable);
            when(resources.getDrawable(R.drawable.window_white)).thenReturn(whiteDrawable);
            when(resources.getDrawable(R.drawable.window_blue)).thenReturn(blueDrawable);
            when(resources.getDrawable(R.drawable.window_red)).thenReturn(redDrawable);
            when(resources.getDrawable(R.drawable.window_multicolor)).thenReturn(multicolorDrawable);
        }

        @Test
        public void shouldReturnWindowBackground() throws Exception {
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(null), equalTo(drawable(R.drawable.window_colorless)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor()), equalTo(drawable(R.drawable.window_colorless)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor(Color.GREEN)), equalTo(drawable(R.drawable.window_green)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor(Color.BLACK)), equalTo(drawable(R.drawable.window_black)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor(Color.WHITE)), equalTo(drawable(R.drawable.window_white)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor(Color.BLUE)), equalTo(drawable(R.drawable.window_blue)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor(Color.RED)), equalTo(drawable(R.drawable.window_red)));
            assertThat(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentationFor(Color.RED, Color.BLACK)), equalTo(drawable(R.drawable.window_multicolor)));
        }
    }

    public class cardBackgroundDrawable {
        @Before
        public void setUp() throws Exception {
            when(resources.getDrawable(R.drawable.card_background_colorless)).thenReturn(colorlessDrawable);
            when(resources.getDrawable(R.drawable.card_background_green)).thenReturn(greenDrawable);
            when(resources.getDrawable(R.drawable.card_background_black)).thenReturn(blackDrawable);
            when(resources.getDrawable(R.drawable.card_background_white)).thenReturn(whiteDrawable);
            when(resources.getDrawable(R.drawable.card_background_blue)).thenReturn(blueDrawable);
            when(resources.getDrawable(R.drawable.card_background_red)).thenReturn(redDrawable);
            when(resources.getDrawable(R.drawable.card_background_multicolor)).thenReturn(multicolorDrawable);
        }

        @Test
        public void shouldReturnBackground() throws Exception {
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(null), equalTo(drawable(R.drawable.card_background_colorless)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor()), equalTo(drawable(R.drawable.card_background_colorless)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor(Color.GREEN)), equalTo(drawable(R.drawable.card_background_green)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor(Color.BLACK)), equalTo(drawable(R.drawable.card_background_black)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor(Color.WHITE)), equalTo(drawable(R.drawable.card_background_white)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor(Color.BLUE)), equalTo(drawable(R.drawable.card_background_blue)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor(Color.RED)), equalTo(drawable(R.drawable.card_background_red)));
            assertThat(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentationFor(Color.RED, Color.BLACK)), equalTo(drawable(R.drawable.card_background_multicolor)));
        }
    }

    public class headerDrawable {
        @Before
        public void setUp() throws Exception {
            when(resources.getDrawable(R.drawable.header_colorless)).thenReturn(colorlessDrawable);
            when(resources.getDrawable(R.drawable.header_green)).thenReturn(greenDrawable);
            when(resources.getDrawable(R.drawable.header_black)).thenReturn(blackDrawable);
            when(resources.getDrawable(R.drawable.header_white)).thenReturn(whiteDrawable);
            when(resources.getDrawable(R.drawable.header_blue)).thenReturn(blueDrawable);
            when(resources.getDrawable(R.drawable.header_red)).thenReturn(redDrawable);
            when(resources.getDrawable(R.drawable.header_multicolor)).thenReturn(multicolorDrawable);
        }

        @Test
        public void shouldReturnHeaderDrawable() throws Exception {
            assertThat(cardRepresentationToDrawable.headerFrom(null), equalTo(drawable(R.drawable.header_colorless)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor()), equalTo(drawable(R.drawable.header_colorless)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor(Color.GREEN)), equalTo(drawable(R.drawable.header_green)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor(Color.BLACK)), equalTo(drawable(R.drawable.header_black)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor(Color.WHITE)), equalTo(drawable(R.drawable.header_white)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor(Color.BLUE)), equalTo(drawable(R.drawable.header_blue)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor(Color.RED)), equalTo(drawable(R.drawable.header_red)));
            assertThat(cardRepresentationToDrawable.headerFrom(cardRepresentationFor(Color.RED, Color.BLACK)), equalTo(drawable(R.drawable.header_multicolor)));
        }
    }

}