package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.avalax.mtg_insight.R;


public class CardView extends LinearLayout {
    private View background;
    private View window;
    private TextView name;
    private TextView convertedManaCost;
    private int count;

    public CardView(Context context) {
        super(context);
        init(context);
        count = 1;
        addCountToCardView();
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        attributs(attrs);
        addCountToCardView();
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        attributs(attrs);
        addCountToCardView();
    }

    private void init(Context context) {
        inflate(context, R.layout.fragment_single_card, this);
        setOrientation(VERTICAL);
        background = findViewById(R.id.cardBackground);
        window = findViewById(R.id.cardWindow);
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
        if (a.getDrawable(R.styleable.CardView_window) != null) {
            window.setBackground(a.getDrawable(R.styleable.CardView_window));
        }
        count = a.getInteger(R.styleable.CardView_count, 1);
        a.recycle();
    }

    private void addCountToCardView() {
        for (int i=1;i<count;i++) {
            addView(new CardHeaderView(getContext()));
        }
    }
}
