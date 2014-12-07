package it.gvnn.slackcast;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.VolleyError;

import it.gvnn.slackcast.search.PodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClientFactory;
import it.gvnn.slackcast.search.PodcastSearchResponse;
import it.gvnn.slackcast.search.SearchResultsAdapter;
import it.gvnn.slackcast.search.Services;
import it.gvnn.slackcast.utils.VolleyResultListener;


public class SearchActivity extends ActionBarActivity {

    private static final String TAG = "SearchActivity";
    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private SearchResultsAdapter mSearchAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PodcastSearchClientFactory mPodcastSearchClientFactory;
    private PodcastSearchClient mPodcastSearchClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitializeUI();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void doMySearch(String query) {
        Log.d(TAG, query);
        mPodcastSearchClient.search(query, new VolleyResultListener<PodcastSearchResponse>() {
            @Override
            public void onResult(PodcastSearchResponse response) {
                mSearchAdapter.updateDataset(response);
                mSearchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

            PodcastSearchClientFactory mPodcastSearchClientFactory = PodcastSearchClientFactory.getInstance(getCacheDir());
            mPodcastSearchClient = mPodcastSearchClientFactory.getSearchClient(Services.GPODDER);

            String query = intent.getStringExtra(SearchManager.QUERY);
            mToolbar.setTitle(query);
            doMySearch(query);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.menu_item_search || super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        InitializeUI();
    }

    public void InitializeUI() {
        setContentView(R.layout.activity_search);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        mToolbar.setTitle(R.string.title_activity_search);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSearchAdapter = new SearchResultsAdapter();
        mRecyclerView.setAdapter(mSearchAdapter);
    }

}
