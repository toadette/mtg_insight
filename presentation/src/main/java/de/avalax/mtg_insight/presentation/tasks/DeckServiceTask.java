package de.avalax.mtg_insight.presentation.tasks;

import android.os.AsyncTask;

import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;

public class DeckServiceTask extends AsyncTask<String, Void, Deck> {

    private DeckService deckService;

    private DeckServiceResponse delegate;

    public DeckServiceTask(DeckServiceResponse delegate, DeckService deckService) {
        this.delegate = delegate;
        this.deckService = deckService;
    }

    @Override
    protected Deck doInBackground(String... params) {
        return deckService.deckFromDeckname(new Deckname(params[0]));
    }

    @Override
    protected void onPostExecute(Deck deck) {
        delegate.processFinish(deck);
    }
}
