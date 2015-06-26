package de.avalax.mtg_insight.port.adapter.service.deck;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.deck.JobProgressListener;
import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;
import de.avalax.mtg_insight.port.adapter.service.mtgdb.MtgDBCardService;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class TappedOutDeckServiceTest {

    private String deckname;
    private TappedOutDeckService tappedOutDeckService;
    private MockedJobProgressListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new MockedJobProgressListener();
    }

    public void setUp1() throws Exception {
        deckname = "from-the-grave-to-the-cradle";
        tappedOutDeckService = new TappedOutDeckService(new MtgDBCardService(new ManaTokenizer(), new ColorMatcher(), new AbilityTokenizer()));
    }

    public void setUp2() throws Exception {
        deckname = "dudes-with-swords";
        tappedOutDeckService = new TappedOutDeckService(new MtgDBCardService(new ManaTokenizer(), new ColorMatcher(), new AbilityTokenizer()));
    }

    public void setUp3() throws Exception {
        deckname = "you-do-not-defy-ojutai-uw-control";
        tappedOutDeckService = new TappedOutDeckService(new MtgDBCardService(new ManaTokenizer(), new ColorMatcher(), new AbilityTokenizer()));
    }

    @Ignore("use other deck")
    @Test
    public void testDeckFromDeckname() throws Exception {
        setUp1();
        Deck deck = tappedOutDeckService.deckFromDeckname(new Deckname(deckname), listener);
        assertThat(deck.name().getName(), equalTo("From the Grave, to the Cradle"));

        org.assertj.core.api.Assertions.assertThat(deck.deck()).hasSize(64);
        assertThat(deck.deck().creatures().get(0).name(), equalTo("Boneyard Wurm"));
        assertThat(deck.deck().creatures().get(1).name(), equalTo("Boneyard Wurm"));
        assertThat(deck.deck().spells().get(0).name(), equalTo("Drown in Filth"));
        assertThat(deck.deck().lands().get(0).name(), equalTo("Svogthos, the Restless Tomb"));
    }

    @Test(expected = DeckNotFoundException.class)
    public void deckFromDeckname_shouldReturnDeckNotFound() throws Exception {
        setUp1();
        tappedOutDeckService.deckFromDeckname(new Deckname("blablablbalbla"), listener);
    }

    @Test
    public void testDeckFromDeckname_shouldReturnSideboard() throws Exception {
        setUp2();
        Deck deck = tappedOutDeckService.deckFromDeckname(new Deckname(deckname), listener);
        assertThat(deck.name().getName(), equalTo("Lotus Blade [Modern]"));
        org.assertj.core.api.Assertions.assertThat(deck.deck()).hasSize(60);
        assertThat(deck.sideboard(), hasSize(15));
        assertThat(deck.sideboard().get(0).name(), equalTo("Celestial Purge"));
        assertThat(deck.sideboard().get(1).name(), equalTo("Celestial Purge"));
        assertThat(deck.sideboard().get(6).name(), equalTo("Leyline of Sanctity"));
        assertThat(deck.sideboard().get(11).name(), equalTo("Rest in Peace"));
        assertThat(deck.sideboard().get(14).name(), equalTo("Stony Silence"));
    }

    @Ignore("use other deck")
    @Test
    public void testDeckFromDeckname_shouldReturnPrintableName() throws Exception {
        setUp3();
        Deck deck = tappedOutDeckService.deckFromDeckname(new Deckname(deckname), listener);
        assertThat(deck.name().getName(), equalTo("You do not defy Ojutai! (U/W Control)"));
    }

    private static class MockedJobProgressListener implements JobProgressListener {
        @Override
        public void publishProgress(int progress) {
            //TODO: Tests for progress indicator
        }
    }
}