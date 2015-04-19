package de.avalax.mtg_insight.presentation.persistence;

import android.content.Context;
import android.database.DatabaseUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CardRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class SQLiteCardRepositoryTest {

    private CardRepository setRepository;
    private MtgInsightSQLiteOpenHelper sqLiteOpenHelper;

    @Before
    public void setUp() throws Exception {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        sqLiteOpenHelper = new MtgInsightSQLiteOpenHelper("SQLiteCardRepositoryTest", 1, context, R.raw.mtg_insight_db);
        setRepository = new SQLiteCardRepository(sqLiteOpenHelper);
    }

    @Test
    public void newCard_shouldBePersisted() throws Exception {
        Card card = new CardBuilder("cardname").build();

        setRepository.save(card);

        long cardCount = DatabaseUtils.queryNumEntries(sqLiteOpenHelper.getReadableDatabase(), SQLiteCardRepository.TABLE_NAME);
        assertThat(cardCount, equalTo(1L));
    }
}