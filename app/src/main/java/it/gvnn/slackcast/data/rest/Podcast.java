package it.gvnn.slackcast.data.rest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import it.gvnn.slackcast.data.rest.Episode;

public class Podcast implements Serializable {
    String website;
    String description;
    String title;
    String url;
    @SerializedName("logo_url")
    String logoUrl;
    List<Episode> episodes;

    public String getTitle() {
        return title;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
