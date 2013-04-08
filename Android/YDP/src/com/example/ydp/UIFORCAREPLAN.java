package com.example.ydp;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class UIFORCAREPLAN extends Activity implements OnItemClickListener{
	
	
	WebView wb1;
	private ProgressDialog progressBar;
	TextView user,pass;
	boolean loadingFinished = false;
	boolean redirect = false;
	static int i = 0;
	
	
	String  n[]   = { "Gauthamasdgggddss","Gauthamasad","Gauthamas","Gauthamas" ,"Gauthamas","Gauthamas","Gauthamas"   };
	  String l[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
	  String p[] ={ "9981152313","9811313134","9854533319","8982456189","9989124629","9989126565","9989515636"};
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.careplanhome);
	   	  
		String usern = getIntent().getExtras().getString("username");
 	   String passw = getIntent().getExtras().getString("password"); 
 	  final Context myApp = this;
 	  
 	  class MyJavaScriptInterface   
 	 {  
 	     @SuppressWarnings("unused")  
 	     public void showHTML(String html)  
 	     {  
 	         new AlertDialog.Builder(myApp)  
 	             .setTitle("HTML")  
 	             .setMessage(html)  
 	             .setPositiveButton(android.R.string.ok, null)  
 	         .setCancelable(false)  
 	         .create()  
 	         .show();  
 	     }  
 	 }  
 	   
 	     wb1 = (WebView)findViewById(R.id.webView1);
 	   wb1.getSettings().setJavaScriptEnabled(true); 
 	   	wb1.getSettings().setBuiltInZoomControls(true);
		wb1.setInitialScale(1);
		wb1.getSettings().setLoadWithOverviewMode(true);
		wb1.getSettings().setUseWideViewPort(true);
		wb1.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wb1.setScrollbarFadingEnabled(false);
		
		progressBar = ProgressDialog.show(UIFORCAREPLAN.this, "YDP page is ", "Loading...");

 	  
 	  /* Register a new JavaScript interface called HTMLOUT */  
 	  wb1.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");  
 	    
 	  /* WebViewClient must be set BEFORE calling loadUrl! */  
// 	  wb1.setWebViewClient(new WebViewClient() {  
// 	      @Override  
// 	      public void onPageFinished(WebView view, String url)  
// 	      {  
// 	          /* This call inject JavaScript into the page which just finished loading. */  
// 	         // wb1.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");  
 	 		i = 0;
			
	wb1.loadUrl("https://yourdoctorprogram.com/qhr/Login.aspx/");
	wb1.setWebViewClient(new WebViewClient() {

		 @Override
		   public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
		       if (!loadingFinished) {
		          redirect = true;
		       }

		   loadingFinished = false;
		   wb1.loadUrl(urlNewString);
		   return true;
		   }
		   
		   public void onPageStarted(WebView view, String url) {
		        loadingFinished = false;
		        
		    }
		   
		   @Override
		   public void onPageFinished(WebView view, String url) {
			   Log.d("page loaded","page not loaded");
			   //if(!redirect)
			   {
				   
	             loadingFinished = true;
	             }

		      // if(loadingFinished && !redirect){
		    	   
		    	 if(i == 0){
		    	 
		    	   Log.d("page loaded","page");
		    	   
		    	   String user = getIntent().getExtras().getString("username");
		    	   String pass = getIntent().getExtras().getString("password");

		    	   wb1.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').value='"+user+"';");
		    	   wb1.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').value='"+pass+"';");
		    	   wb1.loadUrl("javascript:document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_bt_Login').click();");
		    	  // wb.loadUrl("file:///android_asset/javascript.js");
		    	   
		    	   i++;
		    	   //wb1.loadUrl("javascript:window.MyHandler.setmydata(document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView_Label11').innerHTML)");
		    	   
		    	   
		    	   
		    	 }
		    	 else if(i==1)
		    	   {
					
		    	     progressBar.dismiss();
		    	     i++;
		    	  
		    	   }
//		       } else{
//		          redirect = false; 
//		       }
		   
		    }
		   
		   
		});
	

	
 	    
 	    
 	    
 	    //wb1.loadUrl("https://yourdoctorprogram.com/qhr/Login.aspx/");
 	  
 	  
 	  
 	  
		
		
		
		ListView lv = (ListView) findViewById(R.id.cphomelistView);
		 lv.setAdapter(new Myadapter(this));
        lv.setOnItemClickListener(this);
		ImageButton img = (ImageButton) findViewById(R.id.imageButton1);
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent allergiesintent = new Intent(getApplicationContext(),UIFORALLERGESDETAILVIEW.class);
				startActivity(allergiesintent);
				
			}
		});
		
	}
		
		class Myadapter extends BaseAdapter 
		{

			
			 Context context;
			   public Myadapter(Context c) {
			// TODO Auto-generated constructor stub
				  this.context =c;
		        }
		 	
			public int getCount() {
				// TODO Auto-generated method stub
				return n.length;
				
			}

			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			


			@Override
			public View getView(int position, View v, ViewGroup parent) {
				// TODO Auto-generated method stub
				
	//for adding header to list view

//				ListView lv = getListView();
//				LayoutInflater inflater = getLayoutInflater();
//				ViewGroup header = (ViewGroup)inflater.inflate(R.layout.careplan, lv, false);
//				lv.addHeaderView(header, null, false);
				
				
				
				
				LayoutInflater li = getLayoutInflater();
				v = li.inflate(R.layout.careplandataview, null);
				 TextView t1 = (TextView)v.findViewById(R.id.cpdatatextView1);
				 TextView t2 = (TextView)v.findViewById(R.id.cpdatatextView2);
				 TextView t3 = (TextView)v.findViewById(R.id.cpdatatextView3);
				 
				 t1.setText(n[position]);
				 t2.setText(l[position]);
				 t3.setText(p[position]);
				 return v;
			}
			
			
			
	
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			//Toast.makeText(getApplicationContext(), "selected name is :", 3000).show();
			
			Intent i = new Intent(getApplicationContext(),Careplandetailview.class);
			startActivity(i);
			
			
		}
		

	}	    


	


