package com.blundell.woody;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * I manage loading and destroying the camera reference for you
 */
public class FrontCameraRetriever implements Application.ActivityLifecycleCallbacks, LoadFrontCameraAsyncTask.Listener {

    private final Listener listener;

    private FaceDetectionCamera camera;

    public static void retrieveFor(Activity activity) {
        if (!(activity instanceof Listener)) {
            throw new IllegalStateException("Your activity needs to implement FrontCameraRetriever.Listener");
        }
        Listener listener = (Listener) activity;
        retrieve(activity, listener);
    }

    public static void retrieve(Context context, Listener listener) {
        Application application = (Application) context.getApplicationContext();
        FrontCameraRetriever frontCameraRetriever = new FrontCameraRetriever(listener);
        application.registerActivityLifecycleCallbacks(frontCameraRetriever);
    }

    FrontCameraRetriever(Listener listener) {
        this.listener = listener;
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
        new LoadFrontCameraAsyncTask(this).load();
    }

    @Override
    public void onLoaded(FaceDetectionCamera camera) {
        this.camera = camera;
        listener.onLoaded(camera);
    }

    @Override
    public void onFailedToLoadFaceDetectionCamera() {
        listener.onFailedToLoadFaceDetectionCamera();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (camera != null) {
            camera.recycle();
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
        // not used
    }

    public interface Listener extends LoadFrontCameraAsyncTask.Listener {

    }
}
