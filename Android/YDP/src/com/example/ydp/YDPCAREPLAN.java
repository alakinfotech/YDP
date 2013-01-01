package com.example.ydp;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class YDPCAREPLAN extends Activity{
	
	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    String scanText;
    
    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    } 
    
	EditText username;
	EditText password;
	Button login;
	public String uname,pword;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ydpcareplan);
		
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

	        
	        username = (EditText) findViewById(R.id.username);
			 password =(EditText) findViewById(R.id.password);
			 
		login =(Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent innt =new Intent(getApplicationContext(),YDPWEBVIEW.class );
				
				uname = username.getText().toString();
				pword = password.getText().toString();
			   innt.putExtra("username",uname);
			   innt.putExtra("password",pword);
			   
				 startActivity(innt);
			    
				
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
                        scanText = "barcode result " + sym.getData();
                        barcodeScanned = true;
                    }
                    
                    Toast.makeText(YDPCAREPLAN.this, scanText, Toast.LENGTH_SHORT).show();
                   
//                    mCamera.setPreviewCallback(previewCb);
//                    mCamera.startPreview();
//                    previewing = true;
//                    mCamera.autoFocus(autoFocusCB);
                    
                    login.performClick();
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

