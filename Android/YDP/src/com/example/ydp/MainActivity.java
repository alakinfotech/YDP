package com.example.ydp;



import android.os.Bundle;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		  
		
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
		           Intent in = new Intent(MainActivity.this,HOMESCREEN.class);
		            startActivity(in);
		          // Intent gotohomescreen =  new Intent().setClass(this,HOMESCREEN.class);
		         //  Intent gotohomescreen = new Intent("android.intent.action.HOMESCREEN");
		            //  startActivity(gotohomescreen);
		          }
		         }
		        };
		        splashThread.start();
		    }    
		}




	