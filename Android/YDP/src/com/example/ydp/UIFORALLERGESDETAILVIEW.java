package com.example.ydp;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UIFORALLERGESDETAILVIEW  extends Activity{
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allargiesdetailview);
		ListView lv = (ListView) findViewById(R.id.alglistView1);


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
			v = li.inflate(R.layout.allergiesdataview, null);
			 TextView t1 = (TextView)v.findViewById(R.id.alddata1);
			 TextView t2 = (TextView)v.findViewById(R.id.alddata2);
			 TextView t3 = (TextView)v.findViewById(R.id.alddata3);
			 TextView t4 = (TextView)v.findViewById(R.id.alddata4);
			 
			 t1.setText(names[position]);
			 t2.setText(location[position]);
			 t3.setText(phoneno[position]);
			 t4.setText(pic[position]);
			 return v;
		}
		String  names[]   = { "Gauthamasdgggddss","Gauthamasad","Gauthamas","Gauthamas" ,"Gauthamas","Gauthamas","Gauthamas"   };
		  String location[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
		  String phoneno[] ={ "9981152313","9811313134","9854533319","8982456189","9989124629","9989126565","9989515636"};
		  String pic[]={ "Utterpkjkgfradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh","Utterpradesh"};
	}
}
