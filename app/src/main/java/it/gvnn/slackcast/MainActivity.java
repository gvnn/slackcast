package it.gvnn.slackcast;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.android.volley.RequestQueue;

import it.gvnn.slackcast.search.PodcastSearchClient;
import it.gvnn.slackcast.search.PodcastSearchClientFactory;
import it.gvnn.slackcast.search.Providers;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private RequestQueue volleyQueue;
    private PodcastSearchClient searchClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the search client
        PodcastSearchClientFactory clientInstance = PodcastSearchClientFactory.getInstance(this.getCacheDir());
        searchClient = clientInstance.getSearchClient(Providers.GPODDER);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_action_bar_app_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
