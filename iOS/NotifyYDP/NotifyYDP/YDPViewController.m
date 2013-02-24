//
//  YDPViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPViewController.h"
#import "YDPDataModel.h"
#import "PatientModel.h"
#import "PhysicionModel.h"
#import "YDPWebViewController.h"

@implementation YDPViewController

@synthesize avoutViewController;
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    //UITabBarItem *item = [[UITabBarItem alloc]initWithTitle:@"Notify YDP" image:nil tag:0];
    //self.tabBarItem = item;
    self.tabBarController.selectedIndex = 0;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return NO;//(interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}


// Displays an email composition interface inside the application. Populates all the Mail fields. 
- (void) displayComposerSheet:(NSString *)body {
    
    
	MFMailComposeViewController *tempMailCompose = [[MFMailComposeViewController alloc] init];
    
	tempMailCompose.mailComposeDelegate = self;
    
    YDPDataModel *dataModel = [YDPDataModel sharedDataModel];
	[tempMailCompose setToRecipients:[NSArray arrayWithObject:@"help@yourdoctorprogram.com"]];
	//[tempMailCompose setCcRecipients:[NSArray arrayWithObject:@"ipad@me.com"]];
    //NSString *subject = [NSString stringWithFormat:@"Patient: %@ %@ Notifaction to YDP",dataModel.patientModel.firstName,dataModel.patientModel.lastName];
	[tempMailCompose setSubject:@"YDP Patient Encounter Outside Medical Home"];
    
    NSString *strBody = [NSString stringWithFormat:@"YDP Admin,\nA patient had a visit outside YDP network and wanted to notify YDP about this. Below are the details:\nPatient Info\n=====================\n   First Name:%@\n   Last Name:%@\n   PhoneNumber:%@\n   Patient ID:%@\n\nPhysician Info\n=====================\n First Name:%@\n   Last Name:%@\n   PhoneNumber:%@\n   Address:%@",dataModel.patientModel.firstName,dataModel.patientModel.lastName,dataModel.patientModel.phoneNumber,dataModel.patientModel.patientID,dataModel.physicionModel.firstName,dataModel.physicionModel.lastName,dataModel.physicionModel.phoneNumber,dataModel.physicionModel.address];
    
    //[tempMailCompose setMessageBody: isHTML:<#(BOOL)#>
	[tempMailCompose setMessageBody:strBody isHTML:NO];
    
	[self presentModalViewController:tempMailCompose animated:YES];
    //[tempMailCompose release];
}

// Dismisses the email composition interface when users tap Cancel or Send. Proceeds to update the message field with the result of the operation.
- (void)mailComposeController:(MFMailComposeViewController*)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError*)error {	
	// Notifies users about errors associated with the interface
	switch (result)
	{
		case MFMailComposeResultCancelled:
			NSLog(@"Result: canceled");
			break;
		case MFMailComposeResultSaved:
			NSLog(@"Result: saved");
			break;
		case MFMailComposeResultSent:
			NSLog(@"Result: sent");
			break;
		case MFMailComposeResultFailed:
			NSLog(@"Result: failed");
			break;
		default:
			NSLog(@"Result: not sent");
			break;
	}
	[self dismissModalViewControllerAnimated:YES];
}

// Launches the Mail application on the device. Workaround
-(void)launchMailAppOnDevice:(NSString *)body{
	NSString *recipients = [NSString stringWithFormat:@"mailto:%@?subject=%@", @"test@mail.com", @"iPhone App recommendation"];
	NSString *mailBody = [NSString stringWithFormat:@"&body=%@", body];
    
	NSString *email = [NSString stringWithFormat:@"%@%@", recipients, mailBody];
	email = [email stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
	[[UIApplication sharedApplication] openURL:[NSURL URLWithString:email]];
}

// Call this method and pass parameters
-(void) showComposer:(id)sender{
	Class mailClass = (NSClassFromString(@"MFMailComposeViewController"));
	if (mailClass != nil){
		// We must always check whether the current device is configured for sending emails
		if ([mailClass canSendMail]){
			[self displayComposerSheet:sender];
		}else{
			[self launchMailAppOnDevice:sender];
		}
	}else{
		[self launchMailAppOnDevice:sender];
	}
}

- (IBAction)OnCallYDP:(id)sender {
    NSURL *phoneNumberURL = [NSURL URLWithString:@"tel:7139816125"];
    
    [[UIApplication sharedApplication] openURL:phoneNumberURL];
}

- (IBAction)moreAboutYDP:(UIButton *)sender {
    
//    self.avoutViewController = [[UIViewController alloc]init];
//    UIWebView *myWebView = [[UIWebView alloc]init];
//    self.avoutViewController.view.frame = myWebView.frame = self.view.frame;
//    [self.avoutViewController.view addSubview:myWebView];
//    [myWebView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@
//                                                                  "https://yourdoctorprogram.com"]]];
//    myWebView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
//    
//    self.avoutViewController.view.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
//    UIButton *btn= [UIButton buttonWithType:UIButtonTypeRoundedRect];
//    btn.frame = CGRectMake(250, 6, 65, 25);
//    btn.backgroundColor = [UIColor clearColor];
//    [btn setTitle:@"Done" forState:UIControlStateNormal];
//    [btn addTarget:self action:@selector(doneClick) forControlEvents:UIControlEventTouchUpInside];
//    [myWebView addSubview:btn]; 
//    
//    [self presentViewController:self.avoutViewController animated:YES completion:nil];

    
    YDPWebViewController *webViewController = [[YDPWebViewController alloc]init];
    webViewController.url = @
    "https://yourdoctorprogram.com";
    webViewController.isAudination = NO;
    [self presentModalViewController:webViewController animated:YES];
    
    //self.avoutViewController.view.frame = myWebView.frame = self.view.frame;
}

-(void) doneClick{
    [self.avoutViewController dismissModalViewControllerAnimated:YES];
}

@end
