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
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    private ProgressDialog progressBar;
	public static final boolean YDP_WEBVIEW_LOGIN =false;
	protected static final byte[] NULL = null;
    private boolean barcodeScanned ;
    private boolean previewing = true;

    String scanText;
    String userName,passWord;
    Button scanButton;
    TextView scanData;
    int classCreateStatus;

    ImageScanner scanner;


    static {
        System.loadLibrary("iconv");
    } 
    
    
    
    private void validationChecking(String data){
		 Log.d(data, "Scanned data");
		 String[] scan = data.split(":");
		 if(scan.length>=10){ 
			 scanData.setText("VALID CARD!");
			 for(int i=1;(9+i)<scan.length;i++){
				 scan[9] = scan[9]+":"+scan[9+i];
			 }
			    userName=scan[8].trim();
				 passWord=scan[9].trim(); 
			    scanData.append("\n"+"Card Belongs to:"+scan[8].trim());
				  		 
		 }
		 else{
			 Toast.makeText(getApplicationContext(), "Invalid Card",Toast.LENGTH_LONG).show();
		 }
		
	}
    
    private void callWebView(){
    	
		Intent innt =new Intent(getApplicationContext(),YDPWEBVIEW.class );
		   innt.putExtra("username",userName);
		   innt.putExtra("password",passWord);
		   Log.d("login data", userName);
	    	onPause();
			
	    	FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
	    	preview.removeView(mPreview);
	    	startActivity(innt);
		
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
                    if(decryptData != null)
                    {
                    	validationChecking(decryptData);
                    	callWebView();

                    }
                    else{
                    	   if (barcodeScanned) {
                               barcodeScanned = false;
                               mCamera.setPreviewCallback(previewCb);
                               mCamera.startPreview();
                               previewing = true;
                               mCamera.autoFocus(autoFocusCB);
                           }
                    }
                     
               
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





