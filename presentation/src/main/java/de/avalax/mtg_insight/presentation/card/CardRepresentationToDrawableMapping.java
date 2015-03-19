package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;

public class CardRepresentationToDrawableMapping {
    private Context context;

    public CardRepresentationToDrawableMapping(Context context) {
        this.context = context;
    }

    public Drawable backgroundFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context,R.drawable.background_colorless);
    }

    public Drawable windowBackgroundFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context,R.drawable.window_green);
    }

    public Drawable cardBackgroundFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context,R.drawable.card_background_green);
    }

    public Drawable headerFrom(CardRepresentation cardRepresentation) {
        return ContextCompat.getDrawable(context,R.drawable.header_green);
    }
}
