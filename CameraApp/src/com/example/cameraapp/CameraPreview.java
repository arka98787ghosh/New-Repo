package com.example.cameraapp;

import java.io.IOException;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	static Camera mCamera;
	static int count = 0;
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, now tell the camera where to draw the
		// preview.
		try {				
			Log.i("count value"," "+ ++count);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			mCamera.setDisplayOrientation(90);
		} catch (IOException e) {
			Log.d("Error in surfaceCreated method: ",
					"Error setting camera preview: " + e.getMessage());
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// empty. Take care of releasing the Camera preview in your activity.
		if (mCamera != null) {
			// Call stopPreview() to stop updating the preview surface.
			//mCamera.stopPreview();

			// Important: Call release() to release the camera for use by other
			// applications. Applications should release the camera immediately
			// during onPause() and re-open() it during onResume()).
			mCamera.release();

			mCamera = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e) {
			Log.d("Error in surfacedChanged method : ",
					"Error starting camera preview: " + e.getMessage());
		}
	}
}