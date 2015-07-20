package de.avalax.mtg_insight.presentation.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import junit.framework.TestCase;

import javax.inject.Inject;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;


@SuppressLint("ViewConstructor")
public class CardView extends LinearLayout {
    @Inject
    protected CardRepresentationToDrawable cardRepresentationToDrawable;

    private View background;
    private View window;
    private TextView name;
    private TextView convertedManaCost;
    private TextView creatureBody;
    private TextView drawPercentage;

    public CardView(Context context, CardRepresentation cardRepresentation) {
        super(context);
        ((MtgInsightApplication) context.getApplicationContext()).inject(this);
        init(context);
        attributes(cardRepresentation);
        addCountToCardView(cardRepresentation);
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_card, this);
        setOrientation(VERTICAL);
        background = findViewById(R.id.cardBackground);
        window = findViewById(R.id.cardWindow);
        name = (TextView) findViewById(R.id.name);
        convertedManaCost = (TextView) findViewById(R.id.converted_mana_cost);
        creatureBody = (TextView) findViewById(R.id.creatureBody);
        drawPercentage = (TextView) findViewById(R.id.drawPercentage);
    }

    private void attributes(CardRepresentation cardRepresentation) {
        name.setText(cardRepresentation.name());
        convertedManaCost.setText(cardRepresentation.convertedManaCost());
        window.setBackground(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentation));
        if (cardRepresentation.count() > 1) {
            setBackground(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentation));
            background.setBackground(cardRepresentationToDrawable.backgroundFrom(cardRepresentation));
        } else {
            setBackground(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentation));
        }
        if (cardRepresentation.isCreatureOrPlaneswalker()) {
            creatureBody.setText(cardRepresentation.powerToughness());
        } else {
            creatureBody.setVisibility(INVISIBLE);
        }
        drawPercentage.setText(cardRepresentation.getDrawPercentage());
    }

    private void addCountToCardView(CardRepresentation cardRepresentation) {
        for (int i = 1; i < cardRepresentation.count(); i++) {
            CardHeaderView cardHeaderView = new CardHeaderView(getContext(), cardRepresentation, cardRepresentation.count() - i);
            addView(cardHeaderView, 0);
        }
    }
}
