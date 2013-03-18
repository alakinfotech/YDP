//
//  Diagnosis.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 9/18/12.
//
//

#import <Foundation/Foundation.h>
@class DetailDiagnosis;

@interface Diagnosis : NSObject

@property (nonatomic, strong) NSDate *date;
@property (nonatomic, strong) NSString *diagnosis;
@property (nonatomic, strong) NSString *status;
@property (nonatomic, strong) DetailDiagnosis *detaileDiagnosis;
@end
