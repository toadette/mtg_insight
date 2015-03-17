package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.avalax.mtg_insight.R;


public class CardHeaderView extends LinearLayout {
    private View background;
    private TextView name;
    private TextView convertedManaCost;

    public CardHeaderView(Context context) {
        super(context);
        init(context);
    }

    public CardHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        attributs(attrs);
    }

    public CardHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        attributs(attrs);
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_card_header, this);
        background = findViewById(R.id.cardBackground);
        name = (TextView) findViewById(R.id.name);
        convertedManaCost = (TextView) findViewById(R.id.converted_mana_cost);
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
            background.setBackground(a.getDrawable(R.styleable.CardView_background));
        }
        a.recycle();
    }
}
