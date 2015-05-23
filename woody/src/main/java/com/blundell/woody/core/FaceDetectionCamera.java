package com.blundell.woody.core;

import android.app.Activity;
import android.view.SurfaceHolder;

public interface FaceDetectionCamera extends OneShotFaceDetectionListener.Listener {
    void initialise(Listener listener);

    void initialiseWithDebugPreview(Listener listener, Activity activity);

    void initialise(Listener listener, SurfaceHolder holder);

    @Override
    void onFaceDetected();

    @Override
    void onFaceTimedOut();

    void recycle();

    interface Listener {
        void onFaceDetected();

        void onFaceTimedOut();

        void onFaceDetectionNonRecoverableError();

    }
}
