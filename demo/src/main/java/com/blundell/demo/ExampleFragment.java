package com.blundell.demo;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

import com.blundell.woody.FaceDetectionCamera;
import com.blundell.woody.Woody;

public class ExampleFragment extends Fragment implements Woody.FragmentListener {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Woody.onAttachLoad(this);
    }

    @Override
    public void onLoaded(FaceDetectionCamera camera) {
//        camera.initialise(this);
        camera.initialiseWithDebugPreview(getActivity(), this);
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
