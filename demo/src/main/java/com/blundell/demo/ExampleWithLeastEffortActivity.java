package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;

import com.blundell.woody.Woody;

public class ExampleWithLeastEffortActivity extends Activity implements Woody.ActivityMonitorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Woody.onCreateMonitor(this);
    }

    @Override
    public void onFaceDetected() {

    }

    @Override
    public void onFaceTimedOut() {

    }

    @Override
    public void onFaceDetectionNonRecoverableError() {

    }

}
