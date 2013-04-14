package com.example.ydp;




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


public class Careplandetailview extends Activity {
	String careplandetilhname;
	String[] careplandetail ;
	String[] names = {"Date","ICD9 Diagnosis","Status","Risk Factors","Goals/Instructions","Interventions","Medication","Pracitioner"};
	TextView cp,titletext; 
	Button backlogout;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.careplandeatailview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlewithbackbutton);
		titletext = (TextView) findViewById(R.id.backtitletextview);
		
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
		
		ListView lv = (ListView) findViewById(R.id.carepalndetaillistView);
	     lv.setAdapter(new Careplanadapter(this));
	     careplandetail = getIntent().getExtras().getStringArray("careplanrecord");
	     careplandetilhname =getIntent().getExtras().getString("userid");
			
	     titletext.setText(careplandetilhname + "'s careplan");  
	     
	     
	     cp =(TextView)findViewById(R.id.cpdetailtextView2);
	     cp.setText(careplandetail[1]);
	     
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
			

			
			
			
			
			LayoutInflater li = getLayoutInflater();
			v = li.inflate(R.layout.carepalndetaildataview, null);
			 TextView t1 = (TextView)v.findViewById(R.id.cpdetaildatatextView1);
			 TextView t2 = (TextView)v.findViewById(R.id.cpdetaildatatextView2);
			 
			 if (position % 2 == 0) {
				 
				  v.setBackgroundColor(0x30EAE7E7);
				}
			 
			 t1.setText(names[position]);
			 t2.setText(careplandetail[position]);
			 
					 return v;
		}
		

	}
	
	
	}


