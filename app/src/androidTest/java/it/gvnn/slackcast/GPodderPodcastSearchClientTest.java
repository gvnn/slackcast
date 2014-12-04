package it.gvnn.slackcast;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NoCache;
import com.google.common.io.ByteStreams;

import java.util.concurrent.CountDownLatch;

import it.gvnn.slackcast.mock.MockNetwork;
import it.gvnn.slackcast.search.GPodderPodcastSearchClient;
import it.gvnn.slackcast.search.SearchResultListener;

public class GPodderPodcastSearchClientTest extends InstrumentationTestCase {
    private static final String TAG = "GPodderPodcastSearchClientTest";
    final CountDownLatch signal = new CountDownLatch(1);

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testSearch() throws Exception {
        MockNetwork network = new MockNetwork();

        network.setDataToReturn(ByteStreams.toByteArray(getInstrumentation().getTargetContext().getAssets().open("mock/searchResult.json")));
        RequestQueue queue = new RequestQueue(new NoCache(), network);

        // Start the queue
        queue.start();

        GPodderPodcastSearchClient client = GPodderPodcastSearchClient.getInstance(queue);
        client.search("test", new SearchResultListener() {

            @Override
            public void onResult(PodcastSearchResponse response) {
                Log.d(TAG, response.toString());
                assertEquals(response.size(), 2);
                signal.countDown();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d(TAG, error.toString());
                signal.countDown();
                assertNull(error); // never true
            }

        });
        signal.await();
    }

}
