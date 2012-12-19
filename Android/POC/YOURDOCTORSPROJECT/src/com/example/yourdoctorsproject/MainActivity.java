package com.example.yourdoctorsproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

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
              Intent intt = new Intent(getApplicationContext(), Home.class);
            
                 startActivity(intt);
             }
            }
           };
           splashThread.start();
       }    

  
    
}
