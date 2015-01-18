package it.gvnn.slackcast.search;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class PodcastSearchClientFactory {

    private static PodcastSearchClientFactory instance = null;
    private RequestQueue mRequestQueue;

    public PodcastSearchClientFactory(java.io.File rootDirectory) {
        // Instantiate the cache
        Cache cache = new DiskBasedCache(rootDirectory, 1024 * 1024); // 1MB cap
        setUpQueue(cache);
    }

    public PodcastSearchClientFactory(Cache cache) {
        setUpQueue(cache);
    }

    public static PodcastSearchClientFactory getInstance(java.io.File rootDirectory) {
        if (instance == null) {
            instance = new PodcastSearchClientFactory(rootDirectory);
        }
        return instance;
    }

    public static PodcastSearchClientFactory getInstance(Cache cache) {
        if (instance == null) {
            instance = new PodcastSearchClientFactory(cache);
        }
        return instance;
    }

    public void setUpQueue(Cache cache) {
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();
    }

    public PodcastSearchClient getSearchClient(Providers service) {
        if (service == Providers.GPODDER)
            return GPodderPodcastSearchClient.getInstance(mRequestQueue);
        return null;
    }


}
