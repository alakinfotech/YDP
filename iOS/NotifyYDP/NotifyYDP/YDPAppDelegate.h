//
//  YDPAppDelegate.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"

@class YDPViewController;
@class YDPCarePlan;

@interface YDPAppDelegate : UIResponder <UIApplicationDelegate,MBProgressHUDDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) YDPViewController *viewController;
@property (strong, nonatomic) YDPCarePlan *carePlanViewController;
@property (strong, nonatomic) UITabBarController *tabBarController;
@property (strong, nonatomic) MBProgressHUD *progressHudForWindow;
- (void)startSpinningLoaderWithMessage:(NSString *)message;
- (void)stopSpinningLoader;
@end
