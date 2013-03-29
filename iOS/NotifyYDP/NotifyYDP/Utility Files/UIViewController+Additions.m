    //
//  UIViewController+Additions.m
//  TimeSheetManagement
//
//  Created by Ganesh Korada on 06/05/11.
//  Copyright 2011 Innominds. All rights reserved.
//

#import "UIViewController+Additions.h"

@implementation UIViewController (Additions)

- (UILabel *)navigationItemTitleView {
	UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 200, 44)];
    label.font = [UIFont boldSystemFontOfSize:17];
    label.textColor = [UIColor whiteColor];
    label.backgroundColor = [UIColor clearColor];
    label.textAlignment = UITextAlignmentCenter;
    
    self.navigationItem.titleView = label;
    return label;
}

- (void)addBackBarButtonItem {	
	UIButton *backButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [backButton setTitle:@"  Back" forState:UIControlStateNormal];
    [backButton setTintColor:[UIColor whiteColor]];
    backButton.titleLabel.font = [UIFont boldSystemFontOfSize:13];
    backButton.frame = CGRectMake(0, 0, 60, 30);
    [backButton setBackgroundImage:[[UIImage imageNamed:@"back_button.png"] stretchableImageWithLeftCapWidth:20 topCapHeight:0]forState:UIControlStateNormal];
    [backButton addTarget:self action:@selector(back:) forControlEvents:UIControlEventTouchUpInside];
    
	self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:backButton];
}

- (IBAction)back:(id)sender {
	[self.navigationController popViewControllerAnimated:YES];
}

@end
