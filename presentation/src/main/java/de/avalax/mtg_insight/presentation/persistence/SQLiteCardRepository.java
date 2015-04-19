package de.avalax.mtg_insight.presentation.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardRepository;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.card.TranslatingCardService;

public class SQLiteCardRepository implements CardRepository {
    protected static final String TABLE_NAME = "cards";
    private SQLiteOpenHelper sqLiteOpenHelper;
    private TranslatingCardService translatingCardService;

    public SQLiteCardRepository(SQLiteOpenHelper sqLiteOpenHelper, TranslatingCardService translatingCardService) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
        this.translatingCardService = translatingCardService;
    }

    @Override
    public void save(Card card) {
        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
        database.insertOrThrow(TABLE_NAME, null, getContentValues(card));
        database.close();
    }

    @Override
    public Card load(String cardName) throws CardNotFoundException {
        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[]{"json"},
                "cardname=?", new String[]{cardName}, null, null, null);
        if (cursor.moveToFirst()) {
            Card card = translatingCardService.cardFromJson(cursor.getString(0));
            cursor.close();
            database.close();
            return card;
        } else {
            cursor.close();
            database.close();
            throw new CardNotFoundException();
        }
    }

    @Override
    public void delete(Card card) {

    }

    private ContentValues getContentValues(Card card) {
        ContentValues values = new ContentValues();

        values.put("cardname", card.name());
        values.put("json", translatingCardService.jsonFromCard(card));
        return values;
    }
}
