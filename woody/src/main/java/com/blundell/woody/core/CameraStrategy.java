package com.blundell.woody.core;

interface CameraStrategy {
    void loadCamera(LoadFrontCameraAsyncTask.Listener listener);

    void disposeCamera(FaceDetectionCamera camera);
}
