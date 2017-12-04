package com.example.arijit.github_mobile;

import android.app.Application;
import android.content.Context;

/**
 * Created by arijit on 03/12/17.
 */

public class GitApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        context = this;
//        initImageLoader();
    }

    public static Context getContext() {
        return context;
    }

}
