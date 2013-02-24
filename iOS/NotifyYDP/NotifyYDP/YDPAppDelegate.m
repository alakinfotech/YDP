//
//  YDPAppDelegate.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPAppDelegate.h"

#import "YDPViewController.h"
#import "YDPCarePlan.h"

@implementation YDPAppDelegate

@synthesize window = _window;
@synthesize viewController = _viewController;
@synthesize tabBarController;
@synthesize carePlanViewController;
@synthesize progressHudForWindow;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [[UIApplication sharedApplication] setStatusBarHidden:NO];
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.viewController = [[YDPViewController alloc] initWithNibName:@"YDPViewController" bundle:nil];
    self.viewController.title = @"Notify YDP";
    self.viewController.tabBarItem.image = [UIImage imageNamed:@"all.png"];
    
    self.carePlanViewController = [[YDPCarePlan alloc] initWithNibName:@"YDPCarePlan" bundle:nil];
    self.carePlanViewController.title = @"YDP Care Plan";
    self.carePlanViewController.tabBarItem.image = [UIImage imageNamed:@"faves.png"];    
    //UITabBarItem *item = [[UITabBarItem alloc]initWithTitle:@"YDP Care Plan" image:nil tag:1];
    //carePlanViewController.tabBarItem = item;
    
    self.tabBarController = [[UITabBarController alloc]init];
    self.tabBarController.viewControllers = [NSArray arrayWithObjects:self.carePlanViewController,self.viewController, nil];
    
    self.window.rootViewController = self.tabBarController;
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
        self.progressHudForWindow = [[MBProgressHUD alloc] initWithView:self.window];
        //self.progressHudForWindow = tempLoadingSpinner;
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


@end
