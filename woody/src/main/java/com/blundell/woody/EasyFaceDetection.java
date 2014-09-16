package com.blundell.woody;

import android.app.Activity;

import com.blundell.woody.core.FaceDetectionCamera;
import com.blundell.woody.core.FrontCameraRetriever;

/**
 * I control everything about face detection for you and you just get callbacks when found
 */
public class EasyFaceDetection {

    public static void monitor(final Activity activity) {
        if (!(activity instanceof Listener)) {
            throw new IllegalStateException("Your activity needs to implement EasyFaceDetection.Listener");
        }
        final Listener listener = (Listener) activity;
        FrontCameraRetriever.retrieve(activity, new FrontCameraRetriever.Listener() {
            @Override
            public void onLoaded(FaceDetectionCamera camera) {
                camera.initialise(listener);
//                camera.initialiseWithDebugPreview(activity, listener);
            }

            @Override
            public void onFailedToLoadFaceDetectionCamera() {
                listener.onFaceDetectionNonRecoverableError();
            }
        });
    }

    public interface Listener extends FaceDetectionCamera.Listener {

    }
}
