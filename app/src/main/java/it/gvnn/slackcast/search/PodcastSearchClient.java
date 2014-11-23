package it.gvnn.slackcast.search;

import java.util.List;

import it.gvnn.slackcast.Podcast;

public interface PodcastSearchClient {
    public List<Podcast> search();
}
