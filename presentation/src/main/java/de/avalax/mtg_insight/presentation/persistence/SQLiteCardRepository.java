package de.avalax.mtg_insight.presentation.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardRepository;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;

public class SQLiteCardRepository implements CardRepository {
    protected static final String TABLE_NAME = "cards";
    private SQLiteOpenHelper sqLiteOpenHelper;

    public SQLiteCardRepository(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    @Override
    public void save(Card card) {
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        database.insertOrThrow(TABLE_NAME, null, getContentValues(card));
        database.close();
    }

    @Override
    public Card load(String cardName) throws CardNotFoundException {
        return null;
    }

    @Override
    public void delete(Card card) {

    }

    private ContentValues getContentValues(Card card) {
        ContentValues values = new ContentValues();

        values.put("cardname", card.name());
        values.put("json","todo ... do the json string");
        return values;
    }
}
