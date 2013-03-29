package com.objectgraph.JavascriptTest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import android.inputmethodservice.*;
public class MyActivity extends Activity{
    WebView myWebView;
    TextView myResult;
    
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        myResult = (TextView)this.findViewById(R.id.myResult);

        myWebView = (WebView)this.findViewById(R.id.myWebView);
        myWebView.getSettings().setJavaScriptEnabled(true);
       // myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setPluginsEnabled(true);
        myWebView.loadUrl("file:///android_asset/index.html");
        //myWebView.loadDataWithBaseUrl()

        myWebView.addJavascriptInterface(new JavaScriptHandler(this), "MyHandler");

        Button btnSet = (Button)this.findViewById(R.id.button1);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          myWebView.loadUrl("javascript:window.MyHandler.setmydata(document.getElementById('test2').innerHTML)");
            }
        });

        Button btnSimple = (Button)this.findViewById(R.id.button2);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	callJavaScriptFunctionAndGetResultBack(444, 333);
            }
        });
        
        Button btn = (Button)this.findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 myWebView.loadUrl("javascript:window.MyHandler.setmydata(function getdata(){var mydata=document.getElementById('test2').innerHTML;return mydata;})");
				//changeme("I LOVE my INDIA");
			}
		});
        Button btnn =(Button)this.findViewById(R.id.button4);
        btnn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				myWebView.loadUrl("file:///android_asset/js/script.js");
				 myWebView.loadUrl("javascript:alert()");
			        myWebView.loadUrl("javascript:printMyName()");
				
			}
		});
    }
    public void changeme (String someText){
    	Log.v("mylog","changeme is called");	
    	 //myWebView.loadUrl("javascript:window.MyHandler.setmydata(function getdata(){var mydata=document.getElementById('test2').innerHTML;return mydata;})");
    	 myWebView.loadUrl("javascript:window.MyHandler.setmydata(document.getElementById('test2').innerHTML)");
    }
    public void changeText(String someText){
        Log.v("mylog","changeText is called");
        myWebView.loadUrl("javascript:document.getElementById('test1').innerHTML = '<strong>"+someText+"</strong>'");
    }

   public void callJavaScriptFunctionAndGetResultBack(int val1, int val2){
        Log.v("mylog","MyActivity.callJavascriptFunctions is called");
  //     myWebView.loadUrl("file:///android_asset/js/script.js");
//        Log.v("mylog","test MyActivity.page executing");
//        InputStream is;
//        String src= "";
//       /*try {
//            is = getAssets().open("script.js");
//            int size = is.available();
//            Log.v("mylog","MyActivity.inside tryblock  is called");
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            // Convert the buffer into a string.
//            src = new String(buffer);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        */
//        myWebView.loadUrl("javascript:alert()");
//        myWebView.loadUrl("javascript:printMyName()");
        
      // myWebView.loadUrl("addSomething("+val1+","+val2+"))");
       myWebView.loadUrl("javascript:window.MyHandler.setResult( addSomething("+val1+","+val2+") )");
    }
    
    
//    public void callJavaScriptFunctionAndGetResultBack(int val1, int val2){
//        Log.v("mylog","MyActivity.callJavascriptFunction is called");
//        myWebView.loadUrl("javascript:window.MyHandler.setResult( addSomething("+val1+","+val2+") )");
//    }


    public void javascriptCallFinished(final int val){
        Log.v("mylog","MyActivity.javascriptCallFinished is called : " + val);
        Toast.makeText(this, "Callback got val: " + val, 5).show();

        // I need to run set operation of UI on the main thread.
        // therefore, the above parameter "val" must be final
        runOnUiThread(new Runnable() {
            public void run() {
                myResult.setText("Callback got val: " + val);
            }
        });
    }
    
    
    public void javascriptCallFinish(final String mydata){
        Log.v("mylog","MyActivity.javascriptCallFinished is called : " + mydata);
        Toast.makeText(this, "mydata is: " + mydata, 5).show();

        // I need to run set operation of UI on the main thread.
        // therefore, the above parameter "val" must be final
        runOnUiThread(new Runnable() {
            public void run() {
                myResult.setText("Callback got val: " + mydata);
            }
        });
    }
}
