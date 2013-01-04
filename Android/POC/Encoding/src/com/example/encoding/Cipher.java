package com.example.encoding;

import org.apache.commons.codec.digest.DigestUtils;  
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;  
import org.bouncycastle.crypto.engines.AESFastEngine;  
import org.bouncycastle.crypto.modes.CBCBlockCipher;  
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;  
import org.bouncycastle.crypto.params.KeyParameter;  
  
import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
  
public class Cipher  {  
  
    private static final byte[] NULL = null;
	private final String password;  
  
    public Cipher(String password) {  
        this.password = password;  
    }  
  
    public byte[] encrypt(byte[] plainText) throws Exception {  
        return transform(true, plainText);  
    }  
  
    public byte[] decrypt(byte[] cipherText) throws Exception {  
        return transform(false, cipherText);  
    }  
  
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
        byte[] outputBuffer = NULL;//new byte[cipher.getOutputSize(inputBuffer.length)];  
  
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
