package com.blundell.woody;

import android.app.Activity;

import com.blundell.woody.core.FaceDetectionCamera;
import com.blundell.woody.core.FrontCameraRetriever;
import com.blundell.woody.core.FrontCameraTaskFactory;
import com.blundell.woody.core.LoadFrontCameraAsyncTask;

public class Woody {

    /**
     * For use with Fragments
     * Use to load the front facing camera, once you receive the camera you can choose how to initialise it
     * You also need to manage recycling the camera object when using this from a fragment
     *
     * @param listener the listener to receive the camera
     */
    public static void onAttachLoad(FragmentListener listener) {
        FrontCameraTaskFactory taskFactory = new FrontCameraTaskFactory();
        LoadFrontCameraAsyncTask frontCameraAsyncTask = taskFactory.getLoadFrontCameraAsyncTask(listener);
        frontCameraAsyncTask.load();
    }

    /**
     * For use with Activities
     * Use to load the front facing camera, once you receive the camera you can choose how to initialise it (preview etc)
     *
     * @param activity the activity to load the camera into , <b>Must implement {@link ActivityListener}</b>
     */
    public static void onCreateLoad(Activity activity) {
        if (!(activity instanceof ActivityListener)) {
            throw new IllegalStateException("Your " + activity.getClass().getSimpleName() + " must implement " + ActivityListener.class.getSimpleName());
        }
        FrontCameraRetriever.retrieveFor(activity);
    }

    /**
     * For use with Activities
     * Use to listen for face detection events, you cannot customise the camera initialisation, (no preview)
     *
     * @param activity the activity to receive face detection events   <b>Must implement {@link ActivityMonitorListener}</b>
     */
    public static void onCreateMonitor(Activity activity) {
        if (!(activity instanceof ActivityMonitorListener)) {
            throw new IllegalStateException("Your " + activity.getClass().getSimpleName() + " must implement " + ActivityMonitorListener.class.getSimpleName());
        }
        EasyFaceDetection.monitor(activity);
    }

    public interface FragmentListener extends LoadFrontCameraAsyncTask.Listener, FaceDetectionCamera.Listener {
        // Marker interfaces to make implementation hidden and use easier
    }

    public interface ActivityListener extends FrontCameraRetriever.Listener, FaceDetectionCamera.Listener {
        // Marker interfaces to make implementation hidden and use easier
    }

    public interface ActivityMonitorListener extends EasyFaceDetection.Listener {
        // Marker interfaces to make implementation hidden and use easier
    }

}
