/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.QHR;



import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class UIFORALLERGESDETAILVIEW  extends Activity{
	
	ArrayList<String[]> allergydetail;
	String allergydetailhname;
	TextView  titletext;
	ImageButton logout;
	RelativeLayout titlerlayout2;
	ListView allergylistview;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.allargiesdetailview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.normaltitlebar);
		titlerlayout2 =(RelativeLayout) findViewById(R.id.RelativeLayout2);
		titlerlayout2.setVisibility(RelativeLayout.VISIBLE);
		titletext = (TextView) findViewById(R.id.titletextview2);

		allergylistview = (ListView) findViewById(R.id.alglistView1);
		allergydetailhname =getIntent().getExtras().getString("userid");
		titletext.setText(allergydetailhname + "'s Careplan");  
		 Intent i = getIntent();
		 Arraydatasample obj = (Arraydatasample)i.getSerializableExtra("sampleObject");
		 allergydetail = obj.arrayobj ;
		 allergylistview.setAdapter(new Algadapter(this));
	}

	
	
	class Algadapter extends BaseAdapter
	{
 
		
		 Context context;
		   public Algadapter(Context c) {
		// TODO Auto-generated constructor stub
			  this.context =c;
	        }
		
		public int getCount() {
			// TODO Auto-generated method stub
			return allergydetail.size();
			
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
			// TODO Auto-generated method stub

			 LayoutInflater layoutinflater = getLayoutInflater();
			 view = layoutinflater.inflate(R.layout.allergiesdataview, null);
			 TextView allergydetailtview1 = (TextView)view.findViewById(R.id.alddata1);
			 TextView allergydetailtview2 = (TextView)view.findViewById(R.id.alddata2);
			 TextView allergydetailtview3 = (TextView)view.findViewById(R.id.alddata3);
			 TextView allergydetailtview4 = (TextView)view.findViewById(R.id.alddata4);
			 
			 String[] allergydata = allergydetail.get(position);
			 
			 
			 if (position % 2 == 0) {
				 
				  view.setBackgroundColor(0x30E6EEF8);
				}
			 
			 if(allergydata[0].equals("undefined")){
				 allergydata[0] = " ";
			 }
			 if(allergydata[1].equals("undefined")){
				 allergydata[1] = " ";
			 }
			 if(allergydata[2].equals("undefined")){
				 allergydata[2] = " ";
			 }
			 if(allergydata[3].equals("undefined")){
				 allergydata[3] = " ";
			 }
			 if(allergydata[3].equals("Inactive"))
			 {
				 allergydetailtview1.setTextColor(0x30141823);
				 allergydetailtview2.setTextColor(0x30141823);
				 allergydetailtview3.setTextColor(0x3029272A);
				 allergydetailtview4.setTextColor(0x3029272A);
				 
			 }

			 allergydetailtview1.setText(allergydata[0]);
			 allergydetailtview2.setText(allergydata[1]);
			 allergydetailtview3.setText(allergydata[2]);
			 allergydetailtview4.setText(allergydata[3]);
			 
			 return view;
		}

	}
}
