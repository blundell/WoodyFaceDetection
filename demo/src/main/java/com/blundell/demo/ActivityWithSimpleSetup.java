package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.blundell.woody.Woody;

public class ActivityWithSimpleSetup extends Activity implements Woody.ActivityMonitorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Woody.onCreateMonitor(this);
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
    public void onFaceDetectionNonRecoverableError() {
        Log.e("XXX", "onFaceDetectionNonRecoverableError");
    }

}
