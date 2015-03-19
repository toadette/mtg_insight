package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;


public class CardHeaderView extends LinearLayout {
    @Inject
    protected CardRepresentationToDrawable cardRepresentationToDrawable;

    private final int headerNumber;
    private View background;
    private TextView name;
    private TextView convertedManaCost;

    @Deprecated
    public CardHeaderView(Context context, AttributeSet attrs, int headerNumber) {
        super(context, attrs);
        this.headerNumber = headerNumber;
        init(context);
        attributs(attrs);
    }

    public CardHeaderView(Context context, CardRepresentation cardRepresentation, int headerNumber) {
        super(context);
        ((MtgInsightApplication) context.getApplicationContext()).inject(this);
        this.headerNumber = headerNumber;
        init(context);
        attributs(cardRepresentation);
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_card_header, this);
        background = findViewById(R.id.cardBackground);
        name = (TextView) findViewById(R.id.name);
        convertedManaCost = (TextView) findViewById(R.id.converted_mana_cost);
    }

    @Deprecated
    private void attributs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CardView);
        if (a.getString(R.styleable.CardView_name) != null) {
            name.setText(a.getString(R.styleable.CardView_name));
        }
        if (a.getString(R.styleable.CardView_converted_mana_cost) != null) {
            convertedManaCost.setText(a.getString(R.styleable.CardView_converted_mana_cost));
        }
        if (a.getDrawable(R.styleable.CardView_background) != null) {
            background.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.header_multicolor));
        }
        if (headerNumber > 1) {
            setBackground(ContextCompat.getDrawable(getContext(),R.drawable.card_background_colorless));
        }
        a.recycle();
    }

    private void attributs(CardRepresentation cardRepresentation) {
        name.setText(cardRepresentation.name());
        convertedManaCost.setText(cardRepresentation.convertedManaCost());
        background.setBackground(cardRepresentationToDrawable.headerFrom(cardRepresentation));
        if (headerNumber > 1) {
            setBackground(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentation));
        }
    }
}
