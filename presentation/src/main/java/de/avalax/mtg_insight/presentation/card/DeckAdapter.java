package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.deck.Deck;

public class DeckAdapter extends BaseAdapter {
    private List<Card> cards;
    private Context context;

    public DeckAdapter(Context context, Deck deck) {
        this.context = context;
        cards = deck.deck();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: group same cards in constructor as CardRepresentation
        return new CardView(context, new CardRepresentation(cards.get(position)));
    }
}
