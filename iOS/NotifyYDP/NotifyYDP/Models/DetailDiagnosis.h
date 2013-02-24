//
//  DetailDiagnosis.h
//  NotifyYDP
//
//  Created by Revanth Tondapu on 9/18/12.
//
//

#import <Foundation/Foundation.h>

@interface DetailDiagnosis : NSObject

@property (nonatomic, strong) NSString* riskFactors;
@property (nonatomic, strong) NSString* goals_Instructions;
@property (nonatomic, strong) NSString* interventions;
@property (nonatomic, strong) NSString* medication;
@property (nonatomic, strong) NSString* practitioner;

@end
