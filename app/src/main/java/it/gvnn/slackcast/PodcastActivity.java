package it.gvnn.slackcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import it.gvnn.slackcast.model.Podcast;


public class PodcastActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private Podcast mPodcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        InitializeUI();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        mPodcast = (Podcast) intent.getSerializableExtra("PODCAST");
    }

    public void InitializeUI() {
        setContentView(R.layout.activity_podcast);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setTitle(mPodcast.getTitle());
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_ic_close_white);

        TextView podcastTitleView = (TextView) findViewById(R.id.podcast_detail_title);
        podcastTitleView.setText(mPodcast.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
