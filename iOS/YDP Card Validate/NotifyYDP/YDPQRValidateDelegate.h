//
//  YDPAppDelegate.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"

@class YDPLogin;

@interface YDPQRValidateDelegate : UIResponder <UIApplicationDelegate,MBProgressHUDDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) YDPLogin *carePlanViewController;
@property (strong, nonatomic) MBProgressHUD *progressHudForWindow;
- (void)startSpinningLoaderWithMessage:(NSString *)message;
- (void)stopSpinningLoader;

- (UILabel *)getLabelWithFrame:(CGRect)frame WithText:(NSString *)text;
@end
