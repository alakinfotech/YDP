//
//  AllergiesViewController.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/21/13.
//
//

#import "AllergiesViewController.h"
#import "AllergiesCell.h"

@interface AllergiesViewController ()

@end

@implementation AllergiesViewController

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
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)onBack {
    [self.navigationController popViewControllerAnimated:YES];
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
    
    return self.allergiesRecoed.count;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
	
    UIView *sectionHeaderImg = [[UIView alloc]init];
    sectionHeaderImg.backgroundColor = [UIColor grayColor];
    UILabel *nameHeaderLable = [UIAppDelegate getLabelWithFrame:CGRectMake(11, 3, 104, 20) WithText:@"Allergi"];
    nameHeaderLable.textAlignment = NSTextAlignmentLeft;
    [sectionHeaderImg addSubview:nameHeaderLable];
    
    UILabel *typeHeaderLable1 = [UIAppDelegate getLabelWithFrame:CGRectMake(135, 3, 124, 20) WithText:@"Reaction"];
    typeHeaderLable1.textAlignment = NSTextAlignmentLeft;
    [sectionHeaderImg addSubview:typeHeaderLable1];
    
    
    UILabel *typeHeaderLable2 = [UIAppDelegate getLabelWithFrame:CGRectMake(279, 3, 100, 20) WithText:@"Severity"];
    typeHeaderLable2.textAlignment = NSTextAlignmentLeft;
    [sectionHeaderImg addSubview:typeHeaderLable2];
    
    
    UILabel *typeHeaderLable3 = [UIAppDelegate getLabelWithFrame:CGRectMake(401, 3, 74, 20) WithText:@"Status"];
    typeHeaderLable3.textAlignment = NSTextAlignmentLeft;
    [sectionHeaderImg addSubview:typeHeaderLable3];
    
    
    return sectionHeaderImg;
}


- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 30;
}



- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    CGSize textSize = { 104, 20000.0f };		// width and height of text area
    NSString *recordKey = [self.allergiesRecoed objectAtIndex:indexPath.row];
    NSArray *record = [self.allergies objectForKey:recordKey];
    
    NSString *value = record[0];
	CGSize size = [value sizeWithFont:[UIFont fontWithName:@"Courier New" size:14] constrainedToSize:textSize lineBreakMode:UILineBreakModeWordWrap];
    
    return size.height + 10 ;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    static NSString *MyIdentifier = @"AllergiesCustumCell";
	
	AllergiesCell *cell = (AllergiesCell *)[tableView dequeueReusableCellWithIdentifier:MyIdentifier];
	
	if(cell == nil)	{
		NSArray *topLevelObjects = [[NSBundle mainBundle] loadNibNamed:@"AllergiesCell" owner:nil options:nil];
		
		for(id currentObject in topLevelObjects) {
			if([currentObject isKindOfClass:[AllergiesCell class]]) {
				cell = (AllergiesCell *)currentObject;
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
    
    NSString *recordKey = [self.allergiesRecoed objectAtIndex:indexPath.row];
    NSArray *record = [self.allergies objectForKey:recordKey];
    
    cell.allergi.text = record[0];
    cell.reaction.text = record[1];
    cell.severity.text = record[2];
    cell.status.text = record[3];
    
    return cell;
}

@end