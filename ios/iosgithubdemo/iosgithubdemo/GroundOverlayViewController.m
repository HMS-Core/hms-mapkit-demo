//
//  GroundOverlayViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/2/9.
//

#import "GroundOverlayViewController.h"

@interface GroundOverlayViewController ()

@end

@implementation GroundOverlayViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* GroundOverlay. */
    CLLocationCoordinate2D position = CLLocationCoordinate2DMake(48.893478, 2.334595);
    HGroundOverlay *groundOverlay = [HGroundOverlay groundOverlayWithCoordinate:position zoomLevel:8 anchor:CGPointMake(0.5, 0.5) icon:[UIImage imageNamed:@"HMapKit.bundle/location"]];
    groundOverlay.opacity = 1.f;
    [self.mapView addOverlay:groundOverlay];
}

- (HOverlayView *)mapView:(HMapView *)mapView viewForOverlay:(id<HOverlay>)overlay
{
    if ([overlay isKindOfClass:[HGroundOverlay class]])
    {
        HGroundOverlayView *groundOverlayView = [[HGroundOverlayView alloc] initWithOverlay:overlay];
        return groundOverlayView;
    }
    
    return nil;
}

@end
