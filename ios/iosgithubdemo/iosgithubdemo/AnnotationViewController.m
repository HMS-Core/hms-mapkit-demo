//
//  AnnotationViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import "AnnotationViewController.h"

@interface AnnotationViewController ()

@end

@implementation AnnotationViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /* Annotation. */
    CLLocationCoordinate2D position = CLLocationCoordinate2DMake(48.893478, 2.334595);
    HPointAnnotation *annotation = [[HPointAnnotation alloc] init];
    annotation.coordinate = position;
    annotation.title = @"Hello Huawei Map";
    annotation.subtitle = @"This is a subtitle!";
    [self.mapView addAnnotation:annotation];
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

@end
