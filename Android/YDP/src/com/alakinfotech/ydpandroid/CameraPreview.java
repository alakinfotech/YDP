/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.ydpandroid;

import java.io.IOException;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 08 Dec 2012
 *
 */


/** A basic Camera preview class */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;//instance to create surface
    private Camera mCamera;//instance for camera
    private PreviewCallback previewCallback;//instance to preview camera
    private AutoFocusCallback autoFocusCallback;//Instance to call back camera

	 //-----------------------------------------------------------------------
	 // Function:      CameraPreview(Context context, Camera camera, PreviewCallback previewCb, AutoFocusCallback autoFocusCb)
	 //
	 // Parameter:
	 //      In:        Context context
	 //                 Camera camera
     //					PreviewCallback previewCb
     //                 AutoFocusCallback autoFocusCb
	 //      Out:       none
	 //      In/Out:    none
	 //
	 // Returns:       none
	 //
	 // Desc:          Constructor to stores camera settings
	 //-----------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera,
                         PreviewCallback previewCb,
                         AutoFocusCallback autoFocusCb) {
        super(context);
        mCamera = camera;
        previewCallback = previewCb;
        autoFocusCallback = autoFocusCb;


        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);

        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
	 //-----------------------------------------------------------------------
	 // Function:      surfaceCreated(SurfaceHolder holder)
	 //
	 // Parameter:
	 //      In:        SurfaceHolder holder
	 //      Out:       none
	 //      In/Out:    none
	 //
	 // Returns:       none
	 //
	 // Desc:          The Surface has been created, now tell the camera where to draw the preview.
	 //-----------------------------------------------------------------------	
    public void surfaceCreated(SurfaceHolder holder) {
        
        try {
        	if(mCamera != null)
        		mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d("DBG", "Error setting camera preview: " + e.getMessage());
        }
    }

    
    
	 //-----------------------------------------------------------------------
	 // Function:     surfaceDestroyed(SurfaceHolder holder)
	 //
	 // Parameter:
	 //      In:    SurfaceHolder holder
	 //      Out:       none
	 //      In/Out:    none
	 //
	 // Returns:       none
	 //
	 // Desc:          Camera preview released in activity
	 //-----------------------------------------------------------------------
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Camera preview released in activity
    }
    
    
    
    
	 //-----------------------------------------------------------------------
	 // Function:     surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	 //
	 // Parameter:
	 //      In:        SurfaceHolder holde
	 //                 int format
    //					int width
    //                	int height
	 //      Out:       none
	 //      In/Out:    none
	 //
	 // Returns:       none
	 //
	 // Desc:          If your preview can change or rotate, take care of those events here. Make sure to stop the preview before resizing or reformatting it.
	 //-----------------------------------------------------------------------
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        try {
            // Hard code camera surface rotation 270 degs to match Activity view 
             mCamera.setDisplayOrientation(90);


            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(previewCallback);
            mCamera.startPreview();
            mCamera.autoFocus(autoFocusCallback);
        } catch (Exception e){
            Log.d("DBG", "Error starting camera preview: " + e.getMessage());
        }
    }
}
