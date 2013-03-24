//
//  YDPCarePlanDetailViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/11/13.
//
//

#import "YDPCarePlanDetailViewController.h"
#import "YDPCarePlanDetailCell.h"

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
    
    self.userID.text = self.title;
    
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
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
    [self setUserID:nil];
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

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
	
    UIImageView *sectionHeaderImg = [[UIImageView alloc]initWithImage:[UIImage imageNamed:@"header_bar.jpg"]];
    UILabel *nameHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(11, 3, 72, 22) WithText:@"Condition"];
    nameHeaderLable.textAlignment = NSTextAlignmentLeft;
    [sectionHeaderImg addSubview:nameHeaderLable];
    
    UILabel *typeHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(172, 3, 299, 20) WithText:self.detailRecord[1]];
    typeHeaderLable.textAlignment = NSTextAlignmentLeft;
    [sectionHeaderImg addSubview:typeHeaderLable];
    
    
    return sectionHeaderImg;
}


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 30;
}



- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    CGSize textSize = { 299, 20000.0f };		// width and height of text area
    NSString *value = [self.detailRecord objectAtIndex:indexPath.row];
	CGSize size = [value sizeWithFont:[UIFont fontWithName:@"Courier New" size:14] constrainedToSize:textSize lineBreakMode:UILineBreakModeWordWrap];
    
    return size.height + 10 ;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *MyIdentifier = @"DetailCarePlan";
	
	YDPCarePlanDetailCell *cell = (YDPCarePlanDetailCell *)[tableView dequeueReusableCellWithIdentifier:MyIdentifier];
	
	if(cell == nil)	{
		NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"YDPCarePlanDetailCell" owner:nil options:nil];
		
		for(id currentObject in topLevelObjects) {
			if([currentObject isKindOfClass:[YDPCarePlanDetailCell class]]) {
				cell = (YDPCarePlanDetailCell *)currentObject;
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
    
    
    NSString *recordKey = [self.detailRecord objectAtIndex:indexPath.row];
    if ([recordKey isEqualToString:@"undefined"]) {
        recordKey = @"";
    }
    
    NSString *field = @"";
    int lines = 1;
    switch (indexPath.row) {
        case 0:
            field = @"Date:";
            break;
        case 1:
            field = @"ICD9 Diagnosis:";
            lines = 2;
            break;
        case 2:
            field = @"Status:";
            lines = 1;
            break;
        case 3:
            field = @"Risk Factors:";
            lines = 1;
            break;
        case 4:
            field = @"Goals/Instructions:";
            lines = 4;
            break;
        case 5:
            field = @"Interventions:";
            lines = 3;
            break;
        case 6:
            field = @"Medication:";
            lines = 2;
            break;
        case 7:
            field = @"Practitioner:";
            lines = 1;
            break;
        default:
            break;
    }
    cell.field.text = field;
    cell.value.numberOfLines = lines;
    cell.value.text = recordKey;
    return cell;
}

@end
