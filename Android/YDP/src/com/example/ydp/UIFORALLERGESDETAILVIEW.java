package com.example.ydp;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class UIFORALLERGESDETAILVIEW  extends Activity{
	
	ArrayList<String[]> allergydetail;
	String allergydetailhname;
	TextView  backtitletext;
	Button backlogout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.allargiesdetailview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlewithbackbutton);
		 backtitletext = (TextView) findViewById(R.id.backtitletextview);
		 backlogout = (Button) findViewById(R.id.backlogoutbutton);
		 backlogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), YDPCAREPLAN.class);
			       // intent.putExtra("finish", true);
			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
			        startActivity(intent);
			       finish();
			}
		});
		
		
		
		
		ListView lv = (ListView) findViewById(R.id.alglistView1);
		allergydetailhname =getIntent().getExtras().getString("userid");
		
		 backtitletext.setText(allergydetailhname + "'s careplan");  
		//allergydetail = getIntent().getExtras().getStringArray("allergyrecord");
		Intent i = getIntent();
		Arraydatasample obj = (Arraydatasample)i.getSerializableExtra("sampleObject");
		allergydetail = obj.arrayobj ;
		lv.setAdapter(new Algadapter(this));
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
		public View getView(int position, View v, ViewGroup parent) {
			// TODO Auto-generated method stub
			

			
			
			
			
			LayoutInflater li = getLayoutInflater();
			v = li.inflate(R.layout.allergiesdataview, null);
			 TextView t1 = (TextView)v.findViewById(R.id.alddata1);
			 TextView t2 = (TextView)v.findViewById(R.id.alddata2);
			 TextView t3 = (TextView)v.findViewById(R.id.alddata3);
			 TextView t4 = (TextView)v.findViewById(R.id.alddata4);
			 
			 String[] allergydata = allergydetail.get(position);
			 
			 
			 if (position % 2 == 0) {
				 
				  v.setBackgroundColor(0x30EAE7E7);
				}
			 
			 t1.setText(allergydata[0]);
			 t2.setText(allergydata[1]);
			 t3.setText(allergydata[2]);
			 t4.setText(allergydata[3]);
			 return v;
		}
//		String  names[]   = { "Gauthamasdgggddss","Gauthamasad","Gauthamas","Gauthamas" ,"Gauthamas","Gauthamas","Gauthamas"   };
//		  String location[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
//		  String phoneno[] ={ "9981152313","9811313134","9854533319","8982456189","9989124629","9989126565","9989515636"};
//		  String pic[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
	}
}
