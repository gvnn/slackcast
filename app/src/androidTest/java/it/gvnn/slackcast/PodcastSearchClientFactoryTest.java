package it.gvnn.slackcast;

import com.android.volley.toolbox.NoCache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClientFactory;
import it.gvnn.slackcast.search.Providers;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class PodcastSearchClientFactoryTest {
    @Test
    public void testGetGPodderInstance() throws Exception {
        PodcastSearchClientFactory clientInstance = PodcastSearchClientFactory.getInstance(new NoCache());
        PodcastSearchClient searchClient = clientInstance.getSearchClient(Providers.GPODDER);
        assertThat(searchClient).isInstanceOf(GPodderPodcastSearchClient.class);
    }
}
