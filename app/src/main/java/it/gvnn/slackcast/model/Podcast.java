package it.gvnn.slackcast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Podcast {
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
