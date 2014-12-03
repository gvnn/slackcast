package it.gvnn.slackcast;

import com.android.volley.toolbox.NoCache;

import junit.framework.TestCase;

import java.io.File;

import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClientFactory;
import it.gvnn.slackcast.search.Services;

public class PodcastSearchClientFactoryTest extends TestCase {

    public void testGetGPodderInstance() throws Exception {
        PodcastSearchClientFactory clientInstance = PodcastSearchClientFactory.getInstance(new NoCache());
        PodcastSearchClient searchClient = clientInstance.getSearchClient(Services.GPODDER);
        assertEquals(GPodderPodcastSearchClient.class, searchClient.getClass());
    }

}
