package de.avalax.mtg_insight.presentation.card;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.deck.BasicDeck;
import de.avalax.mtg_insight.domain.model.deck.Deckname;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class DeckAdapterTest {

    private List<Card> deck;
    private List<Card> sideboard;
    private Deckname deckname;
    private Context context;
    private DeckAdapter deckAdapter;

    @Before
    public void setUp() throws Exception {
        context = mock(Context.class);
        deck = new ArrayList<>();
        sideboard = new ArrayList<>();
        deckname = new Deckname("deckAdapterTestDect");
    }

    @Test
    public void emptyDeck_shouldReturnEmptyCount() throws Exception {
        deckAdapter = new DeckAdapter(context, new BasicDeck(deckname, deck, sideboard));

        assertThat(deckAdapter.getCount(), equalTo(0));
    }

    @Test
    public void deckWithTwoDifferentCards_shouldReturnDeckWithTwoCardRepresentations() throws Exception {
        deck.add(new CardBuilder("card1").build());
        deck.add(new CardBuilder("card2").build());

        deckAdapter = new DeckAdapter(context, new BasicDeck(deckname, deck, sideboard));

        assertThat(deckAdapter.getCount(), equalTo(2));
        assertThat(deckAdapter.getItem(0), instanceOf(CardRepresentation.class));
        assertThat(deckAdapter.getItem(1), instanceOf(CardRepresentation.class));
        assertThat(((CardRepresentation)deckAdapter.getItem(0)).count(), equalTo(1));
        assertThat(((CardRepresentation)deckAdapter.getItem(1)).count(), equalTo(1));
        assertThat(((CardRepresentation)deckAdapter.getItem(0)).name(), equalTo("card1"));
        assertThat(((CardRepresentation)deckAdapter.getItem(1)).name(), equalTo("card2"));
    }

    @Test
    public void deckWithSameCards_shouldReturnDeckWitGroupedCardRepresentations() throws Exception {
        deck.add(new CardBuilder("card1").build());
        deck.add(new CardBuilder("card1").build());

        deckAdapter = new DeckAdapter(context, new BasicDeck(deckname, deck, sideboard));

        assertThat(deckAdapter.getCount(), equalTo(1));
        assertThat(deckAdapter.getItem(0), instanceOf(CardRepresentation.class));
        assertThat(((CardRepresentation)deckAdapter.getItem(0)).count(), equalTo(2));
        assertThat(((CardRepresentation)deckAdapter.getItem(0)).name(), equalTo("card1"));
    }
}