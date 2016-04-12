package com.kar.localoffers;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

/**
 * Created by pvkarthik on 21/6/15.
 */
public class OffersApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {

        super.onCreate();

        context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
