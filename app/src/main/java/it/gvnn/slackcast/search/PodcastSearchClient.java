package it.gvnn.slackcast.search;

public interface PodcastSearchClient {
    public void search(String query, SearchResultListener resultListener);
}
