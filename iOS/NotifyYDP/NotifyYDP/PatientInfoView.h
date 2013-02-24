//
//  PatientInfoView.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Cipher.h"
@class YDPDataModel;

@interface PatientInfoView : UIViewController< ZBarReaderViewDelegate,UITextFieldDelegate >{
    ZBarReaderView *scanView;
    ZBarCameraSimulator *cameraSim;
    Cipher *cipherObj;
}
@property (strong, nonatomic) IBOutlet ZBarReaderView *scanView;
@property (strong, nonatomic) ZBarCameraSimulator *cameraSim;
@property (strong, nonatomic) IBOutlet UIView *formView;
@property (strong, nonatomic) IBOutlet UISegmentedControl *segmentControl;
@property (strong, nonatomic) YDPDataModel *dataModel;
@property (strong, nonatomic) NSString *resultText;
@property (strong, nonatomic) IBOutlet UITextField *firstName;
@property (strong, nonatomic) IBOutlet UITextField *lastName;
@property (strong, nonatomic) IBOutlet UITextField *phoneNumber;
@property (strong, nonatomic) IBOutlet UITextField *patientID;
- (void) cleanup;
- (IBAction)OnCancle:(id)sender;
- (IBAction)OnPatientValueChange:(id)sender;
- (IBAction)OnDone:(id)sender;
@end
