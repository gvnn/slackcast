package it.gvnn.slackcast;

import android.test.InstrumentationTestCase;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NoCache;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import it.gvnn.slackcast.mock.MockNetwork;
import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.SearchResultListener;

public class GPodderPodcastSearchClientTest extends InstrumentationTestCase {
    final CountDownLatch signal = new CountDownLatch(1);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testSearch() throws Exception {

        InputStream mockStream = getInstrumentation().getTargetContext().getAssets().open("mock/searchResult.json");
        byte[] mockResult = ByteStreams.toByteArray(mockStream);

        Gson gson = new Gson();
        final PodcastSearchResponse mockResponse = gson.fromJson(new String(mockResult), PodcastSearchResponse.class);

        MockNetwork network = new MockNetwork();
        network.setDataToReturn(mockResult);
        RequestQueue queue = new RequestQueue(new NoCache(), network);

        queue.start();

        GPodderPodcastSearchClient client = GPodderPodcastSearchClient.getInstance(queue);
        client.search("test", new SearchResultListener() {

            @Override
            public void onResult(PodcastSearchResponse response) {
                assertEquals(response.size(), mockResponse.size());
                for (int i = 0; i < response.size(); i++) {
                    assertEquals(response.get(i).description, mockResponse.get(i).description);
                    assertEquals(response.get(i).title, mockResponse.get(i).title);
                    assertEquals(response.get(i).url, mockResponse.get(i).url);
                    assertEquals(response.get(i).website, mockResponse.get(i).website);
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

}