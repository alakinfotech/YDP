package com.vibgyor.tallycounter;

import org.ksoap2.*;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	int count;
	Button LoginButton;
	TextView textView;
	EditText UserID;
	EditText PWD;
	StringBuilder sb;
	
	public static final String NAMESPACE = "http://tempuri.org/";
	public static final String URL = "http://192.168.1.3/QHRMobile/QHRMobileService.svc?wsdl";  
	public static final String SOAP_ACTION = "http://tempuri.org/IQHRMobileService/ValidateUser";
	public static final String METHOD_NAME = "ValidateUser";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//Custom Implementation
		LoginButton = (Button) findViewById(R.id.buttonLogin);
		textView = (TextView) findViewById(R.id.text_count_number);
		UserID = (EditText) findViewById(R.id.editUserId);
		PWD = (EditText) findViewById(R.id.editPassword);
		sb = new StringBuilder();
		
		//Listener Setup
		LoginButton.setOnClickListener(new View.OnClickListener() {
				
					public void onClick(View v)
					{
						//Click Handler
						SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
						request.addProperty("UserId", UserID.getText().toString()); //"adikadapa"
						request.addProperty("PWD", PWD.getText().toString()); //"Medico!8"
						SoapSerializationEnvelope envelope = 
						    new SoapSerializationEnvelope(SoapEnvelope.VER11); 

						envelope .dotNet = true;

						envelope.setOutputSoapObject(request);
						HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

						try {
						    androidHttpTransport.call(SOAP_ACTION, envelope);
						    SoapPrimitive result = (SoapPrimitive)envelope.getResponse();
						  //to get the data
					        String resultData = result.toString();
					        // 0 is the first object of data 
					        sb.delete(0,sb.length());
					        sb.append(resultData + "\n");
					        setCountText(sb);
						}
						catch (Exception e) {
							setCountText(new StringBuilder(e.toString()));
						}
						
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void setCountText(StringBuilder text)
	{
		textView.setText(text);
	}
}
