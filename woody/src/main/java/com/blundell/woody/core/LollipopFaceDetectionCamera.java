package com.blundell.woody.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.Face;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import java.util.Arrays;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LollipopFaceDetectionCamera implements FaceDetectionCamera {

    private static final int PREVIEW_WIDTH = 320;
    private static final int PREVIEW_HEIGHT = 240;

    private final CameraDevice camera;

    private Listener listener;

    public LollipopFaceDetectionCamera(CameraDevice camera) {
        this.camera = camera;
    }

    @Override
    public void initialise(Listener listener) {
        initialise(listener, new DummySurfaceHolder());
    }

    @Override
    public void initialiseWithDebugPreview(Listener listener, Activity activity) {
        DebugCameraSurfaceView debugCameraSurfaceView = new DebugCameraSurfaceView(activity, this, listener);
        debugCameraSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(PREVIEW_WIDTH, PREVIEW_HEIGHT));
        ((FrameLayout) activity.findViewById(android.R.id.content)).addView(debugCameraSurfaceView);
    }

    @Override
    public void initialise(final Listener listener, final SurfaceHolder holder) {
        this.listener = listener;
        final SessionFaceDetectionListener<Face> sessionFaceDetectionListener = new SessionFaceDetectionListener<>(this);
        try {
            CaptureRequest.Builder requestBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            requestBuilder.addTarget(holder.getSurface());
            requestBuilder.set(CaptureRequest.STATISTICS_FACE_DETECT_MODE, CaptureRequest.STATISTICS_FACE_DETECT_MODE_SIMPLE);
            final CaptureRequest previewRequest = requestBuilder.build();
            camera.createCaptureSession(
                    Arrays.asList(holder.getSurface()),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {
                            try {
                                session.setRepeatingRequest(
                                        previewRequest,
                                        new CameraCaptureSession.CaptureCallback() {
                                            @Override
                                            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                                                super.onCaptureCompleted(session, request, result);
                                                final Face[] faces = result.get(CaptureResult.STATISTICS_FACES);

                                                sessionFaceDetectionListener.onFaceDetection(faces);
                                            }
                                        },
                                        null);
                            } catch (CameraAccessException e) {
                                Log.e("XXX", "CameraAccessException", e);
                            } catch (IllegalStateException e) {
                                Log.e("XXX", "IllegalStateException", e);
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession session) {
                            Log.e("XXX", "configuration failed." + session);
                        }
                    },
                    null);
        } catch (CameraAccessException e) {
            Log.e("XXX", "CameraAccessException", e);
        } catch (IllegalStateException e) {
            Log.e("XXX", "IllegalStateException", e);
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

    @Override
    public void recycle() {
        camera.close();
    }
}
