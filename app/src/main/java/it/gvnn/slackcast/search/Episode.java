package it.gvnn.slackcast.search;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Episode implements Serializable {
    String title;
    String url;
    @SerializedName("podcast_title")
    String podcastTitle;
    @SerializedName("podcast_url")
    String podcastUrl;
    String description;
    String website;
    int released;
    DateTime releaseDate;

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getPodcastTitle() {
        return podcastTitle;
    }

    public String getPodcastUrl() {
        return podcastUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public int getReleased() {
        return released;
    }

    public DateTime getReleaseDate() {
        return releaseDate == null ? releaseDate = new DateTime(released) : releaseDate;
    }
}
