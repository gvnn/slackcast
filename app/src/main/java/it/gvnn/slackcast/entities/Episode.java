package it.gvnn.slackcast.entities;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "episodes")
public class Episode extends Model {
    @Column(name = "title")
    public String title;
    @Column(name = "podcast")
    public Podcast podcast;

    public Episode() {
    }
}
