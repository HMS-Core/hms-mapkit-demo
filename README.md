HMS Map Kit Demo for Android
===============================

![Apache-2.0](https://img.shields.io/badge/license-Apache-blue)

## Table of Contents

 * [Introduction](#introduction)
 * [Getting Started](#getting-started)
 * [Supported Environments](#supported-environments)
 * [Support](#support)
 * [Result](#result)
 * [License](#license)

Introduction
------------

Personalizing how your map displays and interacts with your users tailors their experience to them, not the other way around. Make location-based services work better for you so your app works better for your users.

HUAWEI Map Kit uses the WGS 84 GPS coordinate system, which meets most map development requirements outside China, including:

- Map display: Displays buildings, roads, water systems, and Points of Interest (POIs).
- Map interaction: Controls the interaction gestures and buttons on the map.
- Map drawing: Adds location markers, overlays, and various shapes.


Getting Started
---------------

We provide an example to demonstrate the use of Map SDK for Android.

This sample uses the Gradle build system.

First download the demo by cloning this repository or downloading an archived snapshot.

In Android Studio, use the "Open an existing Android Studio project", and select the directory of "map-sample".

You can use the "gradlew build" command to build the project directly.

You should create an app in AppGallery Connect, and obtain the file of agconnect-services.json and add to the project. You should also generate a signing certificate fingerprint  and add the certificate file to the project, and add configuration to build.gradle. See the [Configuring App Information in AppGallery Connect](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/android-sdk-config-agc-0000001050158641-V5) guide to configure app in AppGallery Connect.



For more development details, please refer to the following link:

- [Development Guide](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/android-sdk-introduction-0000001050158633-V5)
- [API References](https://developer.huawei.com/consumer/en/doc/development/HMSCore-References-V5/maps-overview-0000001050151498-V5)

Supported Environments
-------

Android SDK Version >= 19 and JDK version >= 1.8 is recommended.

Support
-------

If you have any questions or suggestions during use, welcome suggestions or communicate here: https://github.com/HMS-Core/hms-mapkit-demo-java/issues

## Result

  <img src="standard.jpg" width = 30% height = 30%>

  <img src="simple.jpg" width = 30% height = 30%>

  <img src="night.jpg" width = 30% height = 30%>

License
-------

HMS Mapkit Demo is licensed under [Apache License version 2.0](https://github.com/HMS-Core/hms-mapkit-demo-java/blob/master/LICENSE)

