package com.blundell.woody.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Locale;

public class UniqueActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    private final String activityName;
    private final LifeCycleCallbacks lifecycleCallbacks;

    public static UniqueActivityLifecycleCallbacks newInstance(Activity activity, LifeCycleCallbacks lifecycleCallbacks) {
        return new UniqueActivityLifecycleCallbacks(getActivityName(activity), lifecycleCallbacks);
    }

    UniqueActivityLifecycleCallbacks(String activityName, LifeCycleCallbacks lifecycleCallbacks) {
        this.activityName = activityName;
        this.lifecycleCallbacks = lifecycleCallbacks;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // not used
    }

    @Override
    public void onActivityStarted(Activity activity) {
        // not used
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (theseAreTheDroidsWeAreLookingFor(activity)) {
            lifecycleCallbacks.onActivityResumed();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (theseAreTheDroidsWeAreLookingFor(activity)) {
            lifecycleCallbacks.onActivityPaused();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        // not used
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        // not used
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (theseAreTheDroidsWeAreLookingFor(activity)) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
            lifecycleCallbacks.onActivityDestroyed();
        }
    }

    private boolean theseAreTheDroidsWeAreLookingFor(Activity activity) {
        String a = activityName.toLowerCase(Locale.UK);
        String b = getActivityName(activity).toLowerCase(Locale.UK);
        return a.equals(b);
    }

    private static String getActivityName(Activity activity) {
        return activity.getClass().getSimpleName();
    }

    interface LifeCycleCallbacks {
        void onActivityResumed();

        void onActivityPaused();

        void onActivityDestroyed();
    }
}
