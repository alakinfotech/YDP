//
//  YDPDataModel.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class PatientModel;
@class PhysicionModel;

@interface YDPDataModel : NSObject

@property (strong, nonatomic) PatientModel *patientModel;
@property (strong, nonatomic) PhysicionModel *physicionModel;

+ (id)sharedDataModel;
@end
