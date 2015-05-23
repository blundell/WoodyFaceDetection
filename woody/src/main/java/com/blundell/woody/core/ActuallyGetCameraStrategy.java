package com.blundell.woody.core;

class ActuallyGetCameraStrategy implements CameraStrategy {

    @Override
    public void loadCamera(LoadFrontCameraAsyncTask.Listener listener) {
        FrontCameraTaskFactory taskFactory = new FrontCameraTaskFactory();
        LoadFrontCameraAsyncTask frontCameraAsyncTask = taskFactory.getLoadFrontCameraAsyncTask(listener);
        frontCameraAsyncTask.load();
    }

    @Override
    public void disposeCamera(FaceDetectionCamera camera) {
        camera.recycle();
    }
}
