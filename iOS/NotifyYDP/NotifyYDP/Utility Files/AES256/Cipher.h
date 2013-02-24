//
//  Cipher.h
//  AES128
//
//  Created by Srikanth T on 11/25/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>  
#import <CommonCrypto/CommonCryptor.h>  

@interface Cipher : NSObject {  
    NSString* cipherKey;  
}  

@property (retain) NSString* cipherKey;  

- (Cipher *) initWithKey:(NSString *) key;  

- (NSData *) encrypt:(NSData *) plainText;  
- (NSData *) decrypt:(NSData *) cipherText;  

- (NSData *) transform:(CCOperation) encryptOrDecrypt data:(NSData *) inputData;  

+ (NSData *) md5:(NSString *) stringToHash;  

@end
