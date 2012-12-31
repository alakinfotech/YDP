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
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
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
byte[] keyBytes;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_main);
    	inputTxtFld = (EditText) findViewById(R.id.editText1);
    	encryptTxtFld = (EditText) findViewById(R.id.editText2);
    	decryptTxtFld = (EditText) findViewById(R.id.editText3);

    	
		keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
		        0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

		
		
		key = new SecretKeySpec(keyBytes, "AES");
    	
    	
    	encryptbtn = (Button) findViewById(R.id.button1);
    	encryptbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//encryptdata = data.getText().toString();
		    	//Log.d("encrypt", encryptdata);
				inputStr = inputTxtFld.getText().toString();
		    	
				
				try {
					byte[] input = inputStr.getBytes();
					//Toast.makeText(MainActivity.this, new String("Adi&Revanth".getBytes()), Toast.LENGTH_SHORT).show();
					Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
				    // encryption pass
				    cipher.init(Cipher.ENCRYPT_MODE, key);

				    cipherText = new byte[cipher.getOutputSize(input.length)];
				    ctLength = cipher.update(input, 0, input.length, cipherText, 0);
				    ctLength += cipher.doFinal(cipherText, ctLength);
				    encryptTxtFld.setText(new String(cipherText));
				    System.out.println(new String(cipherText));
				    System.out.println(ctLength);

				    
					 

					
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
					Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
					
					// decryption pass
				    cipher.init(Cipher.DECRYPT_MODE, key);
					byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
					int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
				    ptLength += cipher.doFinal(plainText, ptLength);
				    decryptTxtFld.setText(new String(plainText));
				    System.out.println(new String(plainText));
				    System.out.println(ptLength);
				    Toast.makeText(MainActivity.this, new String(plainText), Toast.LENGTH_SHORT).show();
				    
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ShortBufferException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	}
				
				
				
				
			
		});
    	
    	
    	
    	
    	
    }

}
