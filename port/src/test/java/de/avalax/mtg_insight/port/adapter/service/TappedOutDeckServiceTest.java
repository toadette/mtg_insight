package de.avalax.mtg_insight.port.adapter.service;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.List;

import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.Deckname;

import static org.junit.Assert.*;

public class TappedOutDeckServiceTest {

    private String deckname;
    private TappedOutDeckService tappedOutDeckService;
    @Before
    public void setUp() throws Exception {
        deckname="from-the-grave-to-the-cradle";
        tappedOutDeckService=new TappedOutDeckService(deckname);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeckFromDeckname() throws Exception {
        Deck deck = tappedOutDeckService.deckFromDeckname(new Deckname("from-the-grave-to-the-cradle"));
        assertThat(deck.name().getName(),Matchers.equalTo("from-the-grave-to-the-cradle"));
        assertThat(deck.cardsOfDeck().size(),Matchers.equalTo(30));
        assertThat(deck.cardsOfDeck().get(0).name(),Matchers.equalTo("Boneyard Wurm"));
    }

    @Test
    public void testDecknames() throws Exception {
        List<Deckname> decknames = tappedOutDeckService.decknames();
        assertThat(decknames.get(0).getName(), Matchers.equalTo("from-the-grave-to-the-cradle"));
    }
}