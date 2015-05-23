package com.blundell.woody.core;

public class FrontCameraTaskFactory {

    public LoadFrontCameraAsyncTask getLoadFrontCameraAsyncTask(LoadFrontCameraAsyncTask.Listener listener) {
        return new LoadFrontCameraAsyncTask(listener, new LoadPreLollipopFrontCameraTask());
    }

}
