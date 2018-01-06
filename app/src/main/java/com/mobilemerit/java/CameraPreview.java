package com.mobilemerit.java;

import java.io.IOException;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	private SurfaceHolder mSurfaceHolder;
	private Camera mCamera;
	private Display display;
	private int rotation;
	Parameters parameters;
	public static boolean isPortrait=false;
	
	@SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera) {
		super(context);
		this.mCamera = camera;
		this.parameters = camera.getParameters();
		this.parameters.set("orientation", "portrait");

		this.parameters.setRotation(90);
		this.mSurfaceHolder = this.getHolder();
		this.mSurfaceHolder.addCallback(this);
		this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		this.rotation = display.getRotation();
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		try {
			// mCamera.setDisplayOrientation(90);
			if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
				mCamera.setDisplayOrientation(90);
				//Ubah nilai isPortrait untuk mengaktifkan rotasi gambar di ImageEditActivity.isPortrait = true;
			} else {
				mCamera.setDisplayOrientation(0);
			}
			// setCameraDisplayOrientation();
			mCamera.setPreviewDisplay(surfaceHolder);
			Log.i("rotation", "" + rotation);
			// mCamera.setDisplayOrientation(rotation);
			mCamera.startPreview();
		} catch (IOException e) {
			// dibiarkan kosong untuk saat ini
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		mCamera.stopPreview();
		mCamera.release();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
			int width, int height) {
		// mulai pratinjau dengan setting baru
		try {
			// mCamera.stopPreview();
			// this.setCameraDisplayOrientation();
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
		} catch (Exception e) {
			// sengaja dikosongkan untuk tes
		}
	}

}