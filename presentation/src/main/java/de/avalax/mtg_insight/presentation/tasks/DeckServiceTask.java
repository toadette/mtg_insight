package de.avalax.mtg_insight.presentation.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.deck.BasicDeck;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;

public class DeckServiceTask extends AsyncTask<String, Void, Deck> {

    private DeckService deckService;

    private DeckServiceResponse delegate;

    public DeckServiceTask(DeckServiceResponse delegate, DeckService deckService) {
        this.delegate = delegate;
        this.deckService = deckService;
    }

    @Override
    protected Deck doInBackground(String... params) {
        try {
            return deckService.deckFromDeckname(new Deckname(params[0]));
        } catch (DeckNotFoundException e) {
            Log.d("deck not found",e.getMessage(),e);
            return new BasicDeck(new Deckname(e.getMessage()),Collections.<Card>emptyList());
        }
    }

    @Override
    protected void onPostExecute(Deck deck) {
        delegate.processFinish(deck);
    }
}
