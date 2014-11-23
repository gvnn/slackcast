package it.gvnn.slackcast.search;

import com.android.volley.RequestQueue;

import java.util.List;

import it.gvnn.slackcast.Podcast;

public class gPodderPodcastSearchClient implements PodcastSearchClient {

    private static final String GPODDER_API_BASE_URL = "https://gpodder.net";
    private static gPodderPodcastSearchClient instance = null;
    private RequestQueue volleyQueue;

    protected gPodderPodcastSearchClient(RequestQueue queue) {
        volleyQueue = queue;
    }

    public static gPodderPodcastSearchClient getInstance(RequestQueue queue) {
        if (instance == null) {
            instance = new gPodderPodcastSearchClient(queue);
        }
        return instance;
    }

    @Override
    public List<Podcast> search() {
        return null;
    }
}
