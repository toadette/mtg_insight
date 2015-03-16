package de.avalax.mtg_insight.presentation.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.presentation.card.CardDemoActivity;

public class LauncherActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LauncherFragment.newInstance()).commit();
    }
}