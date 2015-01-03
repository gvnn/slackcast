package it.gvnn.slackcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;

import it.gvnn.slackcast.model.Podcast;
import it.gvnn.slackcast.search.PodcastDataResponse;
import it.gvnn.slackcast.search.PodcastEpisodesAdapter;
import it.gvnn.slackcast.search.PodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClientFactory;
import it.gvnn.slackcast.search.Services;
import it.gvnn.slackcast.utils.VolleyResultListener;


public class PodcastActivity extends ActionBarActivity {
    private static final String TAG = "PodcastActivity";
    private Toolbar mToolbar;
    private Podcast mPodcast;
    private PodcastSearchClient mPodcastSearchClient;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private PodcastEpisodesAdapter mPodcastEpisodesAdapter;

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

        mRecyclerView = (RecyclerView) findViewById(R.id.eposides_recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplication());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPodcastEpisodesAdapter = new PodcastEpisodesAdapter();
        mRecyclerView.setAdapter(mPodcastEpisodesAdapter);

        TextView podcastTitleView = (TextView) findViewById(R.id.podcast_detail_title);
        podcastTitleView.setText(mPodcast.getTitle());

        TextView podcastDescriptionView = (TextView) findViewById(R.id.podcast_detail_description);
        podcastDescriptionView.setText(mPodcast.getDescription());

        // load podcast info
        loadPodcastInfo();
    }

    private void loadPodcastInfo() {
        Log.i(TAG, String.format("Loading details of %s", mPodcast.getUrl()));

        PodcastSearchClientFactory mPodcastSearchClientFactory = PodcastSearchClientFactory.getInstance(getCacheDir());
        mPodcastSearchClient = mPodcastSearchClientFactory.getSearchClient(Services.GPODDER);
        mPodcastSearchClient.getPodcast(mPodcast.getUrl(), new VolleyResultListener<PodcastDataResponse>() {
            @Override
            public void onResult(PodcastDataResponse response) {
                updateUI(response);
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

    }

    private void updateUI(PodcastDataResponse response) {
        mPodcastEpisodesAdapter.updateDataset(response.get(0).getEpisodes());
        mPodcastEpisodesAdapter.notifyDataSetChanged();
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
