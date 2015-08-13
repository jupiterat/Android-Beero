package com.au.beero.beero;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.au.beero.beero.manager.BeeroLocationManager;
import com.au.beero.beero.manager.BeeroSearchManager;
import com.au.beero.beero.utility.PlistParsing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shintabmt@gmai.com on 8/12/2015.
 */
public class BeeroApplication extends Application {


    public static final int APPLICATION_START = 0;
    public static final int APPLICATION_FOREGROUND = 1;
    public static final int APPLICATION_BACKGROUND = 2;
    public static final int APPLICATION_STOP = 3;
    private static int appStatus = -1;
    private static Application sInstance;

    private class Status {
        public boolean isVisible = true;
        public boolean isFocused = true;
    }

    private Map<Activity, Status> activities;

    @Override
    public void onCreate() {
        activities = new HashMap<Activity, Status>();
        super.onCreate();
        sInstance = this;
        BeeroLocationManager.initialize(this.getApplicationContext());
        BeeroSearchManager.initialize(this.getApplicationContext());
//        PlistParsing parsing = new PlistParsing(this.getApplicationContext());
//        parsing.getSupportArea();
    }

    private boolean hasVisibleActivity() {
        for (Status status : activities.values())
            if (status.isVisible)
                return true;
        return false;
    }

    private boolean hasFocusedActivity() {
        for (Status status : activities.values())
            if (status.isFocused)
                return true;
        return false;
    }

    public void onActivityCreate(Activity activity, boolean isStarting) {
        if (isStarting && activities.isEmpty())
            onApplicationStart();
        activities.put(activity, new Status());
    }

    public void onActivityStart(Activity activity) {
        if (!hasVisibleActivity() && !hasFocusedActivity())
            onApplicationForeground();
        activities.get(activity).isVisible = true;
    }

    public void onActivityWindowFocusChanged(Activity activity, boolean hasFocus) {
        activities.get(activity).isFocused = hasFocus;
    }

    public void onActivityStop(Activity activity, boolean isFinishing) {
        activities.get(activity).isVisible = false;
        if (!isFinishing && !hasVisibleActivity() && !hasFocusedActivity())
            onApplicationBackground();
    }

    public void onActivityDestroy(Activity activity, boolean isFinishing) {
        activities.remove(activity);
        if (isFinishing && activities.isEmpty())
            onApplicationStop();
    }

    private void onApplicationStart() {
        Log.i(null, "Start");
        appStatus = APPLICATION_START;
    }

    private void onApplicationBackground() {
        Log.i(null, "Background");
        appStatus = APPLICATION_BACKGROUND;
    }

    private void onApplicationForeground() {
        Log.i(null, "Foreground");
        appStatus = APPLICATION_FOREGROUND;
    }

    private void onApplicationStop() {
        Log.i(null, "Stop");
        appStatus = APPLICATION_STOP;
    }

    public static int getApplicationStatus() {
        return appStatus;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

}
