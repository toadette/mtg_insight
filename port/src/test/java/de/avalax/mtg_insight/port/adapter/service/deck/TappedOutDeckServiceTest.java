package de.avalax.mtg_insight.port.adapter.service.deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.card.MtgDBCardService;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.deck.TappedOutDeckService;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class TappedOutDeckServiceTest {

    private String deckname;
    private TappedOutDeckService tappedOutDeckService;

    @Before
    public void setUp() throws Exception {
        deckname = "from-the-grave-to-the-cradle";
        tappedOutDeckService = new TappedOutDeckService(new MtgDBCardService(new ManaTokenizer(), new ColorMatcher()));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeckFromDeckname() throws Exception {
        Deck deck = tappedOutDeckService.deckFromDeckname(new Deckname("from-the-grave-to-the-cradle"));
        assertThat(deck.name().getName(), equalTo("from-the-grave-to-the-cradle"));
        assertThat(deck.cardsOfDeck().size(), equalTo(61));
        assertThat(deck.cardsOfDeck().get(0).name(), equalTo("Boneyard Wurm"));
        assertThat(deck.cardsOfDeck().get(1).name(), equalTo("Boneyard Wurm"));
        assertThat(deck.cardsOfDeck().get(13).name(), equalTo("Elvish Mystic"));
        assertThat(deck.cardsOfDeck().get(45).name(), equalTo("Swamp"));
    }

    @Test(expected = DeckNotFoundException.class)
    public void deckFromDeckname_shouldReturnDeckNotFound() throws Exception {
        tappedOutDeckService.deckFromDeckname(new Deckname("blablablbalbla"));
    }

    @Test
    @Ignore
    public void testDecknames() throws Exception {
        List<Deckname> decknames = tappedOutDeckService.decknames();
//        assertThat(decknames.get(0).getName(), Matchers.equalTo("from-the-grave-to-the-cradle"));
    }
}