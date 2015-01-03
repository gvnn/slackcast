package it.gvnn.slackcast.search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.gvnn.slackcast.R;
import it.gvnn.slackcast.data.rest.Podcast;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private PodcastSearchResponse mDataset;

    public SearchResultsAdapter() {
        mDataset = new PodcastSearchResponse();
    }

    public void updateDataset(PodcastSearchResponse results) {
        mDataset = results;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_search_result, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setPodcast(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mFeedText;
        private Podcast mPodcast;

        public ViewHolder(View v) {
            super(v);
            mFeedText = (TextView) v.findViewById(R.id.info_text);
        }

        private void setPodcast(Podcast podcast) {
            mPodcast = podcast;
            mFeedText.setText(mPodcast.getTitle());
            mFeedText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPodcast != null) {
                        Intent intent = new Intent();
                        intent.setAction("it.gvnn.slackcast.PODCAST_DETAIL");
                        intent.putExtra("PODCAST", mPodcast);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}