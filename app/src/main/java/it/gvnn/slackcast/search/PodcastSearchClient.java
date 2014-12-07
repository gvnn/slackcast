package it.gvnn.slackcast.search;

import it.gvnn.slackcast.utils.VolleyResultListener;

public interface PodcastSearchClient {
    public void search(String query, VolleyResultListener<PodcastSearchResponse> resultListener);
    public void getPodcast(String podcastIdentifier, VolleyResultListener<PodcastDataResponse> resultListener);
}
