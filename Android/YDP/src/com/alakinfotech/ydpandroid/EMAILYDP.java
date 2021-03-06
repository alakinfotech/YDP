/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.ydpandroid;

import com.alakinfotech.ydpandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 08 Dec 2012
 *
 */

public class EMAILYDP extends Activity {
	Button buttonSend;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emailydp);
		buttonSend = (Button) findViewById(R.id.buttonSend);
		textTo = (EditText) findViewById(R.id.editTextTo);
		textSubject = (EditText) findViewById(R.id.editTextSubject);
		textMessage = (EditText) findViewById(R.id.editTextMessage);
		buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  String to = textTo.getText().toString();
				  String subject = textSubject.getText().toString();
				  String message = textMessage.getText().toString();
	 
				  Intent email = new Intent(Intent.ACTION_SEND);
				  email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
				  email.putExtra(Intent.EXTRA_SUBJECT, subject);
				  email.putExtra(Intent.EXTRA_TEXT, message);
	 
				  //need this to prompts email client only
				  email.setType("message/rfc822");
	 
				  startActivity(Intent.createChooser(email, "Choose an Email client :"));
				
			}
		});
	}

}
