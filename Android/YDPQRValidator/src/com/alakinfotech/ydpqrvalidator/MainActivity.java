package com.alakinfotech.ydpqrvalidator;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;



import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;


import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.pm.ActivityInfo;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    String scanText;
    Button scanButton;
    TextView scanData;

    ImageScanner scanner;

    private boolean barcodeScanned ;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    } 
    
    private String decryptScanData(String data){
	   	Cipher cipher = new Cipher("Adi&Revanth");
	   	byte[] encryptinput=null;
	   	try {
		//scanText.trim();
		byte[] dect=data.getBytes("UTF-8");
		encryptinput = Base64.decodeBase64(dect);
	   	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	   	}

		byte[] reseltByts = null;

		try {
		reseltByts = cipher.decrypt(encryptinput);
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		String resultData = null;


		if(reseltByts != null){



			try {
		resultData = new String(reseltByts, "UTF-8");
			} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		}
		if(resultData != null)
		{

		return new String(resultData);
		}
		else 
			{ return null;
			}
	}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);


        scanData = (TextView)findViewById(R.id.scanData);
        scanButton = (Button)findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanData.setText("Please scan the QR code into the box above using camera");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            });


    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    
                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
                    	 scanText = sym.getData();
                         barcodeScanned = true;
                     }
                   
                     
                     String decryptData = decryptScanData(scanText);
                     scanData.setText(decryptData);
                }
            }
        };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };
}
