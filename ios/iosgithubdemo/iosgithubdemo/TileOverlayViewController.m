//
//  TileOverlayViewController.m
//  iosgithubdemo
//
//  Created by czz on 2022/2/9.
//

#import "TileOverlayViewController.h"

@interface TileOverlayViewController ()

@property (nonatomic, strong) HTileOverlay *tileOverlay;

@end

@implementation TileOverlayViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSString *TileTemplate = @"http://tile.openstreetmap.org/{z}/{x}/{y}.png";
    self.tileOverlay = [[HTileOverlay alloc] initWithURLTemplate:TileTemplate];
    
    // If the default cache is used, specify the name of an existing folder before adding the overlay.
    self.tileOverlay.tileCacheDir = @"TileOverlay";
    [self.mapView addOverlay:self.tileOverlay];
}

- (HOverlayView *)mapView:(HMapView *)mapView viewForOverlay:(id<HOverlay>)overlay
{
    
    // Generate the render of tiles.
    if ([overlay isKindOfClass:[HTileOverlay class]]) {
        HTileOverlayView *render = [[HTileOverlayView alloc] initWithTileOverlay:overlay];
        render.zIndex = 0;
        return render;
    }
    
    return nil;
}

@end
