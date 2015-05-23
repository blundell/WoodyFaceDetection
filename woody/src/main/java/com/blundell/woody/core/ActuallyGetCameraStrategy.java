package com.blundell.woody.core;

import com.blundell.woody.Woody;

class ActuallyGetCameraStrategy implements CameraStrategy {

    @Override
    public void loadCamera(LoadFrontCameraAsyncTask.Listener listener) {
        FrontCameraTaskFactory taskFactory = new FrontCameraTaskFactory(Woody.context);
        LoadFrontCameraAsyncTask frontCameraAsyncTask = taskFactory.getLoadFrontCameraAsyncTask(listener);
        frontCameraAsyncTask.load();
    }

    @Override
    public void disposeCamera(FaceDetectionCamera camera) {
        camera.recycle();
    }
}
