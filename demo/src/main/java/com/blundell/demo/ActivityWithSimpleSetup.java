package com.blundell.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blundell.woody.Woody;

public class ActivityWithSimpleSetup extends Activity implements Woody.ActivityMonitorListener {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Woody.onCreateMonitor(this);
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
