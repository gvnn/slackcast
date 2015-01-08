package it.gvnn.slackcast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.*;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    @Test
    public void shouldHaveApplicationName() throws Exception {
        String appName = new MainActivity().getResources().getString(R.string.app_name);
        assertThat(appName).isEqualTo("slackcast");
    }
}