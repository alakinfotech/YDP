//
//  YDPCarePlanCell.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/21/13.
//
//

#import <UIKit/UIKit.h>

@interface YDPCarePlanCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UILabel *condition;
@property (strong, nonatomic) IBOutlet UILabel *medications;
@property (strong, nonatomic) IBOutlet UILabel *provider;
@end
