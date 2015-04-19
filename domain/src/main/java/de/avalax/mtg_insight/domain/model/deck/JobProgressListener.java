package de.avalax.mtg_insight.domain.model.deck;

public interface JobProgressListener {
    void publishProgress(int progress);
}
