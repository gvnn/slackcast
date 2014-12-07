package it.gvnn.slackcast.search;

import android.net.Uri;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.gvnn.slackcast.utils.GsonRequest;
import it.gvnn.slackcast.utils.VolleyResultListener;

public class GPodderPodcastSearchClient implements PodcastSearchClient {

    private static final String TAG = "GPodderPodcastSearchClient";

    private static final String GPODDER_API_BASE_URL = "https://gpodder.net";
    private static final String GPODDER_PARSE_URL = "http://feeds.gpodder.net/parse";

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
    public void search(String query, VolleyResultListener<PodcastSearchResponse> resultListener) {

        GsonRequest<PodcastSearchResponse> searchRequest =
                new GsonRequest<PodcastSearchResponse>(
                        Uri.parse(GPODDER_API_BASE_URL.concat("/search.json"))
                                .buildUpon()
                                .appendQueryParameter("q", query)
                                .build().toString(),
                        PodcastSearchResponse.class,
                        null,
                        this.createSearchSuccessListener(resultListener),
                        this.createSearchErrorListener(resultListener)
                );

        // Add the request to the RequestQueue.
        volleyQueue.add(searchRequest);
    }

    @Override
    public void getPodcast(String feedUrl, VolleyResultListener<PodcastDataResponse> resultListener) {
        GsonRequest<PodcastDataResponse> feedRequest =
                new GsonRequest<PodcastDataResponse>(
                        Uri.parse(GPODDER_PARSE_URL)
                                .buildUpon()
                                .appendQueryParameter("url", feedUrl)
                                .build().toString(),
                        PodcastDataResponse.class,
                        null,
                        this.createFeedSuccessListener(resultListener),
                        this.createFeedErrorListener(resultListener)
                );
        volleyQueue.add(feedRequest);
    }

    private Response.Listener<PodcastDataResponse> createFeedSuccessListener(final VolleyResultListener<PodcastDataResponse> resultListener) {
        return new Response.Listener<PodcastDataResponse>() {
            @Override
            public void onResponse(PodcastDataResponse response) {
                Log.d(TAG, "PodcastDataListener. Response is: ".concat(response.toString()));
                resultListener.onResult(response);
            }
        };
    }

    private Response.ErrorListener createFeedErrorListener(final VolleyResultListener<PodcastDataResponse> resultListener) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PodcastDataListener. Error: ", error.toString(), error);
                resultListener.onError(error);
            }
        };
    }

    private Response.Listener<PodcastSearchResponse> createSearchSuccessListener(final VolleyResultListener<PodcastSearchResponse> resultListener) {
        return new Response.Listener<PodcastSearchResponse>() {
            @Override
            public void onResponse(PodcastSearchResponse response) {
                Log.d(TAG, "SearchResultListener. Response is: ".concat(response.toString()));
                resultListener.onResult(response);
            }
        };
    }

    private Response.ErrorListener createSearchErrorListener(final VolleyResultListener<PodcastSearchResponse> resultListener) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SearchResultListener. Error: ", error.toString(), error);
                resultListener.onError(error);
            }
        };
    }

    public void setQueue(RequestQueue queue) {
        this.volleyQueue = queue;
    }
}
