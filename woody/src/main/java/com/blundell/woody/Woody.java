package com.blundell.woody;

import android.app.Activity;

public class Woody {

    /**
     * For use with Fragments
     * <p/>
     * Use to load the front facing camera, once you receive the camera you can choose how to initialise it
     *
     * @param listener the listener to receive the camera
     */
    public static void onAttachLoad(FragmentListener listener) {
        new LoadFrontCameraAsyncTask(listener).load();
    }

    /**
     * For use with Activities
     * <p/>
     * Use to load the front facing camera, once you receive the camera you can choose how to initialise it
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
     * <p/>
     * Use to listen for face detection events, you cannot customise the camera initialisation,
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

    }

    public interface ActivityMonitorListener extends EasyFaceDetection.Listener {

    }

}
