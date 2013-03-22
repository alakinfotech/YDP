//
//  UINavigationController+autorotate.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 3/22/13.
//
//

#import "UINavigationController+autorotate.h"

@implementation UINavigationController (autorotate)
- (NSUInteger)supportedInterfaceOrientations{
    return UIInterfaceOrientationMaskLandscape;
}
@end
