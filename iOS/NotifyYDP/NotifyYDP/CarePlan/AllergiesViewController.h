//
//  AllergiesViewController.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/21/13.
//
//

#import <UIKit/UIKit.h>

@interface AllergiesViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) NSMutableDictionary *allergies;
@property (strong, nonatomic) NSMutableArray *allergiesRecoed;

- (IBAction)onBack;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UILabel *userID;

@end
