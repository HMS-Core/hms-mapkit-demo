HMS Core Map Kit Sample Code
===============================

![Apache-2.0](https://img.shields.io/badge/license-Apache-blue)

English | [中文](README_ZH.md)

## Contents

* [Introduction](#Introduction)
* [Preparations](#Preparations)
* [Environment Requirements](#Environment-Requirements)
* [Result](#Result)
* [Technical Support](#Technical-Support)
* [License](#License)

Introduction
------------

Map Kit allows you to customize your map display and the way for users to interact with your map, tailoring map use experience for your users. This can make location-based services in your app work better, empowering users to better use your app.

Map Kit uses the WGS 84 GPS coordinate system, which meets most map development requirements outside China, including:

- Map display: Displays buildings, roads, water systems, Points of Interest (POIs), and others.
- Map interaction: Controls the interaction gestures and buttons on the map.
- Map drawing: Adds location markers, map layers, overlays, various shapes, and others.

You can also use HMS Toolkit to quickly run the sample code. HMS Toolkit supports one-stop kit integration, and provides functions such as free app debugging on remote real devices. To learn more about HMS Toolkit, please refer to the [HMS Toolkit documentation](https://developer.huawei.com/consumer/en/doc/development/Tools-Guides/getting-started-0000001077381096?ha_source=hms1).

Preparations
---------------

The sample code is built using Gradle to demonstrate how to use the Map SDK for Android.

First, download the sample code by cloning this repository or downloading the compressed package.

In Android Studio, click **Open an existing Android Studio project** and select the directory where the **map-sample** file is located.

You can use the **gradlew build** command to directly build the project.

Then, you need to create an app in AppGallery Connect, obtain the **agconnect-services.json** file, and add it to the project. You also need to generate a signing certificate fingerprint, add the signing certificate file to the project, and add related configurations to the **build.gradle** file. For details, please refer to [Configuring App Information in AppGallery Connect](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-sdk-config-agc-0000001061560289?ha_source=hms1).

To learn more, refer to the following documents:

- [Development Guide](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-sdk-brief-introduction-0000001061991343?ha_source=hms1)
- [API Reference](https://developer.huawei.com/consumer/en/doc/development/HMSCore-References/package-summary-0000001063736331?ha_source=hms1)

Environment Requirements
-------

It is recommended that the Android SDK version be 19 or later, and the JDK version be 11.

## Result

  <img src="standard.jpg" width = 30% height = 30%>

  <img src="simple.jpg" width = 30% height = 30%>

  <img src="night.jpg" width = 30% height = 30%>

## Technical Support
You can visit the [Reddit community](https://www.reddit.com/r/HMSCore/) to obtain the latest information about HMS Core and communicate with other developers.

If you have any questions about the sample code, try the following:
- Visit [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services?tab=Votes), submit your questions, and tag them with **huawei-mobile-services**. Huawei experts will answer your questions.
- Visit the HMS Core section in the [HUAWEI Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001?ha_source=hms1) and communicate with other developers.

If you encounter any issues when using the sample code, submit your [issues](https://github.com/HMS-Core/hms-mapkit-demo-java/issues) or submit a [pull request](https://github.com/HMS-Core/hms-mapkit-demo/pulls).

License
-------

The sample code is licensed under [Apache License 2.0](https://github.com/HMS-Core/hms-mapkit-demo-java/blob/master/LICENSE).
