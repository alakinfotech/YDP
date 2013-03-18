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
    self.webView = [[UIWebView alloc]init];
    [self.webView  loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:self.url]]];
    
    self.carePlan = [NSMutableDictionary dictionary];
    self.carePlanRecoed = [NSMutableArray array];
    
    self.tabelView.dataSource = self;
    self.tabelView.delegate = self;
    
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
            NSString *responce = [self.webView stringByEvaluatingJavaScriptFromString:@"getCarePlan()"];
            
                        
            NSArray *array = [[NSArray alloc] init];
            array = [responce componentsSeparatedByString:@":$#"];

            for (int i =0; i < array.count - 1;i++) {
                
                NSArray *carePlanrecord = [NSArray arrayWithObjects:[array objectAtIndex:i],[array objectAtIndex:i+1],[array objectAtIndex:i+2],[array objectAtIndex:i+3],[array objectAtIndex:i+4],[array objectAtIndex:i+5],[array objectAtIndex:i+6],[array objectAtIndex:i+7],[array objectAtIndex:i+8], nil];
                if (self.carePlan == nil) {
                    self.carePlan = [NSMutableDictionary dictionary];
                }
                [self.carePlan setObject:carePlanrecord forKey:array[i+1]];
                [self.carePlanRecoed addObject:array[i+1]];
                i += 8;
            }
            if (self.carePlanRecoed.count > 0) {
                self.loadingView.hidden = YES;
                [self.tabelView reloadData];
            }
            else
                [self Logout:nil];

            requestid ++;
        
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

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    return @"ICD9 Diagnosis";
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    //WARNING - Incomplete method implementation.
    // Return the number of rows in the section.
    return self.carePlanRecoed.count;
}

//- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
//    return 60;
//}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *simpleTableIdentifier = @"CarePlan";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    if (cell == nil){
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:simpleTableIdentifier];
    }
    NSString *recordKey = [self.carePlanRecoed objectAtIndex:indexPath.row];
    cell.textLabel.text = recordKey;
    NSArray *record = [self.carePlan objectForKey:recordKey];
    NSString *status = [record objectAtIndex:2];
    status = [status lastPathComponent];
    cell.detailTextLabel.text = [record objectAtIndex:0];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    cell.imageView.image = [UIImage imageNamed:status];
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
    NSString *recordKey = [self.carePlanRecoed objectAtIndex:indexPath.row];
    NSArray *record = [self.carePlan objectForKey:recordKey];
    
    carePlanDetailViewController.detailRecord = record;
    [self.navigationController pushViewController:carePlanDetailViewController animated:YES];
}

@end
