package it.gvnn.slackcast.search;

import com.android.volley.VolleyError;

import it.gvnn.slackcast.PodcastSearchResponse;

public interface SearchResultListener {
    public void onResult(PodcastSearchResponse response);
    public void onError(VolleyError error);
}
