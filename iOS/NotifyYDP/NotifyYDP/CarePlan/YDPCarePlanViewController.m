//
//  YDPWebViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 7/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPCarePlanViewController.h"
#import "YDPAppDelegate.h"

@implementation YDPCarePlanViewController
@synthesize webView;
@synthesize userName;
@synthesize password;
@synthesize url;
@synthesize isAudination;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    //AppDelegate *appDelegate = [[UIApplication sharedApplication] delegate];
    //[(YDPAppDelegate*)[[UIApplication sharedApplication] delegate] startSpinningLoaderWithMessage:@"Loading..."];
    //self.webView = [[UIWebView alloc]init];
    [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:self.url]]];
    
    
    
    webView.scalesPageToFit = YES;
    webView.autoresizesSubviews = YES;
    webView.autoresizingMask=(UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth);
    
    self.webView.delegate = self;
    requestid = 0;

}

- (void)viewDidUnload
{
    [self setWebView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return YES;//(interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)Logout:(id)sender {
    [self dismissModalViewControllerAnimated:YES];
}



//- (void)didRotateFromInterfaceOrientation:(UIInterfaceOrientation)fromInterfaceOrientation
//{
//    if(fromInterfaceOrientation == UIInterfaceOrientationPortrait){
//        [webView stringByEvaluatingJavaScriptFromString:@"rotate(0)"];
//        
//    }
//    else{
//        [webView stringByEvaluatingJavaScriptFromString:@"rotate(1)"];
//    }
//}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType
{
        
    
    return YES;
}

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    
[(YDPAppDelegate*)[[UIApplication sharedApplication] delegate] startSpinningLoaderWithMessage:@"Loading..."];
}
- (void)webViewDidFinishLoad:(UIWebView *)webview
{

    if (self.isAudination == YES) {
        
    
        
        if (requestid == 0) {
        
            NSString *setqrcode = [[NSString alloc]initWithFormat:@"document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_UserName').value='%@';",self.userName]; 
            [self.webView stringByEvaluatingJavaScriptFromString:setqrcode];
            
                setqrcode = nil;
                setqrcode = [[NSString alloc]initWithFormat:@"document.getElementById('ContentPlaceHolder_ContentPlaceHolder1_txt_Password').value='%@';",self.password];

            [self.webView stringByEvaluatingJavaScriptFromString:setqrcode];
            
            [self.webView stringByEvaluatingJavaScriptFromString:@"document.getElementsByName('ctl00$ctl00$ContentPlaceHolder$ContentPlaceHolder1$bt_Login')[0].click();"];
                requestid ++;
        }
        else if (requestid == 1) {
            
            // Load the JavaScript code from the Resources and inject it into the web page
            NSString *path = [[NSBundle mainBundle] pathForResource:@"YDPWebUtil" ofType:@"js"];
            NSString *jsCode = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:nil];
            
            [self.webView stringByEvaluatingJavaScriptFromString:jsCode];
            //Grabbing the data from the page
            NSString *jsonResponce = [self.webView stringByEvaluatingJavaScriptFromString:@"getCarePlan()"];
            
//            NSString *carePlan = @"document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView')";
//            carePlan = [self.webView stringByEvaluatingJavaScriptFromString:carePlan];
            NSLog(@"Care Plan = %@",jsonResponce);
            requestid ++;
        
        }
    }
    [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
}

@end
