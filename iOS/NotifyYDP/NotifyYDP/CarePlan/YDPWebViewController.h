//
//  YDPWebViewController.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 7/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface YDPWebViewController : UIViewController<UIWebViewDelegate>{
        int requestid;
}
@property (strong, nonatomic) NSString *url;
@property (strong, nonatomic) IBOutlet UIWebView *webView;
@property (strong, nonatomic) NSString *userName;
@property (strong, nonatomic) NSString *password;

@property (readwrite) BOOL isAudination;


- (IBAction)Logout:(id)sender;
- (IBAction)expandAll;
@end
