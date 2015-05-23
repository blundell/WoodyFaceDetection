package com.blundell.woody.core;

class DontGetCameraStrategy implements CameraStrategy {

    @Override
    public void loadCamera(LoadFrontCameraAsyncTask.Listener listener) {
        // no-op
    }

    @Override
    public void disposeCamera(FaceDetectionCamera camera) {
        // no-op
    }
}
