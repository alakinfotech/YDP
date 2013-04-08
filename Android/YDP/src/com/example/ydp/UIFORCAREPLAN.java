package com.example.ydp;





import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	
	String  names[]   = { "Gauthamasdgggddss","Gauthamasad","Gauthamas","Gauthamas" ,"Gauthamas","Gauthamas","Gauthamas"   };
	  String location[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
	  String phoneno[] ={ "9981152313","9811313134","9854533319","8982456189","9989124629","9989126565","9989515636"};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.careplanhome);
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
			
			
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "selected name is :"+names[position], 3000).show();
//
//				startActivity(new Intent(getApplicationContext(), Careplandetailview.class));
//				
//			}

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
				 
				 t1.setText(names[position]);
				 t2.setText(location[position]);
				 t3.setText(phoneno[position]);
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


	


