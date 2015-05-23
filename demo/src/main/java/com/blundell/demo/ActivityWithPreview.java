package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blundell.woody.Woody;
import com.blundell.woody.core.FaceDetectionCamera;

public class ActivityWithPreview extends Activity implements Woody.ActivityListener {

    private Toast toast;

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
        Log.d("XXX", toast("onFaceDetected"));
    }

    @Override
    public void onFaceTimedOut() {
        Log.d("XXX", toast("onFaceTimedOut"));
    }

    @Override
    public void onFailedToLoadFaceDetectionCamera() {
        Log.e("XXX", toast("onFailedToLoadFaceDetectionCamera"));
    }

    @Override
    public void onFaceDetectionNonRecoverableError() {
        Log.e("XXX", toast("onFaceDetectionNonRecoverableError"));
    }

    private String toast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
        return msg;
    }
}
