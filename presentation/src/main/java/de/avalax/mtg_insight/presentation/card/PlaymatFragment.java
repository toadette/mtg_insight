package de.avalax.mtg_insight.presentation.card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.lucasr.twowayview.widget.TwoWayView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;
import de.avalax.mtg_insight.presentation.tasks.DeckServiceResponse;
import de.avalax.mtg_insight.presentation.tasks.DeckServiceTask;

public class PlaymatFragment extends Fragment implements DeckServiceResponse {
    @InjectView(R.id.creatures)
    protected GridView creatures;

    @InjectView(R.id.spells)
    protected GridView spells;

    @InjectView(R.id.permanents)
    protected GridView permanents;

    @InjectView(R.id.planeswalkers)
    protected GridView planeswalkers;

    @InjectView(R.id.lands)
    protected GridView lands;

    @InjectView(R.id.progressBar)
    protected ProgressBar progressBar;

    @Inject
    protected DeckService deckService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_playmat, container, false);
        ((MtgInsightApplication) getActivity().getApplicationContext()).inject(this);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addDummyCard();
    }

    private void addDummyCard() {
        new DeckServiceTask(this, deckService, progressBar).execute("wg-raptor");
    }

    @Override
    public void processFinish(Deck deck) {
        creatures.setAdapter(new DeckAdapter(getActivity(), deck.deck().creatures()));
        permanents.setAdapter(new DeckAdapter(getActivity(), deck.deck().permanents()));
        spells.setAdapter(new DeckAdapter(getActivity(), deck.deck().spells()));
        planeswalkers.setAdapter(new DeckAdapter(getActivity(), deck.deck().planeswalkers()));
        lands.setAdapter(new DeckAdapter(getActivity(), deck.deck().lands()));
        getActivity().setTitle(deck.name().getName());
    }
}
