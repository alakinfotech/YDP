//
//  YDPAppDelegate.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPQRValidateDelegate.h"
#import "YDPLogin.h"

@implementation YDPQRValidateDelegate

@synthesize window = _window;
@synthesize carePlanViewController;
@synthesize progressHudForWindow;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [[UIApplication sharedApplication] setStatusBarHidden:NO];
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    
    self.carePlanViewController = [[YDPLogin alloc] initWithNibName:@"YDPLogin" bundle:nil];
    self.window.rootViewController = self.carePlanViewController;
    [self.window makeKeyAndVisible];
    // force view class to load so it may be referenced directly from NIB
    [ZBarReaderView class];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

- (void)startSpinningLoaderWithMessage:(NSString *)message {
	ShowNetworkActivityIndicator();
    
    if (!self.progressHudForWindow) {
        //ProgressHUD presenting to window
        self.progressHudForWindow = nil;
        self.progressHudForWindow = [[MBProgressHUD alloc] initWithView:self.window];
        self.progressHudForWindow.removeFromSuperViewOnHide = YES;
        [self.window addSubview:self.progressHudForWindow];
        self.progressHudForWindow.delegate = self;
        self.progressHudForWindow.opacity = 0.70;
        self.progressHudForWindow.dimBackground = YES;
        
        [self.progressHudForWindow show:YES];
    }
    
    self.progressHudForWindow.activeCount++;
    self.progressHudForWindow.labelText = message;
}

- (void)stopSpinningLoader {
    self.progressHudForWindow.activeCount--;
    
    if (self.progressHudForWindow.activeCount <= 0) 
    {
        HideNetworkActivityIndicator();
        // Manually we are hiding the loading HUD
        [self.progressHudForWindow hide:YES];
        self.progressHudForWindow = nil;
    }
}


- (UILabel *)getLabelWithFrame:(CGRect)frame WithText:(NSString *)text {
    
    UILabel *tempLabel = [[UILabel alloc] initWithFrame:frame];
    tempLabel.text = text;
    tempLabel.textAlignment = UITextAlignmentCenter;
    tempLabel.backgroundColor = [UIColor clearColor];
    tempLabel.font = [UIFont boldSystemFontOfSize:15];
    tempLabel.textColor = [UIColor whiteColor];
    return tempLabel;
}

@end
