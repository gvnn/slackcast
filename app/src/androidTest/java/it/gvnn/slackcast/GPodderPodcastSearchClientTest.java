package it.gvnn.slackcast;

import android.util.Log;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;

import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.SearchResultListener;

public class GPodderPodcastSearchClientTest extends TestCase {
    private static final String TAG = "GPodderPodcastSearchClientTest";
    final CountDownLatch signal = new CountDownLatch(1);

    @Override
    protected void setUp() throws Exception {

        super.setUp();
    }

    public void testSearch() throws Exception {
//        MockNetwork network = new MockNetwork();
//        RequestQueue queue = new RequestQueue(, network);

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        RequestQueue queue = new RequestQueue(new NoCache(), network);
        // Start the queue
        queue.start();

        GPodderPodcastSearchClient client = GPodderPodcastSearchClient.getInstance(queue);
        client.search("test", new SearchResultListener() {

            @Override
            public void onResult(PodcastSearchResponse response) {
                Log.d(TAG, response.toString());
                assertEquals(0, 1);
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, error.toString());
                assertEquals(0, 1);
                signal.countDown();
            }

        });
        signal.await();
    }

}
