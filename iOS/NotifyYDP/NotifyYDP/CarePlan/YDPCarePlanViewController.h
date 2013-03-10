//
//  YDPWebViewController.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 7/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YDPCarePlanViewController : UIViewController<UIWebViewDelegate,UITableViewDataSource,UITableViewDelegate>{
        int requestid;
}
@property (strong, nonatomic) NSString *url;
@property (strong, nonatomic) UIWebView *webView;
@property (strong, nonatomic) NSString *userName;
@property (strong, nonatomic) NSString *password;
@property (strong, nonatomic) NSMutableDictionary *carePlan;
@property (strong, nonatomic) NSMutableArray *carePlanRecoed;

@property (readwrite) BOOL isAudination;
@property (strong, nonatomic) IBOutlet UITableView *tabelView;

- (IBAction)Logout:(id)sender;

@end
