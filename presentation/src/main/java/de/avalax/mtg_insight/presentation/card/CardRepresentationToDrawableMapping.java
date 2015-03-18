package de.avalax.mtg_insight.presentation.card;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.representation.CardRepresentation;

public class CardRepresentationToDrawableMapping {
    public int backgroundFrom(CardRepresentation cardRepresentation) {
        return R.drawable.background_green;
    }

    public int windowBackgroundFrom(CardRepresentation cardRepresentation) {
        return R.drawable.window_green;
    }

    public int cardBackgroundFrom(CardRepresentation cardRepresentation) {
        return R.drawable.card_background_green;
    }

    public int headerFrom(CardRepresentation cardRepresentation) {
        return R.drawable.header_green;
    }
}
