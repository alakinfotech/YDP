//
//  YDPCarePlan.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Cipher.h"


@interface YDPLogin : UIViewController<ZBarReaderViewDelegate, UITextFieldDelegate>{
    ZBarReaderView *scanView;
    ZBarCameraSimulator *cameraSim;
    Cipher *cipherObj;
}

@property (strong, nonatomic) IBOutlet ZBarReaderView *scanView;
@property (strong, nonatomic) ZBarCameraSimulator *cameraSim;

@property (strong, nonatomic) IBOutlet UITextField *userName;
@property (strong, nonatomic) IBOutlet UITextField *password;


- (IBAction)Login;

@end
