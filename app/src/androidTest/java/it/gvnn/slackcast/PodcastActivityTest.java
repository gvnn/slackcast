package it.gvnn.slackcast;

import android.content.Intent;
import android.widget.Button;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.InputStream;
import java.util.List;

import it.gvnn.slackcast.entities.Podcast;
import it.gvnn.slackcast.search.PodcastSearchResponse;
import it.gvnn.slackcast.services.SubscriptionService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class PodcastActivityTest {

    private Button subscribeButton;
    private it.gvnn.slackcast.search.Podcast mockPodcast;

    @Before
    public void setUp() throws Exception {

        // setting up a mock podcast object
        InputStream mockStream = RuntimeEnvironment.application.getAssets().open("mock/podcast.json");
        byte[] mockPodcastJson = ByteStreams.toByteArray(mockStream);
        Gson gson = new Gson();
        final PodcastSearchResponse mockResponse = gson.fromJson(new String(mockPodcastJson), PodcastSearchResponse.class);

        mockPodcast = mockResponse.get(0);
        // creating a new intent for the podcast activity
        Intent intent = new Intent();
        intent.setAction("it.gvnn.slackcast.PODCAST_DETAIL");
        intent.putExtra("PODCAST", mockPodcast);

        // Launch the activity and look for the subscribe button
        PodcastActivity podcastActivity = Robolectric.buildActivity(PodcastActivity.class)
                .withIntent(intent)
                .create()
                .get();
        subscribeButton = (Button) podcastActivity.findViewById(R.id.podcast_subscribe_button);
    }

    @Test
    public void shouldCreateANewPodcastSubscription() throws Exception {
        // click on button
        subscribeButton.performClick();
        // check it exists in the database
        SubscriptionService subscriptionService = SubscriptionService.getInstance();
        List<Podcast> podcasts = subscriptionService.getPodcasts();
        assertThat(podcasts).hasOnlyElementsOfType(Podcast.class);
        assertThat(podcasts.get(0).title).isEqualTo(mockPodcast.getTitle());
    }
}
