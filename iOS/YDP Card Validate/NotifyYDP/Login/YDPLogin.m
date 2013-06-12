//
//  YDPCarePlan.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/23/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPLogin.h"
#import "NSData+Base64.h"

@implementation YDPLogin
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
    
    
    cipherObj=[[Cipher alloc]initWithKey:@"Adi&Revanth"];
    
    //self.webView = [[UIWebView alloc]init];
    
    self.webView.scalesPageToFit = YES;
    self.webView.autoresizesSubviews = YES;
    self.webView.autoresizingMask=(UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth);
    self.webView.delegate = self;
    self.reselt.text = @"Please scan the QR code into the box above using camera";
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
    
//    [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString: @"https://yourdoctorprogram.com/qhr/Login.aspx"]]];
//    self.userName.text = @"adikadapa";
//    self.password.text = @"Medico!8";
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
    
    [self resetScanCard];
    
    NSArray *array = [[NSArray alloc]init];
    array = [resultText componentsSeparatedByString:@":"];
    
    if (array.count >= 11) {
        
        if (([[array[0] stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] isEqualToString:@"FirstName"]) && ([[array[2] stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]isEqualToString: @"LastName"])) {
        
            NSString *userName = [[array objectAtIndex:8] stringByTrimmingCharactersInSet:
                                  [NSCharacterSet whitespaceAndNewlineCharacterSet]];
            
            NSString *password = [array objectAtIndex:9];
            int passwordIndex = 10;
            while (passwordIndex < array.count - 1) {
                password = [NSString stringWithFormat:@"%@:%@",password,[array objectAtIndex:passwordIndex]];
                passwordIndex++;
            }

            //self.reselt.text = resultText;
            self.reselt.text = [NSString stringWithFormat:@"VALID CARD!\n Card belongs to:%@",userName];
            NSLog(@"UserName:%@ Passward:%@",userName,password);
            [self loginWithUserName:userName password:password];
        }
        
    }
    else{
        self.reselt.text = @"INVALID QR CODE!\nPlease Check.";
    }
        
}

- (void)loginWithUserName:(NSString *) userName  password:(NSString *) password {
    

    if ([userName isEqualToString:@""] || [password isEqualToString:@""]) {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" message:@"Please check the Username and Password" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    self.userName = userName;
    self.password = password;
    requestid = 0;
    
    
    [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString: @"https://yourdoctorprogram.com/qhr/Login.aspx"]]];

       
}

- (NSString*) getDocumentTitle
{
    return [self.webView stringByEvaluatingJavaScriptFromString:@"document.title"];
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType
{
    
    
    return YES;
}

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    
    if (requestid == 0) {
        [(YDPQRValidateDelegate*)[[UIApplication sharedApplication] delegate] startSpinningLoaderWithMessage:@"Loading..."];
    }
    
}
- (void)webViewDidFinishLoad:(UIWebView *)webview
{

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
            
            NSLog(@"%@",[self getDocumentTitle]);
            [(YDPQRValidateDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
            if ([[self getDocumentTitle] isEqualToString:@"Patient Main" ]) {
                // Load the JavaScript code from the Resources and inject it into the web page
                NSBundle *thisBundle = [NSBundle mainBundle];
                NSString *path = [thisBundle  pathForResource:@"YDPWebUtil" ofType:@"js"];
                NSString *jsCode = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:nil];
                
                [self.webView stringByEvaluatingJavaScriptFromString:jsCode];
                
                NSString *qhrUserName = [NSString stringWithFormat:@"%@",[self.webView stringByEvaluatingJavaScriptFromString:@"getYDPUserName()"]];
                
                if ([qhrUserName isEqualToString:self.userName]) {
                    self.reselt.text = [NSString stringWithFormat:@"%@\nName on QHR:%@ \n\n Sucessfull Login",self.reselt.text,qhrUserName];
                }
                else{
                    self.reselt.text = [NSString stringWithFormat:@"%@\nName on QHR:%@ \nNames Mismatch \n\n Sucessfull Login",self.reselt.text,qhrUserName];
                }
            }
            else{
                self.reselt.text = [NSString stringWithFormat:@"%@ \nLogin Failed\nPlease check login credentials",self.reselt.text];
                
            }
            
            requestid ++;
            
        }
    
}
- (IBAction)resetScanCard {
    
    self.reselt.text = @"";
//    self.webView = nil;
//    self.webView = [[UIWebView alloc]init];
//    self.webView.delegate = self;
    self.reselt.text = @"Please scan the QR code into the box above using camera";
}
@end
