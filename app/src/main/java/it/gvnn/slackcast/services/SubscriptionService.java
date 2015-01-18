package it.gvnn.slackcast.services;

import com.activeandroid.query.Select;

import java.util.List;

import it.gvnn.slackcast.entities.Podcast;

public class SubscriptionService {
    private static SubscriptionService instance = null;

    protected SubscriptionService() {
        // Exists only to defeat instantiation.
    }

    public static SubscriptionService getInstance() {
        if (instance == null) {
            instance = new SubscriptionService();
        }
        return instance;
    }

    public void addPodcast(it.gvnn.slackcast.search.Podcast mPodcast) {
        Podcast podcast = new Podcast(mPodcast);
        podcast.save();
    }

    public List<Podcast> getPodcasts() {
        return new Select().from(Podcast.class).orderBy("title").execute();
    }
}
