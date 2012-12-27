package com.example.ydp;


import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class YDPWEBVIEW extends Activity {
	WebView wb;
	//public String  username,password;
	TextView user,pass;
	boolean loadingFinished = true;
	boolean redirect = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.webview);
	wb = (WebView) findViewById(R.id.webView1);
	wb.getSettings().setJavaScriptEnabled(true);
	
	wb.setInitialScale(1);
	wb.getSettings().setLoadWithOverviewMode(true);
	wb.getSettings().setUseWideViewPort(true);
	wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	wb.setScrollbarFadingEnabled(false);
	
	wb.loadUrl("https://yourdoctorprogram.com/qhr/Login.aspx/");
	
	

	
	//user =(TextView) findViewById(R.id.textView1);
	//pass =(TextView) findViewById(R.id.textView2);
	
	 //user.setText("username:"+getIntent().getExtras().getString("username"));
	 //pass.setText("password:"+getIntent().getExtras().getString("password"));
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
		        //SHOW LOADING IF IT ISNT ALREADY VISIBLE  
		    }

		   @Override
		   public void onPageFinished(WebView view, String url) {
			   Log.d("page loaded","page not loaded");
			   if(!redirect){
		          loadingFinished = true;
		          
		        
		       }

		       if(loadingFinished && !redirect){
		         //HIDE LOADING IT HAS FINISHED
		    	   Log.d("page loaded","page");
		    	   wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').setAttribute('value','adikadapa');");
		    	   wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').setAttribute('value','Medico!8');");
		    	   wb.loadUrl("javascript:document.getElementsByName('ctl00$ctl00$ContentPlaceHolder$ContentPlaceHolder1$bt_Login')[0].click();");
		       
		    	  // wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').value=adikadapa;");
		    	   //wb.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').value=Medico!8;");
		       
		       } else{
		          redirect = false; 
		       }

		    }
		});
	
	 
	 
	}

}
