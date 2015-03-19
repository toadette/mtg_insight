package de.avalax.mtg_insight.presentation;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.avalax.mtg_insight.application.launcher.LauncherApplicationService;
import de.avalax.mtg_insight.presentation.card.CardHeaderView;
import de.avalax.mtg_insight.presentation.card.CardRepresentationToDrawable;
import de.avalax.mtg_insight.presentation.card.CardView;
import de.avalax.mtg_insight.presentation.launcher.LauncherFragment;

@Module(injects = {
        LauncherFragment.class,
        CardView.class,
        CardHeaderView.class
})
public class MtgInsightModule {
    private Context context;

    public MtgInsightModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LauncherApplicationService provideManageWorkout(){
        return new LauncherApplicationService();
    }

    @Provides
    @Singleton
    CardRepresentationToDrawable provideCardRepresentationToDrawable(){
        return new CardRepresentationToDrawable(context);
    }
}
