//
//  PolylineViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "PolylineViewController.h"

@interface PolylineViewController ()

@end

@implementation PolylineViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* Polyline. */
    CLLocationCoordinate2D polylineCoords[4];
    
    polylineCoords[0].latitude = 48.993478;
    polylineCoords[0].longitude = 2.234595;
    
    polylineCoords[1].latitude = 48.993478;
    polylineCoords[1].longitude = 2.434595;
    
    polylineCoords[2].latitude = 48.793478;
    polylineCoords[2].longitude = 2.434595;
    
    polylineCoords[3].latitude = 48.793478;
    polylineCoords[3].longitude = 2.234595;
    
    HPolyline *polyline = [HPolyline polylineWithCoordinates:polylineCoords count:4];
    
    [self.mapView addOverlay:polyline];
}

- (HOverlayView *)mapView:(HMapView *)mapView viewForOverlay:(id<HOverlay>)overlay
{
    if ([overlay isKindOfClass:[HPolyline class]])
    {
        HPolylineView *polylineView = [[HPolylineView alloc] initWithPolyline:overlay];
        polylineView.lineWidth   = 5;
        polylineView.strokeColor = [UIColor redColor];
        polylineView.fillColor = [UIColor blackColor];
        return polylineView;
    }

    return nil;
}

@end
