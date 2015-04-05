package de.avalax.mtg_insight.presentation.card;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import de.avalax.mtg_insight.R;

public class CardStageActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_stage);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("CARD_DEMO")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CardDemoFragment()).commit();
        }
    }
}