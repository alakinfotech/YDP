package com.example.widgettab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;


public class SecondScreen extends Activity{
	GridView gridView;
	@Override
 
 protected void onCreate(Bundle savedInstanceState) {
  // TODO Auto-generated method stub
  super.onCreate(savedInstanceState);
  setContentView(R.layout.secondscreen);
  gridView = (GridView) findViewById(R.id.gridView1);
  ImageAdapter imageAdapter = new ImageAdapter(this);
  gridView.setAdapter(imageAdapter);

  
 }
}