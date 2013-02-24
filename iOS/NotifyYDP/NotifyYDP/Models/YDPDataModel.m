//
//  YDPDataModel.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "YDPDataModel.h"

static YDPDataModel *ydpDataModel = nil;

@implementation YDPDataModel
@synthesize patientModel;
@synthesize physicionModel;


+ (id)sharedDataModel {
    @synchronized(self) {
        if (ydpDataModel == nil){
            ydpDataModel = [[self alloc] init];
            ydpDataModel.patientModel = [[PatientModel alloc]init];
            ydpDataModel.physicionModel = [[PhysicionModel alloc]init];
            
        }
    }
    return ydpDataModel;
}

@end
