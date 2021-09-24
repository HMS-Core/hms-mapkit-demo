/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.HuaweiMapOptions;
import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.OnMapClickListener;
import com.huawei.hms.maps.harmony.model.CameraPosition;
import com.huawei.hms.maps.harmony.model.LatLng;
import com.huawei.hms.maps.harmony.model.Circle;
import com.huawei.hms.maps.harmony.model.CircleOptions;
import com.huawei.hms.maps.harmony.CommonContext;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;

public class CircleDemo extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        CommonContext.setContext(this);

        // Declaring MapView Objects
        MapView mMapView;

        // Declaring and Initializing the HuaweiMapOptions Object
        HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();

        // Initialize Camera Properties
        CameraPosition cameraPosition =
                new CameraPosition(new LatLng(48.893478, 2.334595), 12, 0, 0);

        huaweiMapOptions
                // Set Camera Properties
                .camera(cameraPosition)
                // Enables or disables the zoom function. By default, the zoom function is enabled.
                .zoomControlsEnabled(false)
                // Sets whether the compass is available. The compass is available by default.
                .compassEnabled(true)
                // Specifies whether the zoom gesture is available. By default, the zoom gesture is available.
                .zoomGesturesEnabled(true)
                // Specifies whether to enable the scrolling gesture. By default, the scrolling gesture is enabled.
                .scrollGesturesEnabled(true)
                // Specifies whether the rotation gesture is available. By default, the rotation gesture is available.
                .rotateGesturesEnabled(false)
                // Specifies whether the tilt gesture is available. By default, the tilt gesture is available.
                .tiltGesturesEnabled(true)
                // Sets whether the map is in lite mode. The default value is No.
                .liteMode(false)
                // Set Preference Minimum Zoom Level
                .minZoomPreference(3)
                // Set Preference Maximum Zoom Level
                .maxZoomPreference(13);

        // Initializing MapView Objects
        mMapView = new MapView(this, huaweiMapOptions);
        mMapView.onCreate();

        // Obtains the HuaweiMap object.
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(HuaweiMap huaweiMap) {
                HuaweiMap mHuaweiMap = huaweiMap;
                mHuaweiMap.setOnMapClickListener(new  OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        new ToastDialog(CommonContext.getContext()).setText("onMapClick ").show();
                    }
                });

                // Initialize the Circle object.
                Circle mCircle = new Circle(this);
                if (null == mHuaweiMap) {
                    return;
                }
                if (null != mCircle) {
                    mCircle.remove();
                    mCircle = null;
                }

                mCircle = mHuaweiMap.addCircle(new CircleOptions()
                        .center(new LatLng(48.893478, 2.334595))
                        .radius(500)
                        .fillColor(Color.GREEN.getValue()));
                new ToastDialog(CommonContext.getContext()).setText("color green: " + Color.GREEN.getValue()).show();

                int strokeColor = Color.RED.getValue();
                float strokeWidth = 15.0f;

                // Set the edge color of a circle
                mCircle.setStrokeColor(strokeColor);

                // Sets the edge width of a circle
                mCircle.setStrokeWidth(strokeWidth);
            }
        });

        // Creating a Layout
        ComponentContainer.LayoutConfig config = new ComponentContainer.LayoutConfig(ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_PARENT);
        PositionLayout myLayout = new PositionLayout(this);
        myLayout.setLayoutConfig(config);
        ShapeElement element = new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        element.setRgbColor(new RgbColor(255, 255, 255));

        // Load MapView
        myLayout.addComponent(mMapView);
        super.setUIContent(myLayout);
    }
}
