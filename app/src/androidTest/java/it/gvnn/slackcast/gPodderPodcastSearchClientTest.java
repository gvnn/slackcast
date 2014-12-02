package it.gvnn.slackcast;

import junit.framework.TestCase;

import java.io.File;

import it.gvnn.slackcast.search.PodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClientFactory;
import it.gvnn.slackcast.search.Services;
import it.gvnn.slackcast.search.gPodderPodcastSearchClient;

public class gPodderPodcastSearchClientTest extends TestCase {

    public void testGetInstance() throws Exception {
        PodcastSearchClientFactory clientInstance = PodcastSearchClientFactory.getInstance(new File("./cache"));
        PodcastSearchClient searchClient = clientInstance.getSearchClient(Services.GPODDER);
        assertEquals(gPodderPodcastSearchClient.class, searchClient.getClass());
    }

}
