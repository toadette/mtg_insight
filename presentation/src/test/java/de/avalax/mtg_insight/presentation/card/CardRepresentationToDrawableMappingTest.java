package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardRepresentationToDrawableMappingTest {
    @InjectMocks
    private CardRepresentationToDrawableMapping cardRepresentationToDrawableMapping;
    @Mock
    private Context context;
    @Mock
    private Drawable backgroundColorless;
    @Mock
    private Drawable backgroundGreen;
    @Mock
    private android.content.res.Resources resources;

    private Drawable drawable(int resource) {
        return ContextCompat.getDrawable(context, resource);
    }

    private CardRepresentation cardRepresentationFor(Mana ...manas) {
        final List<Mana> colorOfCard = new ArrayList<>();
        Collections.addAll(colorOfCard, manas);
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
        return new CardRepresentation(card);
    }

    @Before
    public void setUp() throws Exception {
        when(context.getResources()).thenReturn(resources);
        when(resources.getDrawable(R.drawable.background_colorless)).thenReturn(backgroundColorless);
        when(resources.getDrawable(R.drawable.background_green)).thenReturn(backgroundGreen);
    }

    @Test
    public void shouldReturnColorlessBackground() throws Exception {
        assertThat(cardRepresentationToDrawableMapping.backgroundFrom(null), equalTo(drawable(R.drawable.background_colorless)));
        assertThat(cardRepresentationToDrawableMapping.backgroundFrom(cardRepresentationFor()), equalTo(drawable(R.drawable.background_colorless)));
        assertThat(cardRepresentationToDrawableMapping.backgroundFrom(cardRepresentationFor(Mana.COLORLESS)), equalTo(drawable(R.drawable.background_colorless)));
        //assertThat(cardRepresentationToDrawableMapping.backgroundFrom(cardRepresentationFor(Mana.GREEN)), equalTo(drawable(R.drawable.background_green)));
    }
}