package de.avalax.mtg_insight.presentation.card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.application.representation.ConvertedManaCostToString;
import de.avalax.mtg_insight.domain.model.card.Image;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.color.Color;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;

public class CardDemoFragment extends Fragment {
    @InjectView(R.id.cardStage)
    protected LinearLayout cardStage;

    @Inject
    protected ConvertedManaCostToString convertedManaCostToString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_card_demo, container, false);
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
        String cardName = "name";
        List<Color> cardColors = new ArrayList<>();
        cardColors.add(Color.BLUE);
        List<ManaCost> convertedManaCost = new ArrayList<>();
        convertedManaCost.add(new ManaCost(Collections.<Mana>emptyList(),"U"));
        convertedManaCost.add(new ManaCost(Collections.<Mana>emptyList(),"U"));
        convertedManaCost.add(new ManaCost(Collections.<Mana>emptyList(),"3"));
        Image image = null;
        CardRepresentation cardRepresentation = new CardRepresentation(new Creature(cardName, image, cardColors, convertedManaCost), convertedManaCostToString);
        cardRepresentation.setCountOfCard(5);
        CardView cardView = new CardView(getActivity(), cardRepresentation);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        cardStage.addView(cardView);
    }
}
