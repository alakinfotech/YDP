//
//  YDPWebViewController.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 7/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class YDPReachability;

@interface YDPCarePlanViewController : UIViewController<UIWebViewDelegate,UITableViewDataSource,UITableViewDelegate, UIActionSheetDelegate>{
    int requestid;
    YDPReachability* internetReachable;
    YDPReachability* hostReachable;
}
@property (strong, nonatomic) NSString *url;
@property (strong, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) NSString *userName;
@property (strong, nonatomic) NSString *password;
@property (strong, nonatomic) NSMutableDictionary *carePlan;
@property (strong, nonatomic) NSMutableArray *carePlanRecoed;

@property (strong, nonatomic) NSMutableDictionary *allergies;
@property (strong, nonatomic) NSMutableArray *allergiesRecoed;

@property (readwrite) BOOL isAudination;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UIView *loadingView;
- (IBAction)Logout:(id)sender;
@property (strong, nonatomic) IBOutlet UILabel *userID;
@property (strong, nonatomic) IBOutlet UILabel *allergiesList;
- (IBAction)onAllergies;

-(void) checkNetworkStatus:(NSNotification *)notice;
@end
