/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.ydpandroid;

import com.alakinfotech.ydpandroid.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 08 Dec 2012
 *
 */


public class Careplandetailview extends Activity {
	
	String careplandetilhname;
	String[] careplandetail ;
	String[] names = {"Date:","ICD9 Diagnosis:","Status:","Risk Factors:","Assessment/Plan:","Goals:","Medication:","Pracitioner:"};
	TextView careplantxtview,titletext; 
	ImageButton logout;
	RelativeLayout titlerlayout3;
	ListView careplanlistview;
	 
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.careplandeatailview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.normaltitlebar);
		titlerlayout3 =(RelativeLayout) findViewById(R.id.RelativeLayout2);
		titlerlayout3.setVisibility(RelativeLayout.VISIBLE);
		titletext = (TextView) findViewById(R.id.titletextview2);
		careplantxtview =(TextView)findViewById(R.id.cpdetailtextView2);
	    careplanlistview = (ListView) findViewById(R.id.carepalndetaillistView);
	    careplanlistview.setAdapter(new Careplanadapter(this));
	    careplandetail = getIntent().getExtras().getStringArray("careplanrecord");
	    careplandetilhname =getIntent().getExtras().getString("userid");
		titletext.setText(careplandetilhname + "'s Careplan");  
	    careplantxtview.setText(careplandetail[1]);
	     
	  }  
	
	class Careplanadapter extends BaseAdapter 	{

		
		 Context context;
		   public Careplanadapter(Context c) {
		// TODO Auto-generated constructor stub
			  this.context =c;
	        }
		
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
			
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
		public View getView(int position, View view, ViewGroup parent) {

			 LayoutInflater layoutinflater = getLayoutInflater();
			 view = layoutinflater.inflate(R.layout.carepalndetaildataview, null);
			 TextView cptextview1 = (TextView)view.findViewById(R.id.cpdetaildatatextView1);
			 TextView cptextview2 = (TextView)view.findViewById(R.id.cpdetaildatatextView2);
			 
			 if (position % 2 == 0) {
				 
				  view.setBackgroundColor(0x30E6EEF8);
				}
			 
			 cptextview1.setText(names[position]);
			 cptextview2.setText(careplandetail[position]);
			 
			 return view;
		}
		

	}
	
	
	}


