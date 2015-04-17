package de.avalax.mtg_insight.presentation;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.avalax.mtg_insight.application.launcher.LauncherApplicationService;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.gatherer.GathererCardService;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.deck.TappedOutDeckService;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;
import de.avalax.mtg_insight.presentation.card.PlaymatFragment;
import de.avalax.mtg_insight.presentation.card.CardHeaderView;
import de.avalax.mtg_insight.presentation.card.CardRepresentationToDrawable;
import de.avalax.mtg_insight.presentation.card.CardView;
import de.avalax.mtg_insight.presentation.launcher.LauncherFragment;

@Module(injects = {
        LauncherFragment.class,
        CardView.class,
        CardHeaderView.class,
        PlaymatFragment.class
})
public class MtgInsightModule {
    private Context context;

    public MtgInsightModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LauncherApplicationService provideManageWorkout() {
        return new LauncherApplicationService();
    }

    @Provides
    @Singleton
    CardRepresentationToDrawable provideCardRepresentationToDrawable() {
        return new CardRepresentationToDrawable(context);
    }

    @Provides
    @Singleton
    DeckService provideDeckService(CardService cardService) {
        return new TappedOutDeckService(null);
    }

    @Provides
    @Singleton
    CardService provideCardService() {
        return new GathererCardService(new ManaTokenizer(), new ColorMatcher(), new AbilityTokenizer());
    }
}
