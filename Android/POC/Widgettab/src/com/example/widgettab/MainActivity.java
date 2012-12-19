package com.example.widgettab;

import android.os.Bundle;





import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;

import android.widget.TabHost;


public class MainActivity extends TabActivity {
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_main);

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab       
        

        // Initialize a TabSpec for each tab and add it to the TabHost
        //for Tab1        
        spec = tabHost.newTabSpec("Tab1").setIndicator("Contacts")
                          .setContent(new Intent(this,Firstscreen.class));
        tabHost.addTab(spec);

        // for Tab2
        spec = tabHost.newTabSpec("Tab2").setIndicator("Gallery view")
                          .setContent(new Intent(this,SecondScreen.class));
        tabHost.addTab(spec);

        // for Tab3       
        spec = tabHost.newTabSpec("Tab3").setIndicator("Notes")
                          .setContent(new Intent(this,ThirdScreen.class));
        tabHost.addTab(spec);

         

    }
}