package com.blundell.woody.core;

class ActuallyGetCameraStrategy implements CameraStrategy {

    @Override
    public void loadCamera(LoadFrontCameraAsyncTask.Listener listener) {
        new LoadFrontCameraAsyncTask(listener, new LoadPreLollipopFrontCameraTask()).load();
    }

    @Override
    public void disposeCamera(FaceDetectionCamera camera) {
        camera.recycle();
    }
}
