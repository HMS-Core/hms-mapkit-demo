//
//  MapViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "MapViewController.h"

@implementation MapViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* MapView. */
    self.mapView = [[HMapView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.frame), CGRectGetHeight(self.view.frame) - CGRectGetMinY(self.navigationController.navigationBar.frame))];
    self.mapView.delegate = self;
    self.mapView.centerCoordinate = CLLocationCoordinate2DMake(48.893478, 2.334595);
    [self.view addSubview:self.mapView];
    
    self.mapView.showsScale = false;
}

@end
