package com.blundell.woody;

import android.hardware.Camera;

public class OneShotFaceDetectionListener implements Camera.FaceDetectionListener {

    private static final int UPDATE_SPEED = 100;
    private static final int UPDATE_SPEED_UNITS = 1000;

    private final Listener listener;

    private boolean timerComplete = true;

    OneShotFaceDetectionListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        if (faces.length == 0) {
            return;
        }

        tickFaceDetectionSession();
        if (sameFaceDetectionSession()) {
            return;
        }
        startFaceDetectionSession();
        listener.onFaceDetected();
    }

    private RestartingCountDownTimer tickFaceDetectionSession() {
        return timer.startOrRestart();
    }

    private boolean sameFaceDetectionSession() {
        return !timerComplete;
    }

    private void startFaceDetectionSession() {
        timerComplete = false;
    }

    private RestartingCountDownTimer timer = new RestartingCountDownTimer(UPDATE_SPEED, UPDATE_SPEED_UNITS) {
        @Override
        public void onFinish() {
            completeFaceDetectionSession();
            listener.onFaceTimedOut();
        }
    };

    private void completeFaceDetectionSession() {
        timerComplete = true;
    }

    interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();
    }
}
