package it.gvnn.slackcast;

import com.google.gson.annotations.SerializedName;

public class Podcast {
    String website;
    String description;
    String title;
    String url;
    @SerializedName("logo_url")
    String logoUrl;

    public String getTitle() {
        return title;
    }
}
