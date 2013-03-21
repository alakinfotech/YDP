//
//  YDPCarePlan.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPLogin.h"
#import "NSData+Base64.h"
#import "YDPCarePlanViewController.h"

@implementation YDPLogin
@synthesize userName;
@synthesize password;
@synthesize scanView;
@synthesize cameraSim;

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
    
    self.userName.delegate = self;
    self.password.delegate = self;
    
    cipherObj=[[Cipher alloc]initWithKey:@"Adi&Revanth"];
    
    self.scanView.readerDelegate = self;
    // you can use this to support the simulator
    if(TARGET_IPHONE_SIMULATOR) {
        self.cameraSim = [[ZBarCameraSimulator alloc]
                          initWithViewController: self];
        //self.cameraSim.readerView.frame = CGRectMake(20, 122, 280, 318);
        self.cameraSim.readerView = self.scanView;
    }
}

- (void)viewDidUnload
{
    [self setUserName:nil];
    [self setPassword:nil];
    [super viewDidUnload];
    self.cameraSim.readerView = nil;
    self.cameraSim = nil;
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return NO;//(interfaceOrientation == UIInterfaceOrientationPortrait);
}


-(BOOL)textFieldShouldReturn:(UITextField*)textField;
{
    NSInteger nextTag = textField.tag + 1;
    // Try to find next responder
    UIResponder* nextResponder = [textField.superview viewWithTag:nextTag];
    if (nextResponder) {
        // Found next responder, so set it.
        [nextResponder becomeFirstResponder];
    } else {
        // Not found, so remove keyboard.
        [textField resignFirstResponder];
    }
    return NO; // We do not want UITextField to insert line-breaks.
}

- (void) viewDidAppear: (BOOL) animated
{
    // run the reader when the view is visible
    [self.scanView start];
    self.userName.text = @"";
    self.password.text = @"";
    
    self.userName.text = @"adikadapa";
    self.password.text = @"Medico!8";
}

- (void) viewWillDisappear: (BOOL) animated
{
    [self.scanView stop];
}

- (void) cleanup
{
    self.cameraSim = nil;
    self.scanView.readerDelegate = nil;
    self.scanView = nil;
}

-(void)textFieldDidBeginEditing:(UITextField *)textField { //Keyboard becomes visible
   
    [textField setReturnKeyType:UIReturnKeyGo];
    
}

- (IBAction)Login {
    
    //[self.view addSubview:self.webView];
    YDPCarePlanViewController *webViewController = [[YDPCarePlanViewController alloc]init];
    webViewController.userName = self.userName.text;
    webViewController.password = self.password.text;
    webViewController.url = @
    "https://yourdoctorprogram.com/qhr/Login.aspx";
    webViewController.isAudination = YES;
    UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:webViewController];
    navigationController.navigationBar.hidden = YES;
    [self presentModalViewController:navigationController animated:YES];
    
}

- (void) readerView: (ZBarReaderView*) view
     didReadSymbols: (ZBarSymbolSet*) syms
          fromImage: (UIImage*) img
{
    NSString *resultText = nil;
    // do something useful with results
    for(ZBarSymbol *sym in syms) {
        resultText = sym.data;
        break;
    }
    
    NSData *decoded=[cipherObj decrypt:[NSData dataFromBase64String:resultText]];
    resultText = [[NSString alloc] initWithData:decoded encoding:NSUTF8StringEncoding];
    
    NSArray *array = [[NSArray alloc]init];
    array = [resultText componentsSeparatedByString:@":"];
    
    if (array.count == 11) {
        
        self.userName.text = [[array objectAtIndex:8] stringByTrimmingCharactersInSet:
                              [NSCharacterSet whitespaceAndNewlineCharacterSet]];
        self.password.text = [array objectAtIndex:9];
        NSLog(@"UserName:%@ Passward:%@",[array objectAtIndex:8],[array objectAtIndex:9]);
        [self Login];
    }
        
}

@end
