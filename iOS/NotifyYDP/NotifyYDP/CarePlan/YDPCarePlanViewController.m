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
    webView.autoresizesSubviews = YES;
    webView.autoresizingMask=(UIViewAutoresizingFlexibleHeight | UIViewAutoresizingFlexibleWidth);
    
    self.webView.delegate = self;
    requestid = 0;

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
            
            NSBundle *thisBundle = [NSBundle mainBundle];
            // Load the JavaScript code from the Resources and inject it into the web page
            NSString *path = [thisBundle  pathForResource:@"YDPWebUtil" ofType:@"js"];
            NSString *jsCode = [NSString stringWithContentsOfFile:path encoding:NSUTF8StringEncoding error:nil];
            
            [self.webView stringByEvaluatingJavaScriptFromString:jsCode];
            //Grabbing the data from the page
            NSString *responce = [webView stringByEvaluatingJavaScriptFromString:@"getCarePlan()"];
            
                        
            NSArray *array = [[NSArray alloc] init];
            array = [responce componentsSeparatedByString:@":$#"];

            for (int i =0; i < array.count - 1;i++) {
                
                NSString *status = [array objectAtIndex:i+2];
                status = [NSString stringWithFormat:@"%@",[[status lastPathComponent] stringByDeletingPathExtension]];
                
                NSArray *carePlanrecord = [NSArray arrayWithObjects:[array objectAtIndex:i],[array objectAtIndex:i+1],status,[array objectAtIndex:i+3],[array objectAtIndex:i+4],[array objectAtIndex:i+5],[array objectAtIndex:i+6],[array objectAtIndex:i+7],[array objectAtIndex:i+8], nil];
                
                if (self.carePlan == nil) {
                    self.carePlan = [NSMutableDictionary dictionary];
                }
                [self.carePlan setObject:carePlanrecord forKey:array[i+1]];
                [self.carePlanRecoed addObject:array[i+1]];
                i += 8;
            }
            if (self.carePlanRecoed.count > 0) {
                
                self.userID.text = [webView stringByEvaluatingJavaScriptFromString:@"getYDPUserName()"];
                
                NSString *allergiesURL = @"https://yourdoctorprogram.com/qhr/CareDashboard/AllergiesEditorMaster.aspx";
                [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:allergiesURL]]];
                
            }
            else
                [self Logout:nil];
            
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
             
             for (int i =0; i < array.count - 1;i++) {
                 
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
             
             
        }
    }
    
    [(YDPAppDelegate*)[[UIApplication sharedApplication] delegate]  stopSpinningLoader];
    
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    //WARNING - Potentially incomplete method implementation.
    // Return the number of sections.
    
    return 1;
}


- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
	
    UIView *sectionHeaderImg = [[UIView alloc]init];
    sectionHeaderImg.backgroundColor = [UIColor grayColor];
    UILabel *nameHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(11, 3, 72, 22) WithText:@"Condition"];
    [sectionHeaderImg addSubview:nameHeaderLable];
    
    UILabel *typeHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(170, 3, 90, 20) WithText:@"Medications"];
    [sectionHeaderImg addSubview:typeHeaderLable];
    
    UILabel *dateHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(360, 3, 64, 20) WithText:@"Provider"];
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
    return 50;
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
    
    NSString *recordValue = record[1];
    if ([recordValue isEqualToString:@"undefined"]) {
        recordValue = @"";
    }
    cell.condition.text = recordValue;
    recordValue = record[6];
    if ([recordValue isEqualToString:@"undefined"]) {
        recordValue = @"";
    }
    
    cell.medications.text = recordValue;
    
    recordValue = record[7];
    if ([recordValue isEqualToString:@"undefined"]) {
        recordValue = @"";
    }
    
    cell.provider.text = recordValue;
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

@end
