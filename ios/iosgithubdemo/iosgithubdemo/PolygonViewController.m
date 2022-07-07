//
//  PolygonViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "PolygonViewController.h"

@interface PolygonViewController ()

@end

@implementation PolygonViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* Polygon. */
    CLLocationCoordinate2D coordinates[4];
    coordinates[0].latitude = 48.993478;
    coordinates[0].longitude = 2.234595;
    
    coordinates[1].latitude = 48.993478;
    coordinates[1].longitude = 2.434595;
    
    coordinates[2].latitude = 48.793478;
    coordinates[2].longitude = 2.434595;
    
    coordinates[3].latitude = 48.793478;
    coordinates[3].longitude = 2.234595;
    
    HPolygon *polygon = [[HPolygon alloc] initWithWithCoordinates:coordinates count:4];
    [self.mapView addOverlay:polygon];
}

- (HOverlayView *)mapView:(HMapView *)mapView viewForOverlay:(id<HOverlay>)overlay
{
    if ([overlay isKindOfClass:[HPolygon class]])
    {
        HPolygonView *polygonView = [[HPolygonView alloc] initWithPolygon:overlay];
        polygonView.lineWidth   = 3;
        polygonView.strokeColor = [UIColor colorWithRed:.2 green:.1 blue:.1 alpha:.8];
        polygonView.fillColor   = [[UIColor redColor] colorWithAlphaComponent:0.2];
        
        return polygonView;
    }
    
    return nil;
}

@end
