package com.blundell.woody.core;

import android.os.AsyncTask;

public class LoadFrontCameraAsyncTask extends AsyncTask<Void, Void, FaceDetectionCamera> {

    private final Listener listener;
    private LoadPreLollipopFrontCameraTask loadPreLollipopFrontCameraTask;

    public LoadFrontCameraAsyncTask(Listener listener, LoadPreLollipopFrontCameraTask loadPreLollipopFrontCameraTask) {
        this.listener = listener;
        this.loadPreLollipopFrontCameraTask = loadPreLollipopFrontCameraTask;
    }

    public void load() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    protected FaceDetectionCamera doInBackground(Void... params) {
        return loadPreLollipopFrontCameraTask.getFaceDetectionCamera();
    }

    @Override
    protected void onPostExecute(FaceDetectionCamera camera) {
        super.onPostExecute(camera);
        if (camera == null) {
            listener.onFailedToLoadFaceDetectionCamera();
        } else {
            listener.onLoaded(camera);
        }
    }

    public interface Listener {
        void onLoaded(FaceDetectionCamera camera);

        void onFailedToLoadFaceDetectionCamera();
    }

}
