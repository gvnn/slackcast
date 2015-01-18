package it.gvnn.slackcast.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "podcasts")
public class Podcast extends Model {

    @Column(name = "title")
    public String title;

    public Podcast() {
    }

    public Podcast(it.gvnn.slackcast.search.Podcast podcast) {
        this.title = podcast.getTitle();
    }

    public List<Episode> episodes() {
        return getMany(Episode.class, "podcast");
    }
}
