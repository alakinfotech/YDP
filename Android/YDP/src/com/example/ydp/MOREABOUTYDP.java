package com.example.ydp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MOREABOUTYDP extends Activity {
	WebView wb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		wb = (WebView) findViewById(R.id.webView1);
		 wb.loadUrl("http://www.yourdoctorprogram.com/");
		
	}

}
