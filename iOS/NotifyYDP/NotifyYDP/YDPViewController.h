//
//  YDPViewController.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MFMailComposeViewController.h>

@class PatientInfoView;
@class PhysicionInfoView;

@interface YDPViewController : UIViewController<MFMailComposeViewControllerDelegate>

-(IBAction)showComposer:(id)sender;
- (IBAction)OnCallYDP:(id)sender;
- (IBAction)moreAboutYDP:(UIButton *)sender;

@property (strong, nonatomic) UIViewController *avoutViewController;
@end
