package com.alakinfotech.QHR;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;






public class YDPWEBVIEW extends Activity {
	WebView wb;
	private ProgressDialog progressBar;
	TextView user,pass;
	boolean loadingFinished = true;
	boolean redirect = false;
	static int i = 0;
	
	protected void onPause()
	   {
	       super.onPause();
	       System.gc();
	   }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
			i = 0;
			super.onCreate(savedInstanceState);
			setContentView(R.layout.webview);
			wb = (WebView) findViewById(R.id.webView1);
			wb.getSettings().setJavaScriptEnabled(true);
			wb.getSettings().setBuiltInZoomControls(true);
			wb.setInitialScale(1);
			wb.getSettings().setLoadWithOverviewMode(true);
			wb.getSettings().setUseWideViewPort(true);
			wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			wb.setScrollbarFadingEnabled(false);
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

			progressBar = ProgressDialog.show(YDPWEBVIEW.this, "YDP page is ", "Loading...");
			

			
	wb.loadUrl("https://yourdoctorprogram.com/qhr/Login.aspx/");


	 wb.setWebViewClient(new WebViewClient() {

		   @Override
		   public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
		       if (!loadingFinished) {
		          redirect = true;
		       }

		   loadingFinished = false;
		   wb.loadUrl(urlNewString);
		   return true;
		   }
		   
		   public void onPageStarted(WebView view, String url) {
		        loadingFinished = false;
		        
		    }
		   
		   @Override
		   public void onPageFinished(WebView view, String url) {
			   Log.d("page loaded","page not loaded");
			   if(!redirect)
			   {
				   
	             loadingFinished = true;
	             }

		       if(loadingFinished && !redirect){
		    	   
		    	 if(i == 0){
		    	   progressBar.dismiss();
		    	   Log.d("page loaded","page");
		    	   
		    	   String user = getIntent().getExtras().getString("username");
		    	   String pass = getIntent().getExtras().getString("password");

		    	   wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').value='"+user+"';");
		    	   wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').value='"+pass+"';");
		    	   wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_bt_Login').click();");
		    	
		    	   
		    	   i = 1;
		    	   wb.loadUrl("javascript:window.MyHandler.setmydata(document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView_Label11').innerHTML)");
		    	   if(i==1)
		    	   {
					
		    		   wb.loadUrl("javascript:window.MyHandler.setmydata(function getCarePlan(){var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');var result = 'NO'; if(carePlanTable){ result = ''; var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');if(carePlanTable){ var carePlan = carePlanTable.getElementsByTagName('tr');for(var i = 1; i < carePlan.length; i++){var carePlanRow = carePlan[i].getElementsByTagName('td');for(var j = 0; j < carePlanRow.length; j++){var childValue = carePlanRow[j].childNodes[1].textContent;if(childValue){result += childValue;result += ':$#';console.log(childValue);}else{result += carePlanRow[j].childNodes[1].src;result += ':$#';console.log(carePlanRow[j].childNodes[1].src);}}}}}return result;)())");
		    	  
		    	   }
		    	   
		    	   
		    	 }
		       } else{
		          redirect = false; 
		       }
		       
		    }
		   
		   
		});
	
	 
	 
	}

	 public void javascriptCallFinish(final String mydata){
        Log.v("mylog","MyActivity.javascriptCallFinished is called : " + mydata);
        Toast.makeText(this, "mydata is: " + mydata, 5).show();

        // I need to run set operation of UI on the main thread.
        // therefore, the above parameter "val" must be final
        runOnUiThread(new Runnable() {
            public void run() {
              
            }
        });
    }

}
