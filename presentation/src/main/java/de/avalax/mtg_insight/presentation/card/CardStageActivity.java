package de.avalax.mtg_insight.presentation.card;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import de.avalax.mtg_insight.R;

public class CardStageActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_stage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.card_stage_toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("CARD_DEMO")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CardDemoFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_stage, menu);
        return true;
    }
}