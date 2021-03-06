/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.ydpandroid;

import com.alakinfotech.ydpandroid.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 08 Dec 2012
 *
 */


public class NOTIFYYDP  extends Activity{
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notifyydp);
		Button call = (Button) findViewById(R.id.callydp);
		Button email = (Button) findViewById(R.id.emailydp);
		Button about = (Button) findViewById(R.id.moreaboutydp);
		
		call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 try {
				        Intent callIntent = new Intent(Intent.ACTION_CALL);
				        callIntent.setData(Uri.parse("tel:7139816125"));
				        startActivity(callIntent);
				    } catch (ActivityNotFoundException activityException) {
				         
				Log.e("helloandroid dialing example", "Call failed", activityException);
				
			}
			}
		});
		about.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
              Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yourdoctorprogram.com/"));
			startActivity(browserIntent);
				
				
				
			}
		});
		email.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//send-email-in-your-android-application/
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:info@yourdoctorprogram.com"));
				intent.putExtra("subject", "my subject");
				intent.putExtra("body", "my message");
				startActivity(intent);
				
			}
		});
	}

}

