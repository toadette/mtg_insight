package de.avalax.mtg_insight.presentation.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;


@SuppressLint("ViewConstructor")
public class CardHeaderView extends LinearLayout {
    @Inject
    protected CardRepresentationToDrawable cardRepresentationToDrawable;

    private final int headerNumber;
    private View background;
    private TextView name;
    private TextView convertedManaCost;

    public CardHeaderView(Context context, CardRepresentation cardRepresentation, int headerNumber) {
        super(context);
        ((MtgInsightApplication) context.getApplicationContext()).inject(this);
        this.headerNumber = headerNumber;
        init(context);
        attributes(cardRepresentation);
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_card_header, this);
        background = findViewById(R.id.cardBackground);
        name = (TextView) findViewById(R.id.name);
        convertedManaCost = (TextView) findViewById(R.id.converted_mana_cost);
    }

    private void attributes(CardRepresentation cardRepresentation) {
        name.setText(cardRepresentation.name());
        convertedManaCost.setText(cardRepresentation.convertedManaCost());
        if (headerNumber > 1) {
            background.setBackground(cardRepresentationToDrawable.headerFrom(cardRepresentation));
        }
    }
}
