package it.gvnn.slackcast.search;

import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.gvnn.slackcast.PodcastSearchResponse;
import it.gvnn.slackcast.utils.GsonRequest;

public class GPodderPodcastSearchClient implements PodcastSearchClient {

    private static final String TAG = "GPodderPodcastSearchClient";

    private static final String GPODDER_API_BASE_URL = "https://gpodder.net";
    private static GPodderPodcastSearchClient instance = null;
    private RequestQueue volleyQueue;

    protected GPodderPodcastSearchClient(RequestQueue queue) {
        volleyQueue = queue;
    }

    public static GPodderPodcastSearchClient getInstance(RequestQueue queue) {
        if (instance == null) {
            instance = new GPodderPodcastSearchClient(queue);
        }
        return instance;
    }

    @Override
    public void search(String query, SearchResultListener resultListener) {

        GsonRequest<PodcastSearchResponse> searchRequest =
                new GsonRequest<PodcastSearchResponse>(
                        Uri.parse(GPODDER_API_BASE_URL.concat("/search.json"))
                                .buildUpon()
                                .appendQueryParameter("q", query)
                                .build().toString(),
                        PodcastSearchResponse.class,
                        null,
                        this.createSuccessListener(resultListener),
                        this.createErrorListener(resultListener)
                );

        // Add the request to the RequestQueue.
        volleyQueue.add(searchRequest);
    }

    private Response.Listener<PodcastSearchResponse> createSuccessListener(final SearchResultListener resultListener) {
        return new Response.Listener<PodcastSearchResponse>() {
            @Override
            public void onResponse(PodcastSearchResponse response) {
                Log.d(TAG, "Response is: ".concat(response.toString()));
                resultListener.onResult(response);
            }
        };
    }

    private Response.ErrorListener createErrorListener(final SearchResultListener resultListener) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorListener", error.toString());
                Log.d(TAG, "That didn't work!");
                resultListener.onError(error);
            }
        };
    }
}
