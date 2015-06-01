package de.avalax.mtg_insight.presentation;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.avalax.mtg_insight.R;
import de.avalax.mtg_insight.application.launcher.LauncherApplicationService;
import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.application.port.adapter.CachedCardService;
import de.avalax.mtg_insight.domain.model.card.CardRepository;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.port.adapter.service.card.TranslatingCardService;
import de.avalax.mtg_insight.port.adapter.service.gatherer.GathererCardService;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.deck.TappedOutDeckService;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;
import de.avalax.mtg_insight.presentation.playmat.PlaymatFragment;
import de.avalax.mtg_insight.presentation.card.CardHeaderView;
import de.avalax.mtg_insight.presentation.card.CardRepresentationToDrawable;
import de.avalax.mtg_insight.presentation.card.CardView;
import de.avalax.mtg_insight.presentation.persistence.MtgInsightSQLiteOpenHelper;
import de.avalax.mtg_insight.presentation.persistence.SQLiteCardRepository;
import de.avalax.mtg_insight.presentation.persistence.AndroidCacheStrategy;
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
        return new TappedOutDeckService(cardService);
    }

    @Provides
    @Singleton
    CardService provideCardService(CacheStrategy cacheStrategy) {
        GathererCardService gathererCardService = new GathererCardService(new ManaTokenizer(), new ColorMatcher(), new AbilityTokenizer());
        return new CachedCardService(gathererCardService, cacheStrategy);
    }

    @Provides
    @Singleton
    CacheStrategy provideCacheStrategy(CardRepository cardRepository) {
        return new AndroidCacheStrategy(cardRepository);
    }

    @Provides
    @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper() {
        return new MtgInsightSQLiteOpenHelper("mtg_insight", 1, context, R.raw.mtg_insight_db);
    }

    @Provides
    @Singleton
    CardRepository provideCardRepository(SQLiteOpenHelper sqLiteOpenHelper, TranslatingCardService translatingCardService) {
        return new SQLiteCardRepository(sqLiteOpenHelper, translatingCardService);
    }

    @Provides
    @Singleton
    TranslatingCardService provideTranslatingCardService() {
        return new TranslatingCardService();
    }
}
