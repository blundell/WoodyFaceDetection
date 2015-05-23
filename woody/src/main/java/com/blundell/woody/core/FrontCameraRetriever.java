package com.blundell.woody.core;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * I manage loading and destroying the camera reference for you
 */
public class FrontCameraRetriever implements UniqueActivityLifecycleCallbacks.LifeCycleCallbacks, LoadFrontCameraAsyncTask.Listener {

    private static final StackCameraRetrievers RETRIEVERS = new StackCameraRetrievers();

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
        RETRIEVERS.push(frontCameraRetriever);
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
        RETRIEVERS.pop();
    }

    public interface Listener extends LoadFrontCameraAsyncTask.Listener {

    }

    static class StackCameraRetrievers {

        private final List<FrontCameraRetriever> retrievers = new ArrayList<>();

        public void push(FrontCameraRetriever frontCameraRetriever) {
            enable(frontCameraRetriever);
            if (!retrievers.isEmpty()) {
                FrontCameraRetriever latestRetriever = getHead();
                if (latestRetriever != null) {
                    disable(latestRetriever);
                }
            }
            retrievers.add(frontCameraRetriever);
        }

        public void pop() {
            removeHead();
            if (!retrievers.isEmpty()) {
                FrontCameraRetriever latestRetriever = getHead();
                if (latestRetriever != null) {
                    enable(latestRetriever);
                }
            }
        }

        private FrontCameraRetriever removeHead() {
            return retrievers.remove(getHeadPosition());
        }

        private FrontCameraRetriever getHead() {
            return retrievers.get(getHeadPosition());
        }

        private int getHeadPosition() {
            return retrievers.size() - 1;
        }

        private void enable(FrontCameraRetriever latestRetriever) {
            latestRetriever.cameraStrategy = new ActuallyGetCameraStrategy();
        }

        private void disable(FrontCameraRetriever latestRetriever) {
            latestRetriever.cameraStrategy = new DontGetCameraStrategy();
        }

    }
}
