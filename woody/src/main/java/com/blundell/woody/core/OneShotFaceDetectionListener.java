package com.blundell.woody.core;

import android.hardware.Camera;

class OneShotFaceDetectionListener implements Camera.FaceDetectionListener {

    private final SessionFaceDetectionListener<Camera.Face> sessionFaceDetectionListener;

    OneShotFaceDetectionListener(SessionFaceDetectionListener<Camera.Face> sessionFaceDetectionListener) {
        this.sessionFaceDetectionListener = sessionFaceDetectionListener;
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        sessionFaceDetectionListener.onFaceDetection(faces);
    }
}
