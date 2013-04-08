package com.example.ydp;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.ydp.UIFORCAREPLAN.Myadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Careplandetailview extends Activity {
	
	
	private ArrayAdapter<String> listAdapter ;  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.careplandeatailview);
	    ListView lv = (ListView) findViewById(R.id.carepalndetaillistView);
	     lv.setAdapter(new Careplanadapter(this));
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
		public View getView(int position, View v, ViewGroup parent) {
			// TODO Auto-generated method stub
			
//for adding header to list view

//			ListView lv = getListView();
//			LayoutInflater inflater = getLayoutInflater();
//			ViewGroup header = (ViewGroup)inflater.inflate(R.layout.careplan, lv, false);
//			lv.addHeaderView(header, null, false);
			
			
			
			
			LayoutInflater li = getLayoutInflater();
			v = li.inflate(R.layout.carepalndetaildataview, null);
			 TextView t1 = (TextView)v.findViewById(R.id.cpdetaildatatextView1);
			 TextView t2 = (TextView)v.findViewById(R.id.cpdetaildatatextView2);
			 
			 
			 t1.setText(names[position]);
			 t2.setText(location[position]);
					 return v;
		}
		String  names[]   = { "Gauthamasdgggddss","Gauthamasad","Gauthamas","Gauthamas" ,"Gauthamas","Gauthamas","Gauthamas"   };
		  String location[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
		//  String phoneno[] ={ "9981152313","9811313134","9854533319","8982456189","9989124629","9989126565","9989515636"};
		

	}
	
	
	}


