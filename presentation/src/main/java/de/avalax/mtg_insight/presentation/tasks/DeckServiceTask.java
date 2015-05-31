package de.avalax.mtg_insight.presentation.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Collections;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.deck.StandardDeck;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.deck.JobProgressListener;
import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;

public class DeckServiceTask extends AsyncTask<String, Integer, Deck> implements JobProgressListener {

    private DeckService deckService;
    private ProgressBar progressBar;

    private DeckServiceResponse delegate;

    public DeckServiceTask(DeckServiceResponse delegate, DeckService deckService, ProgressBar progressBar) {
        this.delegate = delegate;
        this.deckService = deckService;
        this.progressBar = progressBar;
    }

    @Override
    protected Deck doInBackground(String... params) {
        try {
            return deckService.deckFromDeckname(new Deckname(params[0]), this);
        } catch (DeckNotFoundException e) {
            Log.d("deck not found", e.getMessage(), e);
            return new StandardDeck(new Deckname(e.getMessage()), Collections.<Card>emptyList(), Collections.<Card>emptyList());
        }
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(Deck deck) {
        delegate.processFinish(deck);
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }

    @Override
    public void publishProgress(int progress) {
        super.publishProgress(progress);
    }
}
