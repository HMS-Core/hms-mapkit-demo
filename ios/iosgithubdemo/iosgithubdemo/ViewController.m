//
//  ViewController.m
//  iosgithubdemo
//
//  Created by czz on 2021/12/11.
//

#import "ViewController.h"
#import "BaseViewController.h"
@interface ViewController () <UITableViewDelegate, UITableViewDataSource>


@property (nonatomic, strong) NSArray * titlsArray;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"Map Kit Demo";
    
    UITableView *tableView = [[UITableView alloc]initWithFrame:CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height) style:UITableViewStylePlain];
    
    tableView.dataSource = self;
    
    tableView.delegate = self;
    
    [self.view addSubview:tableView];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 9;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"cellIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentifier];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }

    NSString *title                 = self.titlsArray[indexPath.row];
    UIColor *normalColor            = nil;
    UIColor *highlightedTextColor   = nil;

    // TextLabel.
    cell.textLabel.text                 = title;
    cell.textLabel.textColor            = normalColor;
    cell.textLabel.highlightedTextColor = highlightedTextColor;

    // DetailLabel.
    cell.detailTextLabel.textColor            = normalColor;
    cell.detailTextLabel.highlightedTextColor = highlightedTextColor;

    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    NSString *name = [NSString stringWithFormat:@"%@ViewController",self.titlsArray[indexPath.row]];
    BaseViewController *controller;
    controller = [[NSClassFromString(name) alloc] init];
    controller.title = self.titlsArray[indexPath.row];
    [self.navigationController pushViewController:controller animated:true];
}

- (NSArray *)titlsArray {
    return @[@"Map",@"Control",@"Annotation",@"Polyline",@"Polygon",@"MapStyle",@"GroundOverlay",@"TileOverlay",@"More"];
}

@end
