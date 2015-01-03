package it.gvnn.slackcast;

import com.activeandroid.ActiveAndroid;

public class SlackCastApplication extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}

