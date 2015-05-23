package com.blundell.woody.core;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * I manage loading and destroying the camera reference for you
 */
public class FrontCameraRetriever implements UniqueActivityLifecycleCallbacks.LifeCycleCallbacks, LoadFrontCameraAsyncTask.Listener {

    private static final List<FrontCameraRetriever> RETRIEVERS = new ArrayList<>(); // TODO first class collection

    private final Listener listener;

    private FaceDetectionCamera camera;
    private CameraStrategy cameraStrategy;

    public static void retrieveFor(Activity activity) {
        if (!(activity instanceof Listener)) {
            throw new IllegalStateException("Your activity needs to implement FrontCameraRetriever.Listener");
        }
        Listener listener = (Listener) activity;
        retrieve(activity, listener);
    }

    public static void retrieve(Activity activity, Listener listener) {
        Application application = (Application) activity.getApplicationContext();
        FrontCameraRetriever frontCameraRetriever = new FrontCameraRetriever(listener);
        UniqueActivityLifecycleCallbacks callbacks = UniqueActivityLifecycleCallbacks.newInstance(activity, frontCameraRetriever);
        application.registerActivityLifecycleCallbacks(callbacks);
        frontCameraRetriever.cameraStrategy = new ActuallyGetCameraStrategy();
        if (!RETRIEVERS.isEmpty()) {
            FrontCameraRetriever latestRetriever = RETRIEVERS.get(RETRIEVERS.size() - 1);
            if (latestRetriever != null) {
                latestRetriever.cameraStrategy = new DontGetCameraStrategy();
            }
        }
        RETRIEVERS.add(frontCameraRetriever);
    }

    FrontCameraRetriever(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityResumed() {
        cameraStrategy.loadCamera(this);
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
    public void onActivityPaused() {
        if (camera != null) {
            cameraStrategy.disposeCamera(camera);
        }
    }

    @Override
    public void onActivityDestroyed() {
        RETRIEVERS.remove(RETRIEVERS.size() - 1);
        if (!RETRIEVERS.isEmpty()) {
            FrontCameraRetriever latestRetriever = RETRIEVERS.get(RETRIEVERS.size() - 1);
            if (latestRetriever != null) {
                latestRetriever.cameraStrategy = new ActuallyGetCameraStrategy();
            }
        }
    }

    public interface Listener extends LoadFrontCameraAsyncTask.Listener {

    }

}
