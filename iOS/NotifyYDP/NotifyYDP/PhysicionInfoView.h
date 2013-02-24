//
//  PhysicionInfoView.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>

@class YDPDataModel;
@class AutoScrollLabel;

@interface PhysicionInfoView : UIViewController<MKReverseGeocoderDelegate,UITextFieldDelegate>

@property (strong, nonatomic) IBOutlet UISegmentedControl *segmentControl;
@property (strong, nonatomic) MKReverseGeocoder	*reverseGeocoder;
@property (strong, nonatomic) IBOutlet MKMapView *mapView;
@property (strong, nonatomic) IBOutlet UIView *formView;
@property (strong, nonatomic) YDPDataModel *dataModel;

@property (strong, nonatomic) IBOutlet UITextField *firstName;
@property (strong, nonatomic) IBOutlet UITextField *lastName;
@property (strong, nonatomic) IBOutlet UITextField *phoneNumber;
@property (strong, nonatomic) IBOutlet UITextField *address;
@property (strong, nonatomic) IBOutlet AutoScrollLabel *currentLocation;

- (IBAction)OnPatientValueChange:(UISegmentedControl *)sender;
- (IBAction)OnCancle:(id)sender;
- (IBAction)OnDone:(id)sender;
- (NSString *) getLocation:(double) lattitude logitude:(double) longitude;
@end
