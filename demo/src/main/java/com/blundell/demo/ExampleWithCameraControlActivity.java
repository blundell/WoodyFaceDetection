package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.blundell.woody.FaceDetectionCamera;
import com.blundell.woody.Woody;

public class ExampleWithCameraControlActivity extends Activity implements Woody.ActivityListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Woody.onCreateLoad(this);
    }

    @Override
    public void onLoaded(FaceDetectionCamera camera) {
//        camera.initialise(this);
        camera.initialiseWithDebugPreview(this, this);
    }

    @Override
    public void onFaceDetected() {
        Log.d("XXX", "onFaceDetected");
    }

    @Override
    public void onFaceTimedOut() {
        Log.d("XXX", "onFaceTimedOut");
    }

    @Override
    public void onFailedToLoadFaceDetectionCamera() {
        Log.e("XXX", "onFailedToLoadFaceDetectionCamera");
    }

    @Override
    public void onFaceDetectionNonRecoverableError() {
        Log.e("XXX", "onFaceDetectionNonRecoverableError");
    }
}
