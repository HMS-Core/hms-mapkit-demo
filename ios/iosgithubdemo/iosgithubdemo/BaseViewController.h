//
//  BaseViewController.h
//  iosgithubdemo
//
//  Created by czz on 2022/1/28.
//

#import <UIKit/UIKit.h>
#import <HMapKit/HMapKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface BaseViewController : UIViewController <HMapViewDelegate>

@property (nonatomic, strong) HMapView *mapView;

@end

NS_ASSUME_NONNULL_END
