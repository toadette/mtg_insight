package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;

public class CardRepresentationToDrawable {
    private Context context;

    public CardRepresentationToDrawable(Context context) {
        this.context = context;
    }

    public Drawable backgroundFrom(CardRepresentation cardRepresentation) {
        if (cardRepresentation == null) {
            return ContextCompat.getDrawable(context, R.drawable.background_colorless);
        }
        switch (cardRepresentation.color()) {
            case WHITE:
                return ContextCompat.getDrawable(context, R.drawable.background_white);
            case BLUE:
                return ContextCompat.getDrawable(context, R.drawable.background_blue);
            case BLACK:
                return ContextCompat.getDrawable(context, R.drawable.background_black);
            case RED:
                return ContextCompat.getDrawable(context, R.drawable.background_red);
            case GREEN:
                return ContextCompat.getDrawable(context, R.drawable.background_green);
            case MULTICOLOR:
                return ContextCompat.getDrawable(context, R.drawable.background_multicolor);
            case COLORLESS:
            default:
                return ContextCompat.getDrawable(context, R.drawable.background_colorless);
        }
    }

    public Drawable windowBackgroundFrom(CardRepresentation cardRepresentation) {
        if (cardRepresentation == null) {
            return ContextCompat.getDrawable(context, R.drawable.window_colorless);
        }
        switch (cardRepresentation.color()) {
            case WHITE:
                return ContextCompat.getDrawable(context, R.drawable.window_white);
            case BLUE:
                return ContextCompat.getDrawable(context, R.drawable.window_blue);
            case BLACK:
                return ContextCompat.getDrawable(context, R.drawable.window_black);
            case RED:
                return ContextCompat.getDrawable(context, R.drawable.window_red);
            case GREEN:
                return ContextCompat.getDrawable(context, R.drawable.window_green);
            case MULTICOLOR:
                return ContextCompat.getDrawable(context, R.drawable.window_multicolor);
            case COLORLESS:
            default:
                return ContextCompat.getDrawable(context, R.drawable.window_colorless);
        }
    }

    public Drawable cardBackgroundFrom(CardRepresentation cardRepresentation) {
        if (cardRepresentation == null) {
            return ContextCompat.getDrawable(context, R.drawable.card_background_colorless);
        }
        switch (cardRepresentation.color()) {
            case WHITE:
                return ContextCompat.getDrawable(context, R.drawable.card_background_white);
            case BLUE:
                return ContextCompat.getDrawable(context, R.drawable.card_background_blue);
            case BLACK:
                return ContextCompat.getDrawable(context, R.drawable.card_background_black);
            case RED:
                return ContextCompat.getDrawable(context, R.drawable.card_background_red);
            case GREEN:
                return ContextCompat.getDrawable(context, R.drawable.card_background_green);
            case MULTICOLOR:
                return ContextCompat.getDrawable(context, R.drawable.card_background_multicolor);
            case COLORLESS:
            default:
                return ContextCompat.getDrawable(context, R.drawable.card_background_colorless);
        }
    }

    public Drawable headerFrom(CardRepresentation cardRepresentation) {
        if (cardRepresentation == null) {
            return ContextCompat.getDrawable(context, R.drawable.header_colorless);
        }
        switch (cardRepresentation.color()) {
            case WHITE:
                return ContextCompat.getDrawable(context, R.drawable.header_white);
            case BLUE:
                return ContextCompat.getDrawable(context, R.drawable.header_blue);
            case BLACK:
                return ContextCompat.getDrawable(context, R.drawable.header_black);
            case RED:
                return ContextCompat.getDrawable(context, R.drawable.header_red);
            case GREEN:
                return ContextCompat.getDrawable(context, R.drawable.header_green);
            case MULTICOLOR:
                return ContextCompat.getDrawable(context, R.drawable.header_multicolor);
            case COLORLESS:
            default:
                return ContextCompat.getDrawable(context, R.drawable.header_colorless);
        }
    }
}
