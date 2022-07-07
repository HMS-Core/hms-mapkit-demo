//
//  MapStyleViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "MapStyleViewController.h"

@interface MapStyleViewController ()

@end

@implementation MapStyleViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* Set map style. */
    NSString *path = [NSBundle.mainBundle pathForResource:@"HMapKit.bundle/resources/mapstyle_night" ofType:@"json"];
    [self.mapView setMapStyle:path];
}

@end
