package de.avalax.mtg_insight.presentation;

import android.app.Application;
import dagger.ObjectGraph;

public class MtgInsightApplication extends Application {
    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();
        graph = ObjectGraph.create(new MtgInsightModule(this));
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
