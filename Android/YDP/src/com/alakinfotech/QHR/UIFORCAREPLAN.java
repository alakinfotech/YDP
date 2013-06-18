/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.QHR;





import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 08 Dec 2012
 *
 */


 class MyJavaScriptInterface   
{  
	 Context context;
	 String allergyvar  = "";
	 String userName = "";
	 ArrayList<String[]> careplanRecords;
	 ArrayList<String[]> allergyRecords;
	 UIFORCAREPLAN uiforcareplan;
  
    
   public void setUserName(String html)  
   {  
   	userName = html; 
   	
    } 
     
   public void showCareplaneHTML(String html) 
   { 
   	
	   	 String[] careplandata = html.split("[$#]+");
	   	 
	   	 for( int i=0;(i+9)<=careplandata.length;i+=10)    
	   	 {
	   		 String url = careplandata[i+3].toString();
			 String  status = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf('.'));
			 String[] record = {careplandata[i+1],careplandata[i+2],status,careplandata[i+4],careplandata[i+5],careplandata[i+6],careplandata[i+7],careplandata[i+8],careplandata[i+9]};
	   		 careplanRecords.add( record);
	   		 
	   	 }
   	 
   	
    }
    
   public void showAllergiesHTML(String html)  
   {  
     
	   	String[] allergydata = html.split("[$#]+");
	
	
	  	 for( int i=0;i<allergydata.length;i+=4)
	  	 {
	  		 if(i!=0)
	  		 {
	  			 allergyvar += ", "; 
	  		 }
	  		 String[] record = {allergydata[i],allergydata[i+1],allergydata[i+2],allergydata[i+3]};
	  		 allergyvar += allergydata[i];
	  		 allergyRecords.add( record);
	  	    
	  	 }

  	
     }
   
}
 
 
 
public class UIFORCAREPLAN extends Activity implements OnItemClickListener{
	
	 
	MyJavaScriptInterface javeScritpInterfacee;
	Myadapter adapter;
	RelativeLayout rlayout,titlerlayout1;
	final Context context = this;
	public TextView allallergy,titletext,user,pass;
	private ProgressDialog progressBar;
	WebView webview;
	private int loadRequest = 0;
	Button logout;
    ListView careplanlistview;
    ImageView image;
    ImageButton setting;
	boolean setvisible = false;
	  
	class Myadapter extends BaseAdapter 
	{


		 Context context;
		 public Myadapter(Context c) {
		   this.context =c;
	     }
	 	
		public int getCount() {
			return javeScritpInterfacee.careplanRecords.size();
			
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}
		


		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub

			 LayoutInflater li = getLayoutInflater();
			 view = li.inflate(R.layout.careplandataview, null);
			 TextView careplantextview1 = (TextView)view.findViewById(R.id.cpdatatextView1);
			 TextView careplantextview2 = (TextView)view.findViewById(R.id.cpdatatextView2);
			 TextView careplantextview3 = (TextView)view.findViewById(R.id.cpdatatextView3);
			 String[] careplan = javeScritpInterfacee.careplanRecords.get(position);
			
			 if (position % 2 == 0) {
				 
					  view.setBackgroundColor(0x30E6EEF8);
			 }
			 
			 if(careplan[1].equals("undefined")){
				 careplan[1] = " ";
			 }
			 if(careplan[2].equals("undefined")){
				 careplan[2] = " ";
			 }
			 if(careplan[3].equals("undefined")){
				 careplan[3] = " ";
			 }
			 if(careplan[4].equals("undefined")){
				 careplan[4] = " ";
			 }
			 if(careplan[5].equals("undefined")){
				 careplan[5] = " ";
			 }
			 if(careplan[6].equals("undefined")){
				 careplan[6] = " ";
			 }
			 if(careplan[7].equals("undefined")){
				 careplan[7] = " ";
			 }
			 
			 
			 if(careplan[2].equals("Inactive")){
				 careplantextview1.setTextColor(0x30141823);
				 careplantextview2.setTextColor(0x30141823);
				 careplantextview3.setTextColor(0x30141823);
			 }
			
			 
			 careplantextview1.setText(careplan[1]);
			 careplantextview2.setText(careplan[6]);
			 careplantextview3.setText(careplan[7]);
			 return view;
		}
		
		
		

	}
	
	protected void onPause(){
	       super.onPause();
	       System.gc();
	}
	  
	  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		setContentView(R.layout.careplanhome);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		titlerlayout1 =(RelativeLayout) findViewById(R.id.RelativeLayout1);
		titletext = (TextView) findViewById(R.id.titletextview);
		setting = (ImageButton) findViewById(R.id.settingsButton);
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openOptionsMenu();	
			}
		});
		  
		  
		 loadRequest = 0;

 	   	webview = (WebView)findViewById(R.id.webView1);
 	   	webview.getSettings().setJavaScriptEnabled(true); 
 	   	webview.getSettings().setBuiltInZoomControls(true);
		webview.setInitialScale(1);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webview.setScrollbarFadingEnabled(false);

		
		progressBar = ProgressDialog.show(UIFORCAREPLAN.this, "YDP page is ", "Loading...");
	       // Restore preferences
		SharedPreferences settings = getApplicationContext().getSharedPreferences("Websettings", MODE_PRIVATE);
	    int statechange = settings.getInt("Key_state", View.INVISIBLE);
	    webview.setVisibility(statechange);
	       
	       
	       
 	  /* Register a new JavaScript interface called HTMLOUT */  
		javeScritpInterfacee = new MyJavaScriptInterface();
		javeScritpInterfacee.careplanRecords = new ArrayList<String[]>();
	 	javeScritpInterfacee.allergyRecords = new ArrayList<String[]>();  
		javeScritpInterfacee.uiforcareplan = UIFORCAREPLAN.this;
 	    webview.addJavascriptInterface(javeScritpInterfacee, "HTMLOUT");  
 	    webview.loadUrl("https://yourdoctorprogram.com/qhr/Login.aspx/");
	  /* WebViewClient must be set BEFORE calling loadUrl! */ 
	    webview.setWebViewClient(new WebViewClient() {

		    
		   @Override
		   public void onPageFinished(WebView view, String url) {
			   Log.d("page loaded","page not loaded");
			   super.onPageFinished(view, url);
		    	 if(loadRequest == 0){    

			    	   String user = getIntent().getExtras().getString("username");
			    	   String pass = getIntent().getExtras().getString("password");
	
			    	   webview.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').value='"+user+"';");
			    	   webview.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').value='"+pass+"';");
			    	   webview.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_bt_Login').click();");
	
			    	   
			    	   loadRequest++;

		    	   
		    	   
		    	 }else if(loadRequest == 1){
					
			    	    loadRequest++;
			    	    webview.loadUrl("javascript:window.HTMLOUT.setUserName(document.getElementById('TitleContent_TitleContent_lblProviderName').childNodes[0].textContent);");
			    	    webview.loadUrl("javascript:( function () {var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');var result = \"NO\";if(carePlanTable){result = \"\";var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');if(carePlanTable){var carePlan = carePlanTable.getElementsByTagName('tr');for(var i = 1; i < carePlan.length; i++){var carePlanRow = carePlan[i].getElementsByTagName('td');for(var j = 0; j < carePlanRow.length; j++){var childValue = carePlanRow[j].childNodes[1].textContent;if(childValue){result += childValue;result += \"$#\";}else{result += carePlanRow[j].childNodes[1].src;result += \"$#\";}}}}}window.HTMLOUT.showCareplaneHTML( result);}) ()");
			    	    webview.loadUrl("https://yourdoctorprogram.com/qhr/CareDashboard/AllergiesEditorMaster.aspx");
			       

		        }else if(loadRequest == 2){

		              webview.loadUrl("https://yourdoctorprogram.com/qhr/Patients/PatientMainForPatient.aspx?logtype=login");
			          progressBar.dismiss();
			          titlerlayout1.setVisibility(RelativeLayout.VISIBLE);
			          rlayout.setVisibility(RelativeLayout.VISIBLE);
			          webview.loadUrl("javascript:( function () { var allergiesTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_DataList1'); var allergy = allergiesTable.getElementsByTagName('tr');var result = \"\";for(var i = 1; i < allergy.length; i +=3){ var allergyRow = allergy[i].getElementsByTagName('td');for(var j = 0; j < 4; j++){var childValue = allergyRow[j].childNodes[1].textContent;result += childValue;result += \"$#\";}}window.HTMLOUT.showAllergiesHTML(result);} ) ()");
			          loadRequest++;
			          
			          		     
			          adapter.notifyDataSetChanged();
		          
			          if(javeScritpInterfacee.userName.length()==0){

		        	  	String invalidLogin = "UserName and Password Incorrect";
						Intent intent = new Intent(getApplicationContext(), HOMESCREEN.class);
				        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
				        intent.putExtra("Invalid login message", invalidLogin);
				        startActivity(intent);
				        finish();
	 
		        	
			          }else{
		        	  titletext.setText(javeScritpInterfacee.userName + "'s Careplan");   
			          }
			       
			          showAllallergy();
		       }
		    	 
 

		    		    	  
		   }
		   
		});
	

	
 	    
 	    careplanlistview = (ListView) findViewById(R.id.cphomelistView);
 	    adapter = new Myadapter(this);
		careplanlistview.setAdapter(adapter);
        careplanlistview.setOnItemClickListener(this);
        allallergy = (TextView)findViewById(R.id.cphometextView2);
        rlayout = (RelativeLayout) findViewById(R.id.cprelativelayout);
        allallergy.setText("this is my text");
        ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent allergiesintent = new Intent(getApplicationContext(),UIFORALLERGESDETAILVIEW.class);

				Arraydatasample obj = new Arraydatasample();
				obj.arrayobj = javeScritpInterfacee.allergyRecords;
				allergiesintent.putExtra("sampleObject", obj);				
				allergiesintent.putExtra("userid", javeScritpInterfacee.userName);
				startActivity(allergiesintent);
				
			}
		});
		
	}
	
	  @Override
	public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.

			 menu.add(0, 0, 1, "Logout");
			 if(webview.getVisibility() == View.VISIBLE){
				 menu.add(1, 1, 2, "Summary");
			 }else{
				 menu.add(1, 1, 2, "Details");
			 }
			 menu.add(2, 2, 3, "Cancel");

			return true;
			
	}
	  
	 public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {

		        case 0:
		        		Intent intent = new Intent(getApplicationContext(), HOMESCREEN.class);
					    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
					    startActivity(intent);
					    finish();
			        return true;
		        case 1:
		        		if(webview.getVisibility()==View.VISIBLE){
		        		webview.setVisibility(View.INVISIBLE);
		        		item.setTitle("Details");
		        		}else{
		        		webview.setVisibility(View.VISIBLE);
		        		item.setTitle("Summary");
		        		}
	
			        	  SharedPreferences settings = getApplicationContext().getSharedPreferences("Websettings", MODE_PRIVATE);
			        	  Editor editor = settings.edit();
			              editor.putInt("Key_state", webview.getVisibility());
			              // Commit the edits!
			              editor.commit();	
			        return true;
		        case 2:
		        	
			        return true;
		        default:
		        return super.onOptionsItemSelected(item);
	        }
	    }


	 public void showAllallergy(){

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      allallergy.setText(javeScritpInterfacee.allergyvar);
	  }
	  
	  void showCareplane(){
		  
	  }
		

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
	
			Intent careplandetailactivity = new Intent(getApplicationContext(),Careplandetailview.class);
			String[] careplan = javeScritpInterfacee.careplanRecords.get(position);
			careplandetailactivity.putExtra("careplanrecord",careplan);
			careplandetailactivity.putExtra("userid", javeScritpInterfacee.userName);
			startActivity(careplandetailactivity);
			
			
		}
		

  }	    


	


