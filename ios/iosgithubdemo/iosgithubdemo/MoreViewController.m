//
//  MoreViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/2/8.
//

#import "MoreViewController.h"

@interface MoreViewController ()

@end

@implementation MoreViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self setupNavigationBar];
    
    /* Annotation. */
    CLLocationCoordinate2D position = CLLocationCoordinate2DMake(48.393478, 2.034595);
    HPointAnnotation *annotation = [[HPointAnnotation alloc] init];
    annotation.coordinate = position;
    annotation.title = @"Hello Huawei Map";
    annotation.subtitle = @"This is a subtitle!";
    [self.mapView addAnnotation:annotation];
    
    /* Polyline. */
    CLLocationCoordinate2D polylineCoords[4];
    polylineCoords[0].latitude = 48.593478;
    polylineCoords[0].longitude = 2.534595;
    
    polylineCoords[1].latitude = 48.593478;
    polylineCoords[1].longitude = 2.334595;
    
    polylineCoords[2].latitude = 48.693478;
    polylineCoords[2].longitude = 2.334595;
    
    polylineCoords[3].latitude = 48.693478;
    polylineCoords[3].longitude = 2.134595;
    
    HPolyline *polyline = [HPolyline polylineWithCoordinates:polylineCoords count:4];
    [self.mapView addOverlay:polyline];
    
    /* Polygon. */
    CLLocationCoordinate2D coordinates[4];
    coordinates[0].latitude = 48.993478;
    coordinates[0].longitude = 2.134595;
    
    coordinates[1].latitude = 48.993478;
    coordinates[1].longitude = 2.534595;
    
    coordinates[2].latitude = 48.793478;
    coordinates[2].longitude = 2.534595;
    
    coordinates[3].latitude = 48.793478;
    coordinates[3].longitude = 2.134595;
    
    HPolygon *polygon = [[HPolygon alloc] initWithWithCoordinates:coordinates count:4];
    [self.mapView addOverlay:polygon];
    
    /* Circle. */
    HCircle *circle = [HCircle circleWithCenterCoordinate:CLLocationCoordinate2DMake(48.693478, 2.434595) radius:4000];
    [self.mapView addOverlay:circle];
}

- (HAnnotationView *)mapView:(HMapView *)mapView viewForAnnotation:(id<HAnnotation>)annotation
{
    if ([annotation isKindOfClass:[HPointAnnotation class]])
    {
        HPinAnnotationView *annotationView = [[HPinAnnotationView alloc] init];
        annotationView.canShowCallout   = YES;
        return annotationView;
    }
    
    return nil;
}

- (UIView *)mapView:(HMapView *)mapView customCalloutForAnnotationView:(HAnnotationView *)annotationView
{
    return [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"HMapKit.bundle/mapLogoInfo"]];
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
    else if ([overlay isKindOfClass:[HPolygon class]])
    {
        HPolygonView *polygonView = [[HPolygonView alloc] initWithPolygon:overlay];
        polygonView.lineWidth   = 3;
        polygonView.strokeColor = [UIColor colorWithRed:.2 green:.1 blue:.1 alpha:.8];
        polygonView.fillColor   = [[UIColor redColor] colorWithAlphaComponent:0.2];
        
        return polygonView;
    }
    if ([overlay isKindOfClass:[HCircle class]])
    {
        HCircleView *circleView = [[HCircleView alloc] initWithCircle:overlay];
        circleView.lineWidth   = 3;
        circleView.strokeColor = [UIColor blueColor];
        circleView.fillColor   = [UIColor greenColor];
        
        return circleView;
    }

    return nil;
}
    
- (void)setupNavigationBar
{
    self.navigationController.navigationBar.translucent = true;
    
    UIBarButtonItem *testItem = [[UIBarButtonItem alloc] initWithTitle:@"More"
                                                                 style:UIBarButtonItemStylePlain
                                                                target:self
                                                                action:@selector(handleTestAction)];
    self.navigationItem.rightBarButtonItem = testItem;
}
    
- (void)handleTestAction {
    
    UIAlertController *alertController  = [[UIAlertController alloc]init];
    UIAlertAction *hMapTypeNoneAction = [UIAlertAction actionWithTitle:@"SetMapType-HMapTypeNone" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [self.mapView setMapType:HMapTypeNone];
    }];
    [alertController addAction:hMapTypeNoneAction];
    UIAlertAction *hMapTypeTerrainAction = [UIAlertAction actionWithTitle:@"SetMapType-HMapTypeTerrain" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [self.mapView setMapType:HMapTypeTerrain];
    }];
    [alertController addAction:hMapTypeTerrainAction];
    UIAlertAction *hMapTypeNormalAction    = [UIAlertAction actionWithTitle:@"SetMapType-HMapTypeNormal" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        [self.mapView setMapType:HMapTypeNormal];
    }];
    [alertController addAction:hMapTypeNormalAction];
    UIAlertAction *moveCameraAction = [UIAlertAction actionWithTitle:@"Move Camera" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        self.mapView.centerCoordinate = CLLocationCoordinate2DMake(48.393478, 2.034595);
    }];
    [alertController addAction:moveCameraAction];
    UIAlertAction *nightAction = [UIAlertAction actionWithTitle:@"SetMapStyle-Night" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        NSString *path = [NSBundle.mainBundle pathForResource:@"mapstyle_night" ofType:@"json"];
        [self.mapView setMapStyle:path];
    }];
    [alertController addAction:nightAction];
    UIAlertAction *simpleAction = [UIAlertAction actionWithTitle:@"SetMapStyle-Simple" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        NSString *path = [NSBundle.mainBundle pathForResource:@"mapstyle_simple" ofType:@"json"];
        [self.mapView setMapStyle:path];
    }];
    [alertController addAction:simpleAction];
    [self presentViewController:alertController animated:YES completion:nil];
}

@end
