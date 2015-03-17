package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.avalax.mtg_insight.R;


public class CardHeaderView extends LinearLayout {
    private final int headerNumber;
    private View background;
    private TextView name;
    private TextView convertedManaCost;

    public CardHeaderView(Context context, AttributeSet attrs, int headerNumber) {
        super(context, attrs);
        this.headerNumber = headerNumber;
        init(context);
        attributs(attrs);
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_card_header, this);
        background = findViewById(R.id.cardBackground);
        name = (TextView) findViewById(R.id.name);
        convertedManaCost = (TextView) findViewById(R.id.converted_mana_cost);
        if (headerNumber > 1) {
            setBackground(getResources().getDrawable(R.drawable.card_background_colorless));
        }
    }

    private void attributs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CardView);
        if (a.getString(R.styleable.CardView_name) != null) {
            name.setText(a.getString(R.styleable.CardView_name));
        }
        if (a.getString(R.styleable.CardView_converted_mana_cost) != null) {
            convertedManaCost.setText(a.getString(R.styleable.CardView_converted_mana_cost));
        }
        if (a.getDrawable(R.styleable.CardView_background) != null) {
            background.setBackground(getResources().getDrawable(R.drawable.header_multicolor));
        }
        a.recycle();
    }
}
