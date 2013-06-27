//
//  YDPWebViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 7/1/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPCarePlanViewController.h"
#import "YDPAppDelegate.h"
#import "YDPCarePlanDetailViewController.h"
#import "YDPCarePlanCell.h"
#import "AllergiesViewController.h"
#import "YDPReachability.h"

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
    
    self.carePlan = [NSMutableDictionary dictionary];
    self.carePlanRecoed = [NSMutableArray array];
    
    self.allergies = [NSMutableDictionary dictionary];
    self.allergiesRecoed = [NSMutableArray array];
    
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
    
    self.loadingView.hidden = NO;
    
    webView.scalesPageToFit = YES;
    //webView.autoresizesSubviews = YES;
    webView.autoresizingMask=(UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth);
    
    self.webView.delegate = self;
    
    bool viewMode = [[NSUserDefaults standardUserDefaults] boolForKey:@"ViewMode"];
    self.webView.hidden = !viewMode;
    requestid = 0;
    
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];

}

- (void)viewDidUnload
{
    [self setWebView:nil];
    [self setUserID:nil];
    [self setAllergiesList:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

-(void) viewWillAppear:(BOOL)animated
{
    // check for internet connection
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(checkNetworkStatus:) name:kReachabilityChangedNotification object:nil];
    
    internetReachable = [[YDPReachability reachabilityForInternetConnection] retain];
    [internetReachable startNotifier];
    
    // check if a pathway to a random host exists
    hostReachable = [[YDPReachability reachabilityWithHostName: @"www.apple.com"] retain];
    [hostReachable startNotifier];
    
    // now patiently wait for the notification
    
    
}

-(void) viewWillDisappear:(BOOL)animated
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return YES;//(interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)Logout:(id)sender {
    [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
//    [self dismissModalViewControllerAnimated:YES];
    
    UIActionSheet *actionMenu = [[UIActionSheet alloc]
                             initWithTitle:@"Select Option"
                             delegate:self
                             cancelButtonTitle:nil
                             destructiveButtonTitle:nil
                             otherButtonTitles:nil];
    
    [actionMenu addButtonWithTitle:@"Logout"];
    if (webView.hidden) {
        [actionMenu addButtonWithTitle:@"Details"];
    }
    else{
        [actionMenu addButtonWithTitle:@"Summary"];
    }
    
    
    actionMenu.cancelButtonIndex = [actionMenu addButtonWithTitle: @"Cancel"];
    
    [actionMenu showInView:self.view];
    
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

    NetworkStatus internetStatus = [internetReachable currentReachabilityStatus];
    if(internetStatus == NotReachable){
        NSLog(@"The internet is down. Yes");
        [self dismissModalViewControllerAnimated:YES];
    }
    else if (requestid == 0) {
        [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate] startSpinningLoaderWithMessage:@"Loading..."];
    }

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
            
            
            
            NSBundle *thisBundle = [NSBundle mainBundle];
            // Load the JavaScript code from the Resources and inject it into the web page
            NSString *path = [thisBundle  pathForResource:@"YDPWebUtil" ofType:@"js"];
            NSString *jsCode = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:nil];
            
            [self.webView stringByEvaluatingJavaScriptFromString:jsCode];
            //Grabbing the data from the page
            NSString *responce = [webView stringByEvaluatingJavaScriptFromString:@"getCarePlan()"];
            
                        
            NSArray *array = [[NSArray alloc] init];
            array = [responce componentsSeparatedByString:@":$#"];
            int recordCount = 0;
            for (int i =0; (i+9) < array.count - 1;i++) {
                
                NSString *status = [array objectAtIndex:i+3];
                status = [NSString stringWithFormat:@"%@",[[status lastPathComponent] stringByDeletingPathExtension]];
                
                NSArray *carePlanrecord = [NSArray arrayWithObjects:[array objectAtIndex:i+1],[array objectAtIndex:i+2],status,[array objectAtIndex:i+4],[array objectAtIndex:i+5],[array objectAtIndex:i+6],[array objectAtIndex:i+7],[array objectAtIndex:i+8], [array objectAtIndex:i +9],nil];
                
                if (self.carePlan == nil) {
                    self.carePlan = [NSMutableDictionary dictionary];
                }
                [self.carePlan setObject:carePlanrecord forKey:array[i+2]];
                [self.carePlanRecoed addObject:array[i+2]];
                i += 9;
            }
            if (self.carePlanRecoed.count > 0) {
                
                NSString *title = [NSString stringWithFormat:@"%@’s Careplan",[webView stringByEvaluatingJavaScriptFromString:@"getYDPUserName()"]];
                self.userID.text = title;
                
                NSString *allergiesURL = @"https://yourdoctorprogram.com/qhr/CareDashboard/AllergiesEditorMaster.aspx";
                [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:allergiesURL]]];
                
            }
            else{
                
                [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
                [self dismissModalViewControllerAnimated:YES];
                UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Error" message:@"Invalid Username or Password" delegate:self cancelButtonTitle:@"Ok" otherButtonTitles:nil, nil];
                [alert show];
            }
            
            
            requestid ++;
        
        }
        else{
            
            NSBundle *thisBundle = [NSBundle mainBundle];
             // Load the JavaScript code from the Resources and inject it into the web page
             NSString *path = [thisBundle  pathForResource:@"YDPWebUtil" ofType:@"js"];
             NSString *jsCode = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:nil];
             
             [self.webView stringByEvaluatingJavaScriptFromString:jsCode];
             //Grabbing the data from the page
             NSString *responce = [webView stringByEvaluatingJavaScriptFromString:@"getAllergies()"];
             
             if (responce == nil) {
                 return;
             }
             
             NSArray *array = [[NSArray alloc] init];
             array = [responce componentsSeparatedByString:@":$#"];
             
             for (int i =0; (i+3) < array.count - 1;i++) {
                 
                 NSArray *allergies = [NSArray arrayWithObjects:[array objectAtIndex:i],[array objectAtIndex:i+1],[array objectAtIndex:i+2],[array objectAtIndex:i+3], nil];
                 if (self.allergies == nil) {
                     self.allergies = [NSMutableDictionary dictionary];
                 }
                 [self.allergies setObject:allergies forKey:array[i]];
                 [self.allergiesRecoed addObject:array[i]];
                 NSString *allergyString = [allergies objectAtIndex:0];
                 
                 allergyString = [NSString stringWithFormat:@"%@ %@,",self.allergiesList.text,allergyString];
                 self.allergiesList.text = allergyString;
                 
                 i += 3;
             }
             
             self.loadingView.hidden = YES;
             [self.tableView reloadData];
            
            [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
            
            NSString *allergiesURL = @"https://yourdoctorprogram.com/qhr/Patients/PatientMainForPatient.aspx?logtype=login";
            [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:allergiesURL]]];
            
            self.webView.delegate = nil;
            
             
        }
    }
    
    
    
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    //WARNING - Potentially incomplete method implementation.
    // Return the number of sections.
    
    return 1;
}


- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
	
   UIImageView *sectionHeaderImg = [[UIImageView alloc]initWithImage:[UIImage imageNamed:@"header_bar.jpg"]];
    
    UILabel *nameHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(11, 3, 72, 22) WithText:@"Condition"];
    [sectionHeaderImg addSubview:nameHeaderLable];
    
    UILabel *typeHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(170, 3, 90, 20) WithText:@"Medications"];
    [sectionHeaderImg addSubview:typeHeaderLable];
    
    UILabel *dateHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(360, 3, 90, 20) WithText:@"Practitioner"];
    [sectionHeaderImg addSubview:dateHeaderLable];
    
    return sectionHeaderImg;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    //WARNING - Incomplete method implementation.
    // Return the number of rows in the section.
    return self.carePlanRecoed.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 30;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 40;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *MyIdentifier = @"CarePlan";
	
	YDPCarePlanCell *cell = (YDPCarePlanCell *)[tableView dequeueReusableCellWithIdentifier:MyIdentifier];
	
	if(cell == nil)	{
		NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"YDPCarePlanCell" owner:nil options:nil];
		
		for(id currentObject in topLevelObjects) {
			if([currentObject isKindOfClass:[YDPCarePlanCell class]]) {
				cell = (YDPCarePlanCell *)currentObject;
				break;
			}
		}
	}
    
    // Configure the cell...
    if (indexPath.row %2 == 0) {
        cell.backgroundView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"snippet_3.jpg"]];
        
    } else {
        cell.backgroundView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"snippet_2.jpg"]];
        
    }
    
    NSString *recordKey = [self.carePlanRecoed objectAtIndex:indexPath.row];
    NSArray *record = [self.carePlan objectForKey:recordKey];
    
    NSString *status = record[2];
    UIColor *textColor = [UIColor lightGrayColor];
    if ([status isEqualToString:@"Active"]) {
        textColor = [UIColor blackColor];
    }
    
    NSString *recordValue = record[1];
    if ([recordValue isEqualToString:@"undefined"]) {
        recordValue = @"";
    }
    cell.condition.text = recordValue;
    cell.condition.textColor = textColor;
    recordValue = record[6];
    if ([recordValue isEqualToString:@"undefined"]) {
        recordValue = @"";
    }
    
    cell.medications.text = recordValue;
    cell.medications.textColor = textColor;
    
    recordValue = record[7];
    if ([recordValue isEqualToString:@"undefined"]) {
        recordValue = @"";
    }
    
    cell.provider.text = recordValue;
    cell.provider.textColor = textColor;
    
    return cell;
}



#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
    YDPCarePlanDetailViewController *carePlanDetailViewController = [[YDPCarePlanDetailViewController alloc]init];
    carePlanDetailViewController.title = self.userID.text;
    NSString *recordKey = [self.carePlanRecoed objectAtIndex:indexPath.row];
    NSArray *record = [self.carePlan objectForKey:recordKey];
    
    carePlanDetailViewController.detailRecord = record;
    [self.navigationController pushViewController:carePlanDetailViewController animated:YES];
}

- (IBAction)onAllergies {
    
    AllergiesViewController *allergiesViewController = [[AllergiesViewController alloc]init];
    allergiesViewController.title = self.userID.text;
    allergiesViewController.allergies = self.allergies;
    allergiesViewController.allergiesRecoed = self.allergiesRecoed;
    
    [self.navigationController pushViewController:allergiesViewController animated:YES];
}

#pragma mark - UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex{
    
    if (buttonIndex == 0) {
        [self dismissModalViewControllerAnimated:YES];
    }
    else if(buttonIndex == 1){
        webView.hidden = !webView.hidden;
        [[NSUserDefaults standardUserDefaults] setBool:!webView.hidden forKey:@"ViewMode"];
    }
}

-(void) checkNetworkStatus:(NSNotification *)notice
{
    // called after network status changes
    NetworkStatus internetStatus = [internetReachable currentReachabilityStatus];
    switch (internetStatus)
    {
        case NotReachable:
        {
            NSLog(@"The internet is down. Yes");
            //self.internetActive = NO;
            [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
            [self dismissModalViewControllerAnimated:YES];
            
            break;
        }
        case ReachableViaWiFi:
        {
            NSLog(@"The internet is working via WIFI.");
            //self.internetActive = YES;
            
            break;
        }
        case ReachableViaWWAN:
        {
            NSLog(@"The internet is working via WWAN.");
            //self.internetActive = YES;
            
            break;
        }
    }
    
    NetworkStatus hostStatus = [hostReachable currentReachabilityStatus];
    switch (hostStatus)
    {
        case NotReachable:
        {
            NSLog(@"A gateway to the host server is down.");
            //self.hostActive = NO;
            
            break;
        }
        case ReachableViaWiFi:
        {
            NSLog(@"A gateway to the host server is working via WIFI.");
            //self.hostActive = YES;
            
            break;
        }
        case ReachableViaWWAN:
        {
            NSLog(@"A gateway to the host server is working via WWAN.");
            //self.hostActive = YES;
            
            break;
        }
    }
}
@end
