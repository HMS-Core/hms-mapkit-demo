HMS MapKit Demo
===============================

![Apache-2.0](https://img.shields.io/badge/license-Apache-blue)

English | [中文](https://github.com/HMS-Core/hms-mapkit-demo-java/blob/master/README_ZH.md)

## Table of Contents

 * [Introduction](#introduction)
 * [Getting Started](#getting-started)
 * [Supported Environments](#supported-environments)
 * [Result](#result)
 * [License](#license)

Introduction
------------

Personalizing how your map displays and interacts with your users tailors their experience to them, not the other way around. Make location-based services work better for you so your app works better for your users.

HUAWEI Map Kit uses the WGS 84 GPS coordinate system, which meets most map development requirements outside China, including:

- Map display: Displays buildings, roads, water systems, and Points of Interest (POIs).
- Map interaction: Controls the interaction gestures and buttons on the map.
- Map drawing: Adds location markers, overlays, and various shapes.

You also can use HMS Toolkit to quickly integrate the kit and run the demo project, as well as debug the app using a remote device for free. For details, please visit https://developer.huawei.com/consumer/en/doc/development/Tools-Guides/getting-started-0000001077381096

Getting Started
---------------

We provide an example to demonstrate the use of Map SDK for Android.

This sample uses the Gradle build system.

First download the demo by cloning this repository or downloading an archived snapshot.

In Android Studio, use the "Open an existing Android Studio project", and select the directory of "map-sample".

You can use the "gradlew build" command to build the project directly.

You should create an app in AppGallery Connect, and obtain the file of agconnect-services.json and add to the project(Note: The ApiKey needs to be configured for the com/huawei/hms/maps/sample/utils/NetClient.java file separately). You should also generate a signing certificate fingerprint  and add the certificate file to the project, and add configuration to build.gradle. See the [Configuring App Information in AppGallery Connect](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-sdk-config-agc-0000001061560289) guide to configure app in AppGallery Connect.


For more development details, please refer to the following link:

- [Development Guide](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-sdk-brief-introduction-0000001061991343)
- [API References](https://developer.huawei.com/consumer/en/doc/development/HMSCore-References/package-summary-0000001063736331)

Supported Environments
-------

Android SDK Version >= 19 and JDK version >= 1.8 is recommended.


## Result
Standard

<img src="https://github.com/HMS-Core/hms-mapkit-demo/blob/master/standard.jpg" width = 30% height = 30%>
  
Simple

<img src="https://github.com/HMS-Core/hms-mapkit-demo/blob/master/simple.jpg" width = 30% height = 30%>
  
Night

<img src="https://github.com/HMS-Core/hms-mapkit-demo/blob/master/night.jpg" width = 30% height = 30%>

## Question or issues
If you want to evaluate more about HMS Core,
[r/HMSCore on Reddit](https://www.reddit.com/r/HMSCore/) is for you to keep up with latest news about HMS Core, and to exchange insights with other developers.

If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
**huawei-mobile-services**.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001?ha_source=hms1) HMS Core Module is great for general questions, or seeking recommendations and opinions.

If you run into a bug in our samples, please submit an [issue](https://github.com/HMS-Core/hms-mapkit-demo-java/issues) to the Repository. Even better you can submit a [Pull Request](https://github.com/HMS-Core/hms-mapkit-demo-java/pulls) with a fix.

License
-------

HMS Mapkit Demo is licensed under [Apache License version 2.0](https://github.com/HMS-Core/hms-mapkit-demo-java/blob/master/LICENSE)
