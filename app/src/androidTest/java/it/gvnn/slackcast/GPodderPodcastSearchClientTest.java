package it.gvnn.slackcast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NoCache;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import it.gvnn.slackcast.mock.MockNetwork;
import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.PodcastDataResponse;
import it.gvnn.slackcast.search.PodcastSearchResponse;
import it.gvnn.slackcast.utils.VolleyResultListener;

import static org.assertj.core.api.Assertions.assertThat;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class GPodderPodcastSearchClientTest {

    @Test
    public void testSearch() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        InputStream mockStream = Robolectric.getShadowApplication().getAssets().open("mock/searchResult.json");
        byte[] mockResult = ByteStreams.toByteArray(mockStream);

        Gson gson = new Gson();
        final PodcastSearchResponse mockResponse = gson.fromJson(new String(mockResult), PodcastSearchResponse.class);

        MockNetwork network = new MockNetwork();
        network.setDataToReturn(mockResult);
        RequestQueue queue = new RequestQueue(new NoCache(), network);

        queue.start();

        GPodderPodcastSearchClient client = GPodderPodcastSearchClient.getInstance(null);
        client.setQueue(queue); // clearing the queue
        client.search("test", new VolleyResultListener<PodcastSearchResponse>() {

            @Override
            public void onResult(PodcastSearchResponse response) {
                assertThat(response.size()).isEqualTo(mockResponse.size());
                for (int i = 0; i < response.size(); i++) {
                    assertThat(response.get(i).getDescription()).isEqualTo(mockResponse.get(i).getDescription());
                    assertThat(response.get(i).getTitle()).isEqualTo(mockResponse.get(i).getTitle());
                    assertThat(response.get(i).getUrl()).isEqualTo(mockResponse.get(i).getUrl());
                    assertThat(response.get(i).getWebsite()).isEqualTo(mockResponse.get(i).getWebsite());
                }
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                signal.countDown();
                assertThat(error).isNull(); // never true
            }

        });
        signal.await();
    }

    @Test
    public void testGetPodcast() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        InputStream mockStream = Robolectric.getShadowApplication().getAssets().open("mock/podcast.json");
        byte[] mockResult = ByteStreams.toByteArray(mockStream);

        Gson gson = new Gson();
        final PodcastSearchResponse mockResponse = gson.fromJson(new String(mockResult), PodcastSearchResponse.class);

        MockNetwork network = new MockNetwork();
        network.setDataToReturn(mockResult);
        RequestQueue queue = new RequestQueue(new NoCache(), network);

        queue.start();

        GPodderPodcastSearchClient client = GPodderPodcastSearchClient.getInstance(null);
        client.setQueue(queue); // clearing the queue
        client.getPodcast("http://feeds.thisamericanlife.org/talpodcast", new VolleyResultListener<PodcastDataResponse>() {
            @Override
            public void onResult(PodcastDataResponse response) {
                assertThat(response.size()).isEqualTo(mockResponse.size());
                assertThat(response.get(0).getEpisodes().get(0).getReleaseDate().isBefore(new DateTime())).isTrue();
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                signal.countDown();
                assertThat(error).isNull(); // never true
            }
        });
        signal.await();
    }
}