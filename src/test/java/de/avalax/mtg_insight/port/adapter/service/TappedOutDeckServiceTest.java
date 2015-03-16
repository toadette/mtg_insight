package de.avalax.mtg_insight.port.adapter.service;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.List;

import de.avalax.mtg_insight.domain.model.deck.Deckname;

import static org.junit.Assert.*;

public class TappedOutDeckServiceTest {

    private String url;
    private TappedOutDeckService tappedOutDeckService;
    @Before
    public void setUp() throws Exception {
        url="http://tappedout.net/mtg-decks/from-the-grave-to-the-cradle/?fmt=txt";
        tappedOutDeckService=new TappedOutDeckService(url);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDeckFromDeckname() throws Exception {
        List<Deckname> decknames = tappedOutDeckService.decknames();
        assertThat(decknames.get(0).getName(), Matchers.equalTo("test"));
    }

    @Test
    public void testDecknames() throws Exception {

    }
}