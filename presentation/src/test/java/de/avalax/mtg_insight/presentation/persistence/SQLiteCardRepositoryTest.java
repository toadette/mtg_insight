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
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.card.TranslatingCardService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", sdk = 18)
public class SQLiteCardRepositoryTest {

    private CardRepository cardRepository;
    private MtgInsightSQLiteOpenHelper sqLiteOpenHelper;

    @Before
    public void setUp() throws Exception {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        sqLiteOpenHelper = new MtgInsightSQLiteOpenHelper("SQLiteCardRepositoryTest", 1, context, R.raw.mtg_insight_db);
        cardRepository = new SQLiteCardRepository(sqLiteOpenHelper, new TranslatingCardService());
    }

    @Test
    public void newCard_shouldBePersisted() throws Exception {
        Card card = new CardBuilder("cardname").build();

        cardRepository.save(card);

        long cardCount = DatabaseUtils.queryNumEntries(sqLiteOpenHelper.getReadableDatabase(), SQLiteCardRepository.TABLE_NAME);
        assertThat(cardCount, equalTo(1L));
    }

    @Test(expected = CardNotFoundException.class)
    public void loadUnknownCard_shouldThrowCardNotFoundException() throws Exception {
        cardRepository.load("cardname");
    }

    @Test
    public void persistedCard_shouldBeLoadedFromRepository() throws Exception {
        Card persistedCard = new CardBuilder("cardname").build();
        cardRepository.save(persistedCard);

        Card card = cardRepository.load("cardname");

        assertThat(card.name(), equalTo(persistedCard.name()));
    }
}