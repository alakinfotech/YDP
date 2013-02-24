//
//  PatientModel.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PatientModel.h"

@implementation PatientModel
@synthesize firstName;
@synthesize lastName;
@synthesize phoneNumber;
@synthesize patientID;


//#pragma mark NSCoding
//- (void)encodeWithCoder:(NSCoder *)encoder
//{
//    //[super encodeWithCoder: encoder];
//    [encoder encodeObject:firstName forKey:@"kfirstName"];
//    [encoder encodeObject:lastName forKey:@"klastName"];
//    [encoder encodeObject:phoneNumber forKey:@"kphoneNumber"];
//    [encoder encodeObject:patientID forKey:@"kpatientID"];    
//}
////--------------------------------------------------
//- (id)initWithCoder:(NSCoder *)decoder
//{
//    NSLog(@"DataModel initWithCoder: %@", decoder);
//    
//    if (self = [super init])
//    {
//        self.firstName = [decoder decodeObjectForKey:@"kfirstName"];
//        self.lastName = [decoder decodeObjectForKey:@"klastName"];
//        self.phoneNumber = [decoder decodeObjectForKey:@"kphoneNumber"];
//        self.patientID = [decoder decodeObjectForKey:@"kpatientID"];
//    }
//    return self;
//}

@end
