package com.blundell.woody.core;

import android.content.Context;
import android.os.Build;

public class FrontCameraTaskFactory {

    private final Context context;

    public FrontCameraTaskFactory(Context context) {
        this.context = context;
    }

    public LoadFrontCameraAsyncTask getLoadFrontCameraAsyncTask(LoadFrontCameraAsyncTask.Listener listener) {
        LoadFrontCameraTask loadFrontCameraTask;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadFrontCameraTask = new LoadLollipopFrontCameraTask(context);
        } else {
            loadFrontCameraTask = new LoadPreLollipopFrontCameraTask();
        }
        return new LoadFrontCameraAsyncTask(listener, loadFrontCameraTask);
    }

}
