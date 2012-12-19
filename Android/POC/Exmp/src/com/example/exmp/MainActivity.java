package com.example.exmp;



import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 Resources res = getResources(); // Resource object to get Drawables
	        TabHost tabHost = getTabHost();  // The activity TabHost
	        TabHost.TabSpec spec;  // Resusable TabSpec for each tab 
	        
	        

	        // Initialize a TabSpec for each tab and add it to the TabHost
	        //for Tab1        
	        spec = tabHost.newTabSpec("Tab1").setIndicator("YDP care plan",getResources().getDrawable(R.drawable.all))
	                          .setContent(new Intent(this,Firstscreen.class));
	        tabHost.addTab(spec);

	        // for Tab2
	        spec = tabHost.newTabSpec("Tab2").setIndicator("Notify YDP",getResources().getDrawable(R.drawable.faves))
	                          .setContent(new Intent(this,SecondScreen.class));
	        tabHost.addTab(spec);
	}

	

}
