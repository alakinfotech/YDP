//
//  PhysicionInfoView.m
//  NotifyYDP
//
//  Created by Revanth Tondapu on 6/3/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "PhysicionInfoView.h"
#import "YDPDataModel.h"
#import "PhysicionModel.h"
#import "MKMapView+ZoomLevel.h"
#import "JSON.h"
#import "AutoScrollLabel.h"

#define GEORGIA_TECH_LATITUDE 33.777328
#define GEORGIA_TECH_LONGITUDE -84.397348

#define ZOOM_LEVEL 14

@implementation PhysicionInfoView
@synthesize reverseGeocoder;
@synthesize mapView;
@synthesize segmentControl;
@synthesize formView;
@synthesize dataModel;
@synthesize firstName;
@synthesize lastName;
@synthesize phoneNumber;
@synthesize address;
@synthesize currentLocation;

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
    // Do any additional setup after loading the view from its nib.
    self.formView.frame = CGRectMake(20, 122, 280, 350);
    [self.view addSubview: self.formView];
    
    self.formView.hidden = YES;
    self.dataModel = [YDPDataModel sharedDataModel];
    
    self.firstName.delegate = self;
    self.lastName.delegate = self;
    self.phoneNumber.delegate = self;
    self.address.delegate = self;
    
   
    
}

- (void) viewDidAppear:(BOOL)animated{
    self.mapView.showsUserLocation = YES;
    
    [self.mapView.userLocation addObserver:self  
                                forKeyPath:@"location"  
                                   options:(NSKeyValueObservingOptionNew|NSKeyValueObservingOptionOld)  
                                   context:NULL];
    
}

- (void) viewDidDisappear:(BOOL)animated{
    [self.mapView.userLocation removeObserver:self forKeyPath:@"location"];
}
- (void)observeValueForKeyPath:(NSString *)keyPath  
                      ofObject:(id)object  
                        change:(NSDictionary *)change  
                       context:(void *)context {  
    
    if ([self.mapView showsUserLocation]) {  
        //[self moveOrZoomOrAnythingElse];

        [mapView setCenterCoordinate:self.mapView.userLocation.coordinate zoomLevel:ZOOM_LEVEL animated:NO];
        // and of course you can use here old and new location values
        self.address.text = [self getLocation:self.mapView.userLocation.coordinate.latitude logitude:self.mapView.userLocation.coordinate.longitude];
        self.currentLocation.text = self.address.text;
        self.currentLocation.textColor = [UIColor blackColor];
        NSLog(@"User Location:%@",self.address.text);
    }
}

- (void)viewDidUnload
{
    [self setMapView:nil];
    [self setCurrentLocation:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return NO;//(interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)OnCancle:(id)sender {
    [self dismissModalViewControllerAnimated:YES];
}

- (IBAction)OnDone:(id)sender{
    self.dataModel.physicionModel.firstName = self.firstName.text;
    self.dataModel.physicionModel.lastName = self.lastName.text;
    self.dataModel.physicionModel.phoneNumber = self.phoneNumber.text;
    self.dataModel.physicionModel.address = self.address.text;
    
    [self dismissModalViewControllerAnimated:YES];
}

- (IBAction)OnPatientValueChange:(UISegmentedControl *)sender {
    if (sender.selectedSegmentIndex == 0) {
        //Load scan view
        //scanView.frame = CGRectMake(20, 122, 280, 318);
        self.formView.hidden = YES;
        self.mapView.hidden = NO;
    }else{
        //Load Form View
        self.formView.hidden = NO;
        self.mapView.hidden = YES;
    }
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

- (NSString *) getLocation:(double) lattitude logitude:(double) longitude
{
    NSString *urlString = [NSString stringWithFormat:@"http://maps.google.com/maps/api/geocode/json?latlng=%f,%f&sensor=false",lattitude,longitude];
    
    
    NSURL *urlFromString = [NSURL URLWithString:urlString];
    NSStringEncoding encodingType = NSUTF8StringEncoding;
    NSString *reverseGeoString = [NSString stringWithContentsOfURL:urlFromString encoding:encodingType error:nil];
    
    NSDictionary *locationResult = [reverseGeoString JSONValue];
    NSString *status = (NSString *)[locationResult objectForKey:@"status"];
    NSString *retVal = nil;
    if([status isEqualToString:@"OK"])
    {
        NSArray *results = [locationResult objectForKey:@"results"];
        if([results count] > 0)
        {
            NSDictionary *address=[results objectAtIndex:0];
            retVal = [address objectForKey:@"formatted_address"];
        }
        
        
    }   
    return retVal;
}
@end
