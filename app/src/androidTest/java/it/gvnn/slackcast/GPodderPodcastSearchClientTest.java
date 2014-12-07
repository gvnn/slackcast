package it.gvnn.slackcast;

import android.test.InstrumentationTestCase;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NoCache;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import org.joda.time.DateTime;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import it.gvnn.slackcast.mock.MockNetwork;
import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.PodcastDataResponse;
import it.gvnn.slackcast.search.PodcastSearchResponse;
import it.gvnn.slackcast.utils.VolleyResultListener;

public class GPodderPodcastSearchClientTest extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testSearch() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        InputStream mockStream = getInstrumentation().getTargetContext().getAssets().open("mock/searchResult.json");
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
                assertEquals(response.size(), mockResponse.size());
                for (int i = 0; i < response.size(); i++) {
                    assertEquals(response.get(i).getDescription(), mockResponse.get(i).getDescription());
                    assertEquals(response.get(i).getTitle(), mockResponse.get(i).getTitle());
                    assertEquals(response.get(i).getUrl(), mockResponse.get(i).getUrl());
                    assertEquals(response.get(i).getWebsite(), mockResponse.get(i).getWebsite());
                }
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                signal.countDown();
                assertNull(error); // never true
            }

        });
        signal.await();
    }

    public void testGetPodcast() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        InputStream mockStream = getInstrumentation().getTargetContext().getAssets().open("mock/podcast.json");
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
                assertEquals(response.size(), mockResponse.size());
                assertTrue(response.get(0).getEpisodes().get(0).getReleaseDate().isBefore(new DateTime())); // never true
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                signal.countDown();
                assertNull(error); // never true
            }
        });
        signal.await();
    }
}