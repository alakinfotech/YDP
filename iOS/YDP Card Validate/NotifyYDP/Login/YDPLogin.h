//
//  YDPCarePlan.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Cipher.h"


@interface YDPLogin : UIViewController<ZBarReaderViewDelegate, UITextFieldDelegate, UIWebViewDelegate>{
    ZBarReaderView *scanView;
    ZBarCameraSimulator *cameraSim;
    Cipher *cipherObj;
    int requestid;
}

@property (strong, nonatomic) IBOutlet ZBarReaderView *scanView;
@property (strong, nonatomic) ZBarCameraSimulator *cameraSim;

@property (strong, nonatomic) IBOutlet UILabel *reselt;

@property (strong, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) NSString *userName;
@property (strong, nonatomic) NSString *password;


- (IBAction)resetScanCard;
@end
