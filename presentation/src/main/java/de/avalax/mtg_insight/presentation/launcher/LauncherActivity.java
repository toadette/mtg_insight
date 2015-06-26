package de.avalax.mtg_insight.presentation.launcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.avalax.mtg_insight.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LauncherFragment.newInstance()).commit();
    }
}