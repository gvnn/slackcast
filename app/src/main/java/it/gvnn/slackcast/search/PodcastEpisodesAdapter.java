package it.gvnn.slackcast.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.gvnn.slackcast.R;
import it.gvnn.slackcast.data.rest.Episode;

public class PodcastEpisodesAdapter extends RecyclerView.Adapter<PodcastEpisodesAdapter.ViewHolder> {

    private List<Episode> mDataset;

    public PodcastEpisodesAdapter() {
        mDataset = new ArrayList<Episode>();
    }

    public void updateDataset(List<Episode> results) {
        mDataset = results;
    }

    @Override
    public PodcastEpisodesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_podcast_episodes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mEpisodeTitle.setText(mDataset.get(i).getTitle());
        viewHolder.mEpisodeDescription.setText(mDataset.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mEpisodeTitle;
        public TextView mEpisodeDescription;

        public ViewHolder(View v) {
            super(v);
            mEpisodeTitle = (TextView) v.findViewById(R.id.podcast_episode_title);
            mEpisodeDescription = (TextView) v.findViewById(R.id.podcast_episode_description);
        }
    }
}
