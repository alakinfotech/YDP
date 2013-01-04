package com.example.encoding;


import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.Strings;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import org.bouncycastle.util.*;
//import org.bouncycastle.util.encoders.Base64;


//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.ShortBufferException;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.bouncycastle.util.Strings;
// 
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.NoSuchProviderException;
//import java.security.spec.AlgorithmParameterSpec;
//import java.security.SecureRandom;
//http://www.java2s.com/Code/Java/Security/EncryptionanddecryptionwithAESECBPKCS7Padding.htm
 
public class MainActivity extends Activity {
	
 

EditText inputTxtFld,encryptTxtFld,decryptTxtFld;
Button encryptbtn,decryptbtn;
 public	String inputStr,encryptedStr,decryptedStr;
 public byte[] cipherText;
 public int ctLength;
SecretKeySpec key;
byte[] keyBytes;
protected static final byte[] NULL = null;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	inputTxtFld = (EditText) findViewById(R.id.editText1);
    	encryptTxtFld = (EditText) findViewById(R.id.editText2);
    	decryptTxtFld = (EditText) findViewById(R.id.editText3);

    	
		//keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
		    //    0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

		
		
		//key = new SecretKeySpec(keyBytes, "AES");
		
		
		
	//final com.example.encoding.Cipher cipher = new com.example.encoding.Cipher("Adi@Reventh");
    	
    	
    	encryptbtn = (Button) findViewById(R.id.button1);
    	encryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "entering into on click method",3000).show();
				
				
inputStr = inputTxtFld.getText().toString();
                  

				Cipher cipher = new Cipher("Adi@Reventh");
		    	
				

			//com.example.encoding.Cipher cipher = new com.example.encoding.Cipher("Adi@Reventh");
				
			Toast.makeText(getApplicationContext(), "not entering into on click method",3000).show();
			byte[] input = inputStr.getBytes();
		    	
				

				byte[] reseltByts = null;
		    	
				try {
					reseltByts = cipher.encrypt(input);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		String resultData = null;
				
				
				if(reseltByts != null){
					
					resultData = Strings.fromUTF8ByteArray(reseltByts);	
				}
				
				
				encryptTxtFld.setText(new String(resultData));;
			       }
			});
    	
    	decryptbtn =(Button) findViewById(R.id.button2);
    	decryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
decryptedStr = encryptTxtFld.getText().toString();

			



				Cipher cipher = new Cipher("Adi@Reventh");
					byte[] encryptinput = decryptedStr.getBytes();
					byte[] reseltByts = null;

				try {
					reseltByts = cipher.decrypt(encryptinput);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		String resultData = null;


			if(reseltByts != null){
	
		resultData = Strings.fromUTF8ByteArray(reseltByts);	
			}


	decryptTxtFld.setText(new String(resultData));
   }
		    	
});
    	
}

}
