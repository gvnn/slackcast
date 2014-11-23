package it.gvnn.slackcast.search;

import com.android.volley.RequestQueue;

public class PodcastSearchClientFactory {
    public static PodcastSearchClient getSearchClient(Services service, RequestQueue queue) {
        if (service == Services.GPODDER) return gPodderPodcastSearchClient.getInstance(queue);
        return null;
    }
}
