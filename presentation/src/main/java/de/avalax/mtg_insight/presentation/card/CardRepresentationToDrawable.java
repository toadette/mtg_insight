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

            case BLUE:

            case BLACK:

            case RED:

            case GREEN:
                return ContextCompat.getDrawable(context, R.drawable.background_green);
            case MULTICOLOR:

            case COLORLESS:
            default:
                return ContextCompat.getDrawable(context, R.drawable.background_colorless);
        }
    }

    public Drawable windowBackgroundFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context, R.drawable.window_colorless);
    }

    public Drawable cardBackgroundFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context, R.drawable.card_background_colorless);
    }

    public Drawable headerFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context, R.drawable.header_colorless);
    }
}
