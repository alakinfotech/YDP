//
//  UITabBarController+autorotate.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/18/13.
//
//

#import "UITabBarController+autorotate.h"

@implementation UITabBarController (autorotate)

- (NSUInteger)supportedInterfaceOrientations{
    return UIInterfaceOrientationMaskPortrait;
}
@end
