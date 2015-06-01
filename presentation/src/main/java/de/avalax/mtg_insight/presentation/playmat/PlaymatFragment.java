package de.avalax.mtg_insight.presentation.playmat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;
import de.avalax.mtg_insight.presentation.card.CardView;
import de.avalax.mtg_insight.presentation.tasks.DeckServiceResponse;
import de.avalax.mtg_insight.presentation.tasks.DeckServiceTask;

public class PlaymatFragment extends Fragment implements DeckServiceResponse {
    @InjectView(R.id.creatures)
    protected LinearLayout creatures;

    @InjectView(R.id.spells)
    protected LinearLayout spells;

    @InjectView(R.id.permanents)
    protected LinearLayout permanents;

    @InjectView(R.id.planeswalkers)
    protected LinearLayout planeswalkers;

    @InjectView(R.id.lands)
    protected LinearLayout lands;

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
        creatures.removeAllViews();

        for (CardView card : new CardRepresentationAdapter(getActivity(), deck.deck().creatures())) {
            creatures.addView(card);
        }
        permanents.removeAllViews();
        for (CardView card : new CardRepresentationAdapter(getActivity(), deck.deck().permanents())) {
            permanents.addView(card);
        }
        spells.removeAllViews();
        for (CardView card : new CardRepresentationAdapter(getActivity(), deck.deck().spells())) {
            spells.addView(card);
        }
        planeswalkers.removeAllViews();
        for (CardView card : new CardRepresentationAdapter(getActivity(), deck.deck().planeswalkers())) {
            planeswalkers.addView(card);
        }
        lands.removeAllViews();
        for (CardView card : new CardRepresentationAdapter(getActivity(), deck.deck().lands())) {
            lands.addView(card);
        }
        getActivity().setTitle(deck.name().getName());
    }
}
