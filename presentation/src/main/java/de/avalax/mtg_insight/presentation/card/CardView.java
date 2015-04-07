package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import javax.inject.Inject;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;


public class CardView extends LinearLayout {
    @Inject
    protected CardRepresentationToDrawable cardRepresentationToDrawable;

    private View background;
    private View window;
    private TextView name;
    private TextView convertedManaCost;
    private TextView creatureBody;
    private int count;

    public CardView(Context context, CardRepresentation cardRepresentation) {
        super(context);
        ((MtgInsightApplication) context.getApplicationContext()).inject(this);
        init(context);
        attributes(cardRepresentation);
        addCountToCardView(cardRepresentation);
    }

    @Deprecated
    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((MtgInsightApplication) context.getApplicationContext()).inject(this);
        init(context);
        attributs(attrs);
        addCountToCardView(attrs);
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_card, this);
        setOrientation(VERTICAL);
        background = findViewById(R.id.cardBackground);
        window = findViewById(R.id.cardWindow);
        name = (TextView) findViewById(R.id.name);
        convertedManaCost = (TextView) findViewById(R.id.converted_mana_cost);
        creatureBody = (TextView) findViewById(R.id.creatureBody);
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void attributes(CardRepresentation cardRepresentation) {
        name.setText(cardRepresentation.name());
        convertedManaCost.setText(cardRepresentation.convertedManaCost());
        background.setBackground(cardRepresentationToDrawable.backgroundFrom(cardRepresentation));
        window.setBackground(cardRepresentationToDrawable.windowBackgroundFrom(cardRepresentation));
        if (cardRepresentation.count() > 1) {
            setBackground(cardRepresentationToDrawable.cardBackgroundFrom(cardRepresentation));
        }
        if (cardRepresentation.isCreature()) {
            creatureBody.setText(cardRepresentation.power() + " / " + cardRepresentation.toughness());
        } else {
            creatureBody.setVisibility(INVISIBLE);
        }
    }

    @Deprecated
    private void attributs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CardView);
        if (a.getString(R.styleable.MtgInsightCard_name) != null) {
            name.setText(a.getString(R.styleable.MtgInsightCard_name));
        }
        if (a.getString(R.styleable.MtgInsightCard_converted_mana_cost) != null) {
            convertedManaCost.setText(a.getString(R.styleable.MtgInsightCard_converted_mana_cost));
        }
        if (a.getDrawable(R.styleable.MtgInsightCard_card_header) != null) {
            background.setBackground(a.getDrawable(R.styleable.MtgInsightCard_card_header));
        }
        if (a.getDrawable(R.styleable.MtgInsightCard_window) != null) {
            window.setBackground(a.getDrawable(R.styleable.MtgInsightCard_window));
        }
        count = a.getInteger(R.styleable.MtgInsightCard_count, 1);
        if (count > 1 && a.getDrawable(R.styleable.MtgInsightCard_card_background) != null) {
            setBackground(cardRepresentationToDrawable.cardBackgroundFrom(null));
        }
        a.recycle();
    }

    @Deprecated
    private void addCountToCardView(AttributeSet attrs) {
        for (int i = 1; i < count; i++) {
            addView(new CardHeaderView(getContext(), attrs, count - i), 0);
        }
    }

    private void addCountToCardView(CardRepresentation cardRepresentation) {
        for (int i = 1; i < cardRepresentation.count(); i++) {
            CardHeaderView cardHeaderView = new CardHeaderView(getContext(), cardRepresentation, cardRepresentation.count() - i);
            cardHeaderView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            addView(cardHeaderView, 0);
        }
    }
}
