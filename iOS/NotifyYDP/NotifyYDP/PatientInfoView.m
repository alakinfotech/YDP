//
//  PatientInfoView.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PatientInfoView.h"
#import "YDPDataModel.h"
#import "NSData+Base64.h"

@implementation PatientInfoView
@synthesize scanView;
@synthesize formView;
@synthesize segmentControl;
@synthesize dataModel;
@synthesize cameraSim;
@synthesize resultText;
@synthesize firstName;
@synthesize lastName;
@synthesize phoneNumber;
@synthesize patientID;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    cipherObj=[[Cipher alloc]initWithKey:@"Adi&Revanth"];
    //scanView.frame = CGRectMake(20, 122, 280, 318);
    self.formView.frame = CGRectMake(20, 122, 280, 318);
    [self.view addSubview: self.formView];
    
    self.formView.hidden = YES;
    self.dataModel = [YDPDataModel sharedDataModel];
    
    NSArray *array = [[NSArray alloc] initWithContentsOfFile:@"patient.plist"];
    
    self.dataModel.patientModel.firstName = [array objectAtIndex:0];
    self.dataModel.patientModel.lastName = [array objectAtIndex:1]; 
    self.dataModel.patientModel.phoneNumber =  [array objectAtIndex:2];
    self.dataModel.patientModel.patientID = [array objectAtIndex:3];

    self.firstName.text = self.dataModel.patientModel.firstName;
    self.lastName.text = self.dataModel.patientModel.lastName; 
    self.phoneNumber.text = self.dataModel.patientModel.phoneNumber;
    self.patientID.text = self.dataModel.patientModel.patientID;
    
    self.firstName.delegate = self;
    self.lastName.delegate = self;
    self.phoneNumber.delegate = self;
    self.patientID.delegate = self;
    self.scanView.readerDelegate = self;
    
    
    // you can use this to support the simulator
    if(TARGET_IPHONE_SIMULATOR) {
        self.cameraSim = [[ZBarCameraSimulator alloc]
                     initWithViewController: self];
        //self.cameraSim.readerView.frame = CGRectMake(20, 122, 280, 318);
        self.cameraSim.readerView = self.scanView;
    }
    
}

- (void)viewDidUnload
{
    [self setScanView:nil];
    [self setFormView:nil];
    [self setSegmentControl:nil];
    [self setFirstName:nil];
    [self setLastName:nil];
    [self setPhoneNumber:nil];
    [self setPatientID:nil];
    [super viewDidUnload];
    [self cleanup];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return NO;//(interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void) willRotateToInterfaceOrientation: (UIInterfaceOrientation) orient
                                 duration: (NSTimeInterval) duration
{
    // compensate for view rotation so camera preview is not rotated
    [self.scanView willRotateToInterfaceOrientation: orient
                                        duration: duration];
}

- (void) viewDidAppear: (BOOL) animated
{
    // run the reader when the view is visible
    [self.scanView start];
}

- (void) viewWillDisappear: (BOOL) animated
{
    [self.scanView stop];
}

- (void) cleanup
{
    self.cameraSim = nil;
    self.scanView.readerDelegate = nil;
    self.scanView = nil;
}

- (IBAction)OnCancle:(id)sender {
    [self dismissModalViewControllerAnimated:YES];
}

- (IBAction)OnPatientValueChange:(UISegmentedControl *)sender {
    if (sender.selectedSegmentIndex == 0) {
        //Load scan view
        //scanView.frame = CGRectMake(20, 122, 280, 318);
        self.formView.hidden = YES;
        self.scanView.hidden = NO;
    }else{
        //Load Form View
        self.formView.hidden = NO;
        self.scanView.hidden = YES;
    }
}

- (IBAction)OnDone:(id)sender {
    
    self.dataModel.patientModel.firstName = self.firstName.text;
    self.dataModel.patientModel.lastName = self.lastName.text; 
    self.dataModel.patientModel.phoneNumber =  self.phoneNumber.text;
    self.dataModel.patientModel.patientID = self.patientID.text;

    NSArray *array = [NSArray arrayWithObjects:self.dataModel.patientModel.firstName,
                      self.dataModel.patientModel.lastName,
                      self.dataModel.patientModel.phoneNumber,
                      self.dataModel.patientModel.patientID,nil];
    
    [array writeToFile:@"patient.plist" atomically:YES];
    
    [self dismissModalViewControllerAnimated:YES];
}

- (void) readerView: (ZBarReaderView*) view
     didReadSymbols: (ZBarSymbolSet*) syms
          fromImage: (UIImage*) img
{
    // do something useful with results
    for(ZBarSymbol *sym in syms) {
        resultText = sym.data;
        break;
    }
    
    NSData *decoded=[cipherObj decrypt:[NSData dataFromBase64String:resultText]];
    resultText = [[NSString alloc] initWithData:decoded encoding:NSUTF8StringEncoding];
    
    NSArray *array = [[NSArray alloc]init];
    array = [resultText componentsSeparatedByString:@":"];
    
    //NSArray *array = [[NSArray alloc]init];
    //array = [resultText componentsSeparatedByString:@":"];
//     NSLog(@"First Name 1: %@", [array objectAtIndex:1]);
//     NSLog(@"Last Name: %@", [array objectAtIndex:3]);
//     NSLog(@"Phone Name: %@", [array objectAtIndex:5]);
//     NSLog(@"PID: %@", [array objectAtIndex:7]);
    
    if (array.count == 11) {
        
        self.firstName.text = [array objectAtIndex:1];
        self.lastName.text = [array objectAtIndex:3];
        self.phoneNumber.text = [array objectAtIndex:5];
        self.patientID.text = [array objectAtIndex:7];
    }
    //self.scanView.hidden = YES;
    //img = nil;
    //self.scanView.hidden = NO;
    //NSLog(@"%@",resultText);
    
    self.formView.hidden = NO;
    self.scanView.hidden = YES;
    self.segmentControl.selectedSegmentIndex = 1;
    
    //[self dismissModalViewControllerAnimated:YES];
    
}

-(BOOL)textFieldShouldReturn:(UITextField*)textField;
{
    NSInteger nextTag = textField.tag + 1;
    // Try to find next responder
    UIResponder* nextResponder = [textField.superview viewWithTag:nextTag];
    if (nextResponder) {
        // Found next responder, so set it.
        [nextResponder becomeFirstResponder];
    } else {
        // Not found, so remove keyboard.
        [textField resignFirstResponder];
    }
    return NO; // We do not want UITextField to insert line-breaks.
}

-(void)textFieldDidBeginEditing:(UITextField *)textField { //Keyboard becomes visible
    self.view.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y - 70*textField.tag, self.view.frame.size.width, self.view.frame.size.height); //resize
    [textField setReturnKeyType:UIReturnKeyGo];
    
}

- (void)textFieldDidEndEditing:(UITextField *)textField{
    self.view.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y + 70*textField.tag, 
                                 self.view.frame.size.width, self.view.frame.size.height); //resize

}


@end
