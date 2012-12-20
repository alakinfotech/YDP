package com.example.ydp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class Splashscreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);


		Thread splashThread = new Thread(){
		         public void run(){
		          try {
		     sleep(5000);
		    } catch (InterruptedException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		          finally{
		           finish();
		           Intent in = new Intent(Splashscreen.this,HOMESCREEN.class);
		            startActivity(in);
		          
		          }
		         }
		        };
		        splashThread.start();
		    }    
		}



