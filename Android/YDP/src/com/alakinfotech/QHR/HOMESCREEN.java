package com.alakinfotech.QHR;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;


public class HOMESCREEN  extends TabActivity{
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);
		Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab 
        
        

         // Initialize a TabSpec for each tab and add it to the TabHost
        //for Tab1        
        spec = tabHost.newTabSpec("Tab1").setIndicator("YDP Care Plan",res.getDrawable(R.drawable.faves))
                          .setContent(new Intent(this,YDPCAREPLAN.class));
      
  
        tabHost.addTab(spec);
        
      

        // for Tab2
        spec = tabHost.newTabSpec("Tab2").setIndicator("Notify YDP",res.getDrawable(R.drawable.all))
                          .setContent(new Intent(this,NOTIFYYDP.class));
        tabHost.addTab(spec);

	}




}
