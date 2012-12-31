package com.example.encoding;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.SecureRandom;

 
public class MainActivity extends Activity {
	
 
 private String key ="srikanth";
EditText inputTxtFld,encryptTxtFld,decryptTxtFld;
Button encryptbtn,decryptbtn;
 public	String inputStr,encryptedStr,decryptedStr;
 public byte[] encryptedData,decryptedData,secretKeyData;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	inputTxtFld = (EditText) findViewById(R.id.editText1);
    	encryptTxtFld = (EditText) findViewById(R.id.editText2);
    	decryptTxtFld = (EditText) findViewById(R.id.editText3);
    	
    	
		    	
    	
    	
    	encryptbtn = (Button) findViewById(R.id.button1);
    	encryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//encryptdata = data.getText().toString();
		    	//Log.d("encrypt", encryptdata);
				inputStr = inputTxtFld.getText().toString();
		    	
				
				try {
				
					KeyGenerator kgen = KeyGenerator.getInstance("AES");
					SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
					sr.setSeed(key.getBytes());
					kgen.init(128, sr); // 192 and 256 bits may not be available
					SecretKey skey = kgen.generateKey();
					secretKeyData = skey.getEncoded();
					
					
					  
					  SecretKeySpec encodeSpec = new SecretKeySpec(secretKeyData, "AES");
					  Cipher encipher = Cipher.getInstance("AES");
					  encipher.init(Cipher.ENCRYPT_MODE, encodeSpec);
					  encryptedData = encipher.doFinal(inputStr.getBytes());
					  encryptedStr = new String(encryptedData.toString());
					 // Toast.makeText(getApplicationContext(),encryptedStr, 3000).show();
					
					  encryptTxtFld.setText(encryptedStr);

					
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	}
				
			
			
				
			});
    	
    	decryptbtn =(Button) findViewById(R.id.button2);
    	decryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
decryptedStr = encryptTxtFld.getText().toString();
		    	
				
				try {

					SecretKeySpec decodeSpec = new SecretKeySpec(secretKeyData, "AES");
					Cipher cip = Cipher.getInstance("AES");
					  cip.init(Cipher.DECRYPT_MODE, decodeSpec);
					  decryptedData = cip.doFinal(encryptedData);
					  decryptedStr = new String(decryptedData.toString());
					  //Toast.makeText(getApplicationContext(),decryptedStr, 3000).show();
					  decryptTxtFld.setText(decryptedStr);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	}
				
				
				
				
			
		});
    	
    	
    	
    	
    	
    }

}
