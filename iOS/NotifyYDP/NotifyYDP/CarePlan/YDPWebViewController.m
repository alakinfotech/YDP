//
//  YDPWebViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 7/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPWebViewController.h"
#import "YDPAppDelegate.h"

@implementation YDPWebViewController
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

- (IBAction)expandAll {
    
    [self.webView stringByEvaluatingJavaScriptFromString:@"TreeView_ToggleNode(ContentPlaceHolder_MainContent_MainContent_TreeView1_Data,0,document.getElementById('ContentPlaceHolder_MainContent_MainContent_TreeView1n0'),' ',document.getElementById('ContentPlaceHolder_MainContent_MainContent_TreeView1n0Nodes'));"];
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
            
            requestid ++;
        
        }
    }
    [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
}

@end
