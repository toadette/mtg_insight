package de.avalax.mtg_insight.presentation.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.launcher.LauncherApplicationService;
import de.avalax.mtg_insight.presentation.MtgInsightApplication;
import de.avalax.mtg_insight.presentation.card.CardDemoActivity;


public class LauncherFragment extends Fragment {

    @Inject
    protected LauncherApplicationService launcherApplicationService;

    public static LauncherFragment newInstance() {
        return new LauncherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_launcher, container, false);
        ButterKnife.inject(this, view);
        ((MtgInsightApplication) getActivity().getApplication()).inject(this);
        return view;
    }

    @OnClick(R.id.cardDemo)
    protected void cardDemo() {
        Intent intent = new Intent(getActivity(), CardDemoActivity.class);
        startActivity(intent);
    }
}
