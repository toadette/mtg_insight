package de.avalax.mtg_insight.presentation.card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;
import de.avalax.mtg_insight.presentation.ScalableStaggeredGridView;
import de.avalax.mtg_insight.presentation.tasks.DeckServiceResponse;
import de.avalax.mtg_insight.presentation.tasks.DeckServiceTask;

public class PlaymatFragment extends Fragment implements DeckServiceResponse {
    @InjectView(R.id.cardStage)
    protected ScalableStaggeredGridView cardStage;

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
        new DeckServiceTask(this, deckService).execute("from-the-grave-to-the-cradle");
    }

    @Override
    public void processFinish(Deck deck) {
        cardStage.setAdapter(new DeckAdapter(getActivity(), deck));
        getActivity().setTitle(deck.name().getName());
    }
}