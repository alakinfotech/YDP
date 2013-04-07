package com.example.ydp;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class UIFORCAREPLAN extends Activity{
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uicarepalnhome);
	    ListView lv = (ListView) findViewById(R.id.listView1);


        lv.setAdapter(new Myadapter(this));
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

			@Override
			public View getView(int position, View v, ViewGroup parent) {
				// TODO Auto-generated method stub
				
				
				LayoutInflater li = getLayoutInflater();
				v = li.inflate(R.layout.careplandataview, null);
				 TextView t1 = (TextView)v.findViewById(R.id.textView1);
				 TextView t2 = (TextView)v.findViewById(R.id.textView2);
				 TextView t3 = (TextView)v.findViewById(R.id.textView3);
				 
				 t1.setText(names[position]);
				 t2.setText(location[position]);
				 t3.setText(phoneno[position]);
				 return v;
			}
			String  names[] = {   "Name:Gautham","Name:Harbhajan","Name:Sachin","Name:sehwag" ,"Name:SureshRaina","Name:virat","Name:Yuvaraj"   };
			  String location[]={ "loc:Delhi","loc:panjab","loc:Mumbai","loc:Delhi","loc:Utterpradesh","loc:Delhi","loc:Chandigarh"    };
			  String phoneno[] ={"phno:9981152313","phno:9811313134","phno:9854533319","phno:8982456189","phno:9989124629","phno:9989126565","phno:9989515636","phno:8989136568","phno:9999126566","phno:9987265189"};
	
		}
		

	}	    


	


