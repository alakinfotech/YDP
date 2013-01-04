//
//  SpeedyAppDelegate.h
//  Encode
//
//  Created by mac on 30/06/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Cocoa/Cocoa.h>
#import "Cipher.h"

@interface SpeedyAppDelegate : NSObject <NSApplicationDelegate>
{
    NSWindow *window;
    NSTextView *inputText;
    NSTextView *outputText;
    NSButton *encodeClicked;
    NSTextView *decodeText;
    Cipher *cipherObj;
    NSButton *decode;
}

@property (assign) IBOutlet NSWindow *window;
@property (assign) IBOutlet NSTextView *inputText;
@property (assign) IBOutlet NSTextView *outputText;
@property (assign) IBOutlet NSButton *encodeClicked;
@property (assign) IBOutlet NSTextView *decodeText;
- (IBAction)encode:(id)sender;
- (IBAction)decode:(id)sender;

@end
