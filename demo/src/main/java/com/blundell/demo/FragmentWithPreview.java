package com.blundell.demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blundell.woody.Woody;
import com.blundell.woody.core.FaceDetectionCamera;

public class FragmentWithPreview extends Fragment implements Woody.FragmentListener {

    private FaceDetectionCamera camera;
    private Toast toast;

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
        camera.initialiseWithDebugPreview(this, getActivity());
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
        toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
        return msg;
    }

    @Override
    public void onDetach() {
        if (camera != null) {
            camera.recycle();
        }
        super.onDetach();
    }
}
