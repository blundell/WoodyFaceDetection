package com.blundell.demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blundell.woody.FaceDetectionCamera;
import com.blundell.woody.Woody;

public class ExampleFragment extends Fragment implements Woody.FragmentListener {

    private FaceDetectionCamera camera;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Woody.onAttachLoad(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }

    @Override
    public void onLoaded(FaceDetectionCamera camera) {
        this.camera = camera;
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

    @Override
    public void onDetach() {
        if (camera != null) {
            camera.recycle();
        }
        super.onDetach();
    }
}
