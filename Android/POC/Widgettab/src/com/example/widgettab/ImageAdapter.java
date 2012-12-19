package com.example.widgettab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {
	 private Context context;
	 ImageAdapter imageAdapter;

	 public ImageAdapter(Context c) {
	  // TODO Auto-generated constructor stub
	  this.context = c;
	 }


	 public int getCount() {
	  // TODO Auto-generated method stub
	  return flowers.length;
	 }


	 public Object getItem(int position) {
	  // TODO Auto-generated method stub
	  return position;
	 }


	 public long getItemId(int position) {
	  // TODO Auto-generated method stub
	  return position;
	 }


	 public View getView(int position, View convertView, ViewGroup parent) {
	  // TODO Auto-generated method stub
	  ImageView image = new ImageView(context);


	  image.setImageResource(flowers[position]);
	  image.setScaleType(ScaleType.FIT_XY);
	  image.setLayoutParams(new GridView.LayoutParams(100, 100));


	  return image;
	 }


	 int[] flowers = {  R.drawable.gautham ,R.drawable.harbhajan,R.drawable.sachin,R.drawable.sehwag,R.drawable.sureshraina,R.drawable.viratkholi,R.drawable.yuvraj };
	}
