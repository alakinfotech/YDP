package com.alakinfotech.qrvalidate;

import java.io.UnsupportedEncodingException;


import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import org.apache.commons.codec.binary.Base64;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;



class MyJavaScriptInterface   
{  
	 Context context;
	 String allergyvar  = "";
	  String userName = "";

	  YDPQRValidate ydpqrvalidate;
 
   
  public void setUserName(String html)  
   {  
  	userName = html; 
  	
   } 
    

  
}

public class YDPQRValidate extends Activity {

	public static final boolean YDP_WEBVIEW_LOGIN =false;
	protected static final byte[] NULL = null;
	private Camera mCamera;
    private CameraPreview mPreview;
    MyJavaScriptInterface javeScritpInterfacee;
    private ProgressDialog progressBar;
    private Handler autoFocusHandler;
    Context context= this;
    ImageScanner scanner;
    int loadRequest = 0;
    String userName,passWord;
    String qhrName,uname;
    WebView webView;
    private boolean barcodeScanned = false;
    private boolean previewing = true;

    TextView scanData;
    Button reSet;
    
    
    
    int classCreateStatus;
    static {
        System.loadLibrary("iconv");
    } 
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void validateUserName(String data){
		Log.d(data, "Scanned data");
	 String[] scan = data.split(":");
	 if(scan.length>=10){  
		 if((scan[0].trim().equalsIgnoreCase("FirstName"))&&(scan[2].trim().equalsIgnoreCase("LastName"))){
			 scanData.setText("VALID CARD!");
		        for(int i=1;(9+i)<scan.length;i++){
		          scan[9] = scan[9]+":"+scan[9+i];
		        }
		           userName=scan[8].trim();
		          passWord=scan[9].trim(); 
		          
		          uname = scan[1].trim()+" "+scan[3].trim();
		          scanData.append("\n"+"Card Belongs to:"+uname);	 
		 }else{
			 Toast.makeText(context, "Invalid card", Toast.LENGTH_LONG).show();
		 }
		 
	 } else{
		 Toast.makeText(getApplicationContext(), "Invalid UserName and Password",Toast.LENGTH_LONG).show();
	 }
		
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        
        
        webView = (WebView)findViewById(R.id.webView1);
        scanData = (TextView)findViewById(R.id.scanData);
        reSet = (Button)findViewById(R.id.scanButton);
        
                reSet.setOnClickListener(new OnClickListener() {
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
	
	 protected void onResume(){
		 	
			super.onResume();
		
		
			if(classCreateStatus == 10)
			{
				autoFocusHandler = new Handler();
				mCamera = getCameraInstance();

				/* Instance barcode scanner */
				scanner = new ImageScanner();
				scanner.setConfig(0, Config.X_DENSITY, 3);
				scanner.setConfig(0, Config.Y_DENSITY, 3);

				mPreview = new CameraPreview(getApplicationContext(), mCamera, previewCb, autoFocusCB);
				FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
				preview.addView(mPreview);
			}
     
	}

	@Override
	
	public void onPause() {
			super.onPause();
			releaseCamera();
			classCreateStatus = 10;
     	FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
     	preview.removeView(mPreview);
   
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
 	String scanText = null;
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
               
                 
                 String scanData = decryptScanData(scanText);
                 if(scanData != null)
                 {
                boolean connectionStatus = isNetworkOnline();	
                if(connectionStatus== true){
                    validateUserName(scanData);
                    callWebView();
                }else{
                	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					alertDialogBuilder.setTitle("Warning");
					alertDialogBuilder.setMessage("You're not connected to the Internet.Please connect and retry");
					alertDialogBuilder.setNegativeButton("ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
                }

                 
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
     
    public void callWebView(){
     	
 	   	webView.getSettings().setJavaScriptEnabled(true); 
 	   	webView.getSettings().setBuiltInZoomControls(true);
		webView.setInitialScale(1);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(false);

		loadRequest=0;
		progressBar = ProgressDialog.show(this, "Card is Validating ", "Processing...");
	       // Restore preferences
		 SharedPreferences settings = getApplicationContext().getSharedPreferences("Websettings", MODE_PRIVATE);
	       int statechange = settings.getInt("Key_state", View.INVISIBLE);
	       webView.setVisibility(statechange);
	       
	       
	       
 	  /* Register a new JavaScript interface called HTMLOUT */  
		javeScritpInterfacee = new MyJavaScriptInterface();

		javeScritpInterfacee.ydpqrvalidate = YDPQRValidate.this;
 	  webView.addJavascriptInterface(javeScritpInterfacee, "HTMLOUT");  
 	    
 	   

	webView.loadUrl("https://yourdoctorprogram.com/qhr/Login.aspx/");
	
	
	/* WebViewClient must be set BEFORE calling loadUrl! */ 
	webView.setWebViewClient(new WebViewClient() {

		    
		   @Override
		   public void onPageFinished(WebView view, String url) {
			   Log.d("page loaded","page not loaded");
			   super.onPageFinished(view, url);
		    	 if(loadRequest == 0){    
		    		 
		    	   Log.d("page loaded","pageloaded");
		    	   


		    	   webView.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').value='"+userName+"';");
		    	   webView.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').value='"+passWord+"';");
		    	   webView.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_bt_Login').click();");

		    	   
		    	   loadRequest++;

		    	   
		    	   
		    	 }
		    	 else if(loadRequest == 1)
		    	   {
					
		    	    loadRequest++;
		    	    webView.loadUrl("javascript:window.HTMLOUT.setUserName(document.getElementById('TitleContent_TitleContent_lblProviderName').childNodes[0].textContent);");
		    	   
		    	    
		    	    try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	    
			          if(javeScritpInterfacee.userName.length()==0){
			        	  

			        	  scanData.append("\n"+"Login Failed");
			        	  scanData.append("\n"+"Please Check login credentials");

			        		 
				        	
			          }
			          else{
			        	 qhrName = javeScritpInterfacee.userName;  
			        	 scanData.append("\n"+"Name on QHR:"+ qhrName);
			        	 if(uname.equalsIgnoreCase(qhrName)){
			        		 scanData.append("\n"+"Names Match"); 
			        	 }else{
			        		 scanData.append("\n"+"Names Mismatch");
			        	 }
			        	 scanData.append("\n"+"SuccessFull Login");
			          }
			          progressBar.dismiss();
		           }

		   }
		   
		});
    }
     
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();  
            return false;
        }
        return status;

        }     
     

 // Mimic continuous auto-focusing
 AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
         public void onAutoFocus(boolean success, Camera camera) {
             autoFocusHandler.postDelayed(doAutoFocus, 1000);
         }
     	};


	
}
