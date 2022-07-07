//
//  ControlViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "ControlViewController.h"

@interface ControlViewController ()

@end

@implementation ControlViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* Zoom enabled. */
    self.mapView.zoomEnabled = true;
    
    /* Scroll enabled. */
    self.mapView.scrollEnabled = true;
    
    /* Rotate enabled. */
    self.mapView.rotateEnabled = true;
    
    /* Overlooking enabled. */
    self.mapView.overlookingEnabled = true;
}

@end
