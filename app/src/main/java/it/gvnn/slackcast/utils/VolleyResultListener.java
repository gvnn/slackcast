package it.gvnn.slackcast.utils;

import com.android.volley.VolleyError;

public interface VolleyResultListener<T> {
    public void onResult(T response);

    public void onError(VolleyError error);
}
