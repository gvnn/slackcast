package it.gvnn.slackcast.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.gvnn.slackcast.PodcastSearchResponse;
import it.gvnn.slackcast.R;

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
                .inflate(R.layout.search_result_card, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setText(mDataset.get(position).getTitle());
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

        public ViewHolder(View v) {
            super(v);
            mFeedText = (TextView) v.findViewById(R.id.info_text);
        }

        private void setText(String text) {
            mFeedText.setText(text);
        }
    }
}