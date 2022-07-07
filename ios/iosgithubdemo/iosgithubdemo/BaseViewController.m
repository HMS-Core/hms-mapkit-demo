//
//  BaseViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "BaseViewController.h"


@interface BaseViewController ()

@end

@implementation BaseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* Base map view. */
    self.mapView = [[HMapView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.frame), CGRectGetHeight(self.view.frame) - CGRectGetMinY(self.navigationController.navigationBar.frame))];
    self.mapView.delegate = self;
    self.mapView.centerCoordinate = CLLocationCoordinate2DMake(48.893478, 2.334595);
    [self.view addSubview:self.mapView];
    
    self.mapView.showsUserLocation = true;
    self.mapView.showsLocationButton = true;
    self.mapView.showsZoomControl = true;
    self.mapView.showsScale = false;
    
    if (@available(iOS 14.0, *)) {
        if (self.mapView.accuracyAuthorization == CLAccuracyAuthorizationReducedAccuracy) {

            [self.mapView requestTempPrecisedLocation:self.mapView purposeKey:@"purposeKey" completion:^(NSError *error) {
                if (error) {
                    NSLog(@"%@", error);
                }
            }];
        }
    }
}

@end
