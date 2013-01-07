package com.example.encoding;


import javax.crypto.spec.SecretKeySpec;



import android.app.Activity;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.*;
//import org.bouncycastle.util.encoders.Base64;


import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//

 
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.SecureRandom;
//http://www.java2s.com/Code/Java/Security/EncryptionanddecryptionwithAESECBPKCS7Padding.htm
 
public class MainActivity extends Activity {
	
 

EditText inputTxtFld,encryptTxtFld,decryptTxtFld;
Button encryptbtn,decryptbtn;
 public	String inputStr,encryptedStr,decryptedStr;
 public byte[] cipherText;
 public int ctLength;
SecretKeySpec key;
byte[] encryptedBytesTest;
String encryptedStringTest;
 @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	inputTxtFld = (EditText) findViewById(R.id.editText1);
    	encryptTxtFld = (EditText) findViewById(R.id.editText2);
    	decryptTxtFld = (EditText) findViewById(R.id.editText3);
    	
    	Toast.makeText(getApplicationContext(), "entering into on click method",3000).show();

    	
    	
    	
    	encryptbtn = (Button) findViewById(R.id.button1);
    	encryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
				
				
inputStr = inputTxtFld.getText().toString();
  Log.d("intputstr", inputStr);                

				Cipher cipher = new Cipher("A7Q6DyH0LW9VF7G55TEyFw==");
		    	
				

				
//			Toast.makeText(getApplicationContext(), "not entering into on click method",3000).show();
			//byte[] input = inputStr.getBytes();
			byte[] input =null;
			try {
				input = inputStr.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			
			byte[] reseltByts = null;
				  
				try {
					encryptedBytesTest =  reseltByts = cipher.encrypt(input);
				 	  Log.d("in try block", inputStr);                

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					  Log.d("in catch block", inputStr);                
				}
		String resultData = null;
				
				
				if(reseltByts != null){ 
					
					
					try {
						
						byte [] reseltBase64Bytes = Base64.encodeBase64(reseltByts);
						resultData = new String(reseltBase64Bytes, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//resultData = Base64.encodeToString(reseltByts, RESULT_OK);
					//resultData = new String(reseltByts);
					encryptedStringTest = resultData; 
					
					Log.d("in result bytes", inputStr); 
				}
	
				
				encryptTxtFld.setText(new String(resultData));
			       }
			});
    	  
    	decryptbtn =(Button) findViewById(R.id.button2);
    	decryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
decryptedStr = encryptTxtFld.getText().toString();

			Cipher cipher = new Cipher("A7Q6DyH0LW9VF7G55TEyFw==");
				byte[] encryptinput=null;
				try {
					
		byte[] dect=decryptedStr.getBytes("UTF-8");
					//String cipherText = new String(Base64.decodeBase64(decryptedStr), "UTF-8");
				encryptinput = Base64.decodeBase64(dect);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				//	byte[] encryptinput =Base64.decode(encryptedStringTest, RESULT_OK);
					byte[] reseltByts = null;

				try {
					reseltByts = cipher.decrypt(encryptinput);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		String resultData = null;


			if(reseltByts != null){
	
		//resultData = Strings.fromUTF8ByteArray(reseltByts);	
			//resultData =new String(reseltByts);
			
			
			try {
				resultData = new String(reseltByts, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}


	decryptTxtFld.setText(new String(resultData));
   }
		    	
});
    	
}

}
