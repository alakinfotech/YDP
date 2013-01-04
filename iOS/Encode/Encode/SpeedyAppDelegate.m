//
//  SpeedyAppDelegate.m
//  Encode
//
//  Created by mac on 30/06/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SpeedyAppDelegate.h"
#import "NSData+Base64.h"

@implementation SpeedyAppDelegate

@synthesize window = window;
@synthesize inputText;
@synthesize outputText;
@synthesize encodeClicked;
@synthesize decodeText;

- (void)dealloc
{
    [super dealloc];
}
	
- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
    // Insert code here to initialize your application
    
    cipherObj=[[Cipher alloc]initWithKey:@"Adi&Revanth"];
    
}

- (IBAction)encode:(id)sender {
    
    NSString *data = inputText.string;
    NSData *encoded=[cipherObj encrypt:[data dataUsingEncoding:NSUnicodeStringEncoding]];    
    NSString *outputString = [encoded base64EncodedString];
    
    //outputString = [Cipher encrypt:inputText.string withKey:@"Adi&Revanth"];
    [outputText setString:outputString];
    
}
- (IBAction)decode:(id)sender {
    
    NSString *data = outputText.string;
    NSData *encoded=[cipherObj decrypt:[NSData dataFromBase64String:data]];
    NSString *outputString = [[NSString alloc] initWithData:encoded encoding:NSUnicodeStringEncoding];
    
    //outputString = [Cipher decrypt:outputText.string withKey:@"Adi&Revanth"];
    [decodeText setString:outputString];
    
}
@end
