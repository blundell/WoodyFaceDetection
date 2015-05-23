package com.blundell.woody.core;

import android.hardware.Camera;
import android.util.Log;

class LoadPreLollipopFrontCameraTask implements LoadFrontCameraTask {

    @Override
    public FaceDetectionCamera getFaceDetectionCamera() {
        try {
            int id = getFrontFacingCameraId();
            Camera camera = Camera.open(id);

            if (camera.getParameters().getMaxNumDetectedFaces() == 0) {
                Log.e("XXX", "Face detection not supported");
                return null;
            }

            return new PreLollipopFaceDetectionCamera(camera);
        } catch (Exception e) {
            Log.e("XXX", "Likely hardware / non released camera / other app fail", e);
            return null;
        }
    }

    private int getFrontFacingCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int i = 0;
        for (; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                break;
            }
        }
        return i;
    }
}
