package com.blundell.woody;

import android.app.Activity;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import java.io.IOException;

public class FaceDetectionCamera implements OneShotFaceDetectionListener.Listener {

    private static final int PORTRAIT = 90;
    private static final int PREVIEW_WIDTH = 200;
    private static final int PREVIEW_HEIGHT = 200;

    private final Camera camera;

    private Listener listener;

    public FaceDetectionCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Use this to detect faces without an on screen preview
     *
     * @param listener the {@link com.blundell.woody.FaceDetectionCamera.Listener} for when faces are detected
     */
    public void initialise(Listener listener) {
        initialise(new DummySurfaceHolder(), listener);
    }

    /**
     * Use this to detect faces but also show a debug camera preview for testing
     *
     * @param activity the activity which will have the preview overlaid
     * @param listener the {@link com.blundell.woody.FaceDetectionCamera.Listener} for when faces are detected
     */
    public void
    initialiseWithDebugPreview(Activity activity, Listener listener) {
        DebugCameraPreview debugCameraPreview = new DebugCameraPreview(activity, this, listener);
        debugCameraPreview.setLayoutParams(new FrameLayout.LayoutParams(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        camera.setDisplayOrientation(PORTRAIT);
        ((FrameLayout) activity.findViewById(android.R.id.content)).addView(debugCameraPreview);
    }

    /**
     * Use this to detect faces when you have a custom surface to display upon
     *
     * @param holder   the {@link android.view.SurfaceHolder} to display upon
     * @param listener the {@link com.blundell.woody.FaceDetectionCamera.Listener} for when faces are detected
     */
    public void initialise(SurfaceHolder holder, Listener listener) {
        this.listener = listener;
        try {
            camera.stopPreview();
        } catch (Exception swallow) {
            // ignore: tried to stop a non-existent preview
        }
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            camera.setFaceDetectionListener(new OneShotFaceDetectionListener(this));
            camera.startFaceDetection();
        } catch (IOException e) {
            this.listener.onFaceDetectionNonRecoverableError();
        }
    }

    @Override
    public void onFaceDetected() {
        listener.onFaceDetected();
    }

    @Override
    public void onFaceTimedOut() {
        listener.onFaceTimedOut();
    }

    public void recycle() {
        if (camera != null) {
            camera.release();
        }
    }

    public interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();

        void onFaceDetectionNonRecoverableError();

    }
}
