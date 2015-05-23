package com.blundell.woody.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class LoadLollipopFrontCameraTask implements LoadFrontCameraTask {

    private final Context context;

    LoadLollipopFrontCameraTask(Context context) {
        this.context = context;
    }

    @Override
    public FaceDetectionCamera getFaceDetectionCamera() {
        CameraManager service = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = getFaceDetectionCamera(service);

            final CameraDevice[] cameraDeviceHolder = new CameraDevice[1];
            final CountDownLatch latch = new CountDownLatch(1);
            service.openCamera(
                    cameraId, new CameraDevice.StateCallback() {
                        @Override
                        public void onOpened(CameraDevice camera) {
                            cameraDeviceHolder[0] = camera;
                            latch.countDown();
                        }

                        @Override
                        public void onDisconnected(CameraDevice camera) {
                            Log.e("XXX", "Disconnected.");
                            camera.close();
                        }

                        @Override
                        public void onError(CameraDevice camera, int error) {
                            Log.e("XXX", error + " service error.");
                            camera.close();
                        }
                    }, new Handler(Looper.getMainLooper()));
            latch.await(TimeUnit.SECONDS.toMillis(5), TimeUnit.SECONDS);
            CameraDevice cameraDevice = cameraDeviceHolder[0];

            return new LollipopFaceDetectionCamera(cameraDevice);
        } catch (CameraAccessException e) {
            Log.e("XXX", "Likely hardware / non released camera / other app fail.", e);
            return null;
        } catch (IllegalArgumentException e) {
            Log.e("XXX", "Cannot find an available camera.", e);
            return null;
        } catch (SecurityException e) {
            Log.e("XXX", "You do not have permission to open the camera.", e);
            return null;
        } catch (InterruptedException e) {
            Log.e("XXX", "Timed out getting camera.", e);
            return null;
        } catch (NullPointerException e) {
            Log.e("XXX", "// Currently a NPE is thrown when the Camera2API is used but not supported on the device this code runs.", e);
            return null;
        }
    }

    private String getFaceDetectionCamera(CameraManager service) throws CameraAccessException {
        String[] cameraIds = service.getCameraIdList();
        for (String cameraId : cameraIds) {
            CameraCharacteristics characteristics = service.getCameraCharacteristics(cameraId);
            Integer direction = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (CameraCharacteristics.LENS_FACING_FRONT == direction) {
                return cameraId;
            }
        }
        return null;
    }
}
