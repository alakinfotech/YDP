/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDP mobile application is used show patient records.
 */
package com.alakinfotech.QHR;

import org.apache.commons.codec.digest.DigestUtils;  
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;  
import org.bouncycastle.crypto.engines.AESFastEngine;  
import org.bouncycastle.crypto.modes.CBCBlockCipher;  
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;  
import org.bouncycastle.crypto.params.KeyParameter;  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
 

/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 08 Dec 2012
 *
 */
/***
 * 
 * Cipher class used to convert
 * normal data to encrypt format and
 * encrypt data to plain format
 *
 ***/
public class Cipher  {  
  
    @SuppressWarnings("unused")
	private static final byte[] NULL = null;
	private final String password;  
  
	
	//-----------------------------------------------------------------------
	// Function:      	Cipher(String password)
	//
	// Parameter:
	//	     In:        string password - password as parameters
	//	               
	//	     Out:       none
	//	     In/Out:    none
	//
	// Returns:       	none
	//
	// Desc:      		Constructor to store  secret key.
	//-----------------------------------------------------------------------
    public Cipher(String password) {  
        this.password = password;
      }  
 
    
	//-----------------------------------------------------------------------
	// Function:      	encrypt(byte[] plainText)
	//
	// Parameter:
	//	     In:       byte[] plainText - plain text is passed as parameter
	//	               
	//	     Out:       none
	//	     In/Out:    none
	//
	// Returns:       	byte[]
	//
	// Desc:      		 function to take plain text.
	//-----------------------------------------------------------------------
    public byte[] encrypt(byte[] plainText) throws Exception {  
        return transform(true, plainText);  
    }  
    
    
    
    
    
    
	//-----------------------------------------------------------------------
	// Function:      	decrypt(byte[] cipherText)
	//
	// Parameter:
	//	     In:        byte[] cipherText - takes cipher data 
	//	               
	//	     Out:       none
	//	     In/Out:    none
	//
	// Returns:       	byte[]
	//
	// Desc:      		Decrypt function to decrypt the user data.
	//-----------------------------------------------------------------------
    public byte[] decrypt(byte[] cipherText) throws Exception {  
        return transform(false, cipherText);  
    }  
    
    
    
    
    
    
	//-----------------------------------------------------------------------
	// Function:      	transform(boolean encrypt, byte[] inputBytes)
	//
	// Parameter:
	//	     In:       boolean encrypt
    //				   byte[] inputBytes
	//	               
	//	     Out:       none
	//	     In/Out:    none
	//
	// Returns:       	byte[]
	//
	// Desc:      		Transform plain format to ciper format or cipher to plain text format.
	//-----------------------------------------------------------------------
    private byte[] transform(boolean encrypt, byte[] inputBytes) throws Exception {  
        byte[] key = DigestUtils.md5(password.getBytes("UTF-8"));  
  
        
        AESFastEngine aesfastEngine = new AESFastEngine();
        BlockCipher cbsblockCipher = new CBCBlockCipher(aesfastEngine);
       
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(cbsblockCipher);
        cipher.init(encrypt, new KeyParameter(key));  
  
        ByteArrayInputStream input = new ByteArrayInputStream(inputBytes);  
        ByteArrayOutputStream output = new ByteArrayOutputStream();  
  
        int inputLen;  
        int outputLen;  
  
        byte[] inputBuffer = new byte[1024];  
        byte[] outputBuffer = new byte[cipher.getOutputSize(inputBuffer.length)];  
  
        while ((inputLen = input.read(inputBuffer)) > -1) {  
            outputLen = cipher.processBytes(inputBuffer, 0, inputLen, outputBuffer, 0);  
            if (outputLen > 0) {  
                output.write(outputBuffer, 0, outputLen);  
            }  
        }  
  
        outputLen = cipher.doFinal(outputBuffer, 0);  
        if (outputLen > 0) {  
            output.write(outputBuffer, 0, outputLen);  
        }  
  
        return output.toByteArray();  
    }  
} 