## Map Kit Sample Code

## Contents
* [Overview](#Overview)
* [Preparations](#Preparations)
* [Environment Requirements](#Environment-Requirements)
* [Result](#Result)
* [Technical Support](#Technical-Support)
* [License](#License)


Overview
------------
Map Kit is an SDK for map development. It covers map data of more than 200 countries and regions, and supports over 70 languages. With this SDK, you can easily integrate map-based functions into your apps.

In addition, Map Kit continuously optimizes and enriches the map detail display capability. For example, Map Kit can clearly show green fields and vegetation information when the map is zoomed out. It can also display most suitable POIs and road network information in different scales based on the POI attributes and regional road network differences. Map Kit supports gestures including zoom, rotation, moving, and tilt gestures to ensure smooth interaction experience.

Map Kit uses the WGS 84 GPS coordinate system for countries and regions outside the Chinese mainland and GCJ02 coordinate system for the Chinese mainland, which can meet most map development requirements including:

- Map display: Displays features such as buildings, roads, water systems, and Points of Interest (POIs).
- Map interaction: Controls the interaction gestures and buttons on the map.
- Map drawing: Adds map elements such as location markers, map layers, overlays, and various shapes.

Preparations
---------------
Before you start developing an app, configure app information in AppGallery Connect.

### Registering as a Huawei Developer
Before getting started, register as a Huawei developer and complete identity verification on [HUAWEI Developers](https://developer.huawei.com/consumer/en/). The procedure is detailed [here](https://developer.huawei.com/consumer/en/doc/start/registration-and-verification-0000001053628148).

### Creating a Project
[Create your project](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-help-createproject-0000001100334664).

### Creating an App
[Create an app](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-help-createapp-0000001146718717) under the project. Pay attention to the following parameter settings:

- **Platform**: **iOS**.
- **App package ID**: Bundle ID of your iOS app.
- **App name**: name of the app. It is invisible to users.
- **App Store ID**: Apple ID in App Store Connect.

### Enabling Required Services
To use Map Kit, you need to enable it in [AppGallery Connect](https://developer.huawei.com/consumer/en/service/josp/agc/index.html). For details, please refer to [Enabling Services](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-help-enabling-service-0000001146598793).
For the detailed development guide, please refer to [iOS development documentation](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-sdk-introduction-0000001197914654).

Environment Requirements
-------

iOS 11.0 or later is recommended.

## Result

  <img src="standard.png" width = 30% height = 30%>

  <img src="simple.png" width = 30% height = 30%>

  <img src="night.png" width = 30% height = 30%>

## Technical Support
You can visit the [Reddit community](https://www.reddit.com/r/HMSCore/) to obtain the latest information about HMS Core and communicate with other developers.

If you have any questions about the sample code, try the following:
- Visit [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services?tab=Votes), submit your questions, and tag them with **huawei-mobile-services**. Huawei experts will answer your questions.
- Visit the HMS Core section in the [HUAWEI Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001?ha_source=hms3) and communicate with other developers.

If you encounter any issues when using the sample code, submit your [issues](https://github.com/HMS-Core/hms-mapkit-demo-java/issues) or submit a [pull request](https://github.com/HMS-Core/hms-mapkit-demo/pulls).

License
-------

The sample code is licensed under [Apache License 2.0](https://github.com/HMS-Core/hms-mapkit-demo-java/blob/master/LICENSE).
