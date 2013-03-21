//
//  AllergiesCell.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/21/13.
//
//

#import <UIKit/UIKit.h>

@interface AllergiesCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UILabel *allergi;
@property (strong, nonatomic) IBOutlet UILabel *reaction;
@property (strong, nonatomic) IBOutlet UILabel *severity;
@property (strong, nonatomic) IBOutlet UILabel *status;
@end
