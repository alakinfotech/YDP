//
//  YDPCarePlanDetailViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/11/13.
//
//

#import "YDPCarePlanDetailViewController.h"

@interface YDPCarePlanDetailViewController ()

@end

@implementation YDPCarePlanDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.tableView.dataSource = self;
    self.tableView.delegate = self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)onBack {
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)viewDidUnload {
    [self setTableView:nil];
    [super viewDidUnload];
}


#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    //WARNING - Potentially incomplete method implementation.
    // Return the number of sections.
    
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    //WARNING - Incomplete method implementation.
    // Return the number of rows in the section.
    return 8;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    int lines = 1;
    switch (indexPath.row) {
        case 0:
            break;
        case 1:
            lines = 2;
            break;
        case 2:
            lines = 1;
            break;
        case 3:
            lines = 1;
            break;
        case 4:
            lines = 4;
            break;
        case 5:
            lines = 3;
            break;
        case 6:
            lines = 2;
            break;
        case 7:
            lines = 1;
            break;
        default:
            break;
    }
    return lines * 25;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *simpleTableIdentifier = @"DetailCarePlan";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    if (cell == nil){
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:simpleTableIdentifier];
    }
    NSString *recordKey = [self.detailRecord objectAtIndex:indexPath.row];
    if ([recordKey isEqualToString:@"undefined"]) {
        recordKey = @"";
    }
    int lines = 1;
    switch (indexPath.row) {
        case 0:
            recordKey = [NSString stringWithFormat:@"Date: %@",recordKey];
            break;
        case 1:
            recordKey = [NSString stringWithFormat:@"ICD9 Diagnosis: %@",recordKey];
            lines = 2;
            break;
        case 2:
            recordKey = [NSString stringWithFormat:@"Status: %@",[[recordKey lastPathComponent] stringByDeletingPathExtension]];
            lines = 1;
            break;
        case 3:
            recordKey = [NSString stringWithFormat:@"Risk Factors: %@",recordKey];
            lines = 1;
            break;
        case 4:
            recordKey = [NSString stringWithFormat:@"Goals/Instructions: %@",recordKey];
            lines = 4;
            break;
        case 5:
            recordKey = [NSString stringWithFormat:@"Interventions: %@",recordKey];
            lines = 3;
            break;
        case 6:
            recordKey = [NSString stringWithFormat:@"Medication: %@",recordKey];
            lines = 2;
            break;
        case 7:
            recordKey = [NSString stringWithFormat:@"Practitioner: %@",recordKey];
            lines = 1;
            break;
        default:
            break;
    }
    cell.textLabel.numberOfLines = lines;
    cell.textLabel.text = recordKey;
    return cell;
}

@end
