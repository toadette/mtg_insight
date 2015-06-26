package de.avalax.mtg_insight.presentation.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStream;

import de.avalax.mtg_insight.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", sdk=18)
public class MtgInsightSQLiteOpenHelperTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.application.getApplicationContext();
    }

    @Test(expected = RuntimeException.class)
    public void newInstanceWithWrongRessource_shouldCreateDatabase() throws Exception {
        MtgInsightSQLiteOpenHelper openHelper = new MtgInsightSQLiteOpenHelper("MtgInsightSQLiteOpenHelperTest", 1, context, R.raw.mtg_insight_db) {
            protected void insertFromStream(InputStream inputStream, SQLiteDatabase database) throws IOException {
                throw new IOException();
            }
        };
        openHelper.getWritableDatabase();
    }

    @Test
    public void newInstance_shouldCreateDatabaseWithVersionOne() throws Exception {
        MtgInsightSQLiteOpenHelper openHelper = new MtgInsightSQLiteOpenHelper("MtgInsightSQLiteOpenHelperTest", 1, context, R.raw.mtg_insight_db);
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();

        assertThat(writableDatabase.getVersion(), equalTo(1));
    }

    @Test
    public void newInstanceAfterUpgrade_shouldCreateDatabaseWithVersionTwo() throws Exception {
        MtgInsightSQLiteOpenHelper openHelper = new MtgInsightSQLiteOpenHelper("MtgInsightSQLiteOpenHelperTest", 1, context, R.raw.mtg_insight_db);
        SQLiteDatabase writableDatabase = openHelper.getWritableDatabase();
        writableDatabase.close();
        MtgInsightSQLiteOpenHelper newOpenHelper = new MtgInsightSQLiteOpenHelper("MtgInsightSQLiteOpenHelperTest", 2, context, R.raw.mtg_insight_db);
        SQLiteDatabase newWritableDatabase = newOpenHelper.getWritableDatabase();

        assertThat(newWritableDatabase.getVersion(), equalTo(2));
    }
}