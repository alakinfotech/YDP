package com.example.ydp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class YDPCAREPLAN extends Activity{
	EditText username;
	EditText password;
	public String uname,pword;
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ydpcareplan);
		 username = (EditText) findViewById(R.id.username);
		 password =(EditText) findViewById(R.id.password);
		
		
		Button login =(Button) findViewById(R.id.login);
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

}

