//
//  YDPCarePlanDetailViewController.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/11/13.
//
//

#import <UIKit/UIKit.h>

@interface YDPCarePlanDetailViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) NSArray *detailRecord;
- (IBAction)onBack;
@property (strong, nonatomic) IBOutlet UITableView *tableView;
@end
