/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.OnMapClickListener;
import com.huawei.hms.maps.harmony.CommonContext;
import com.huawei.hms.maps.harmony.HuaweiMapOptions;
import com.huawei.hms.maps.harmony.model.LatLngBounds;
import com.huawei.hms.maps.harmony.model.CameraPosition;
import com.huawei.hms.maps.harmony.model.LatLng;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class HuaweiMapOptionsDemo extends AbilitySlice {
    private HuaweiMap mHuaweiMap;

    /**
     * Declare a MapView object.
     */
    private MapView mMapView;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        CommonContext.setContext(this);

        // Set initial camera attributes.
        CameraPosition cameraPosition = new CameraPosition(new LatLng(48.893478, 2.334595), 3, 45, 20);

        // Construct the target area of the camera.
        LatLng southwest = new LatLng(47.893478, 3.334595);
        LatLng northeast = new LatLng(49.893478, 1.334595);

        LatLngBounds latLngBounds = new LatLngBounds(southwest, northeast);
        HuaweiMapOptions options = new HuaweiMapOptions();
        options
                // Set camera attributes.
                .camera(cameraPosition)
                // Set whether to enable the zoom function. It is enabled by default.
                .zoomControlsEnabled(false)
                // Set whether to enable the compass. It is enabled by default.
                .compassEnabled(true)
                // Set whether to enable zoom gestures. They are enabled by default.
                .zoomGesturesEnabled(true)
                // Set whether to enable scroll gestures. They are enabled by default.
                .scrollGesturesEnabled(true)
                // Set whether to enable rotation gestures. They are enabled by default.
                .rotateGesturesEnabled(false)
                // Set whether to enable tilt gestures. They are enabled by default.
                .tiltGesturesEnabled(true)
                // Set whether to place the map view on the top of the map window. The default value is false.
                .zOrderOnTop(true)
                // Set whether to enable the lite mode for the map. It is disabled by default.
                .liteMode(false)
                // Set the preferred minimum zoom level.
                .minZoomPreference(3)
                // Set the preferred maximum zoom level.
                .maxZoomPreference(13)
                // Set an area to constrain the camera target so that the camera target does not move outside the bounds when a user scrolls the map camera.
                .latLngBoundsForCameraTarget(latLngBounds);

        // Initialize MapView Object.
        mMapView = new MapView(this, options);
        mMapView.onCreate();

        // Obtains the HuaweiMap object.
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(HuaweiMap huaweiMap) {
                mHuaweiMap = huaweiMap;

                // If mHuaweiMap is null, the program stops running.
                if (null == mHuaweiMap) {
                    return;
                }

                mHuaweiMap.setOnMapClickListener(new  OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        new ToastDialog(CommonContext.getContext()).setText("onMapClick ").show();
                    }
                });
            }
        });

        // Create a layout.
        ComponentContainer.LayoutConfig config = new ComponentContainer.LayoutConfig(ComponentContainer.LayoutConfig.MATCH_PARENT, ComponentContainer.LayoutConfig.MATCH_PARENT);
        PositionLayout myLayout = new PositionLayout(this);
        myLayout.setLayoutConfig(config);
        ShapeElement element = new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        element.setRgbColor(new RgbColor(255, 255, 255));

        // Load the MapView object.
        myLayout.addComponent(mMapView);
        super.setUIContent(myLayout);
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    protected void onBackground() {
        super.onBackground();
        if (mMapView != null) {
            mMapView.onStop();
        }
    }

    @Override
    protected void onForeground(Intent intent) {
        super.onForeground(intent);
        if (mMapView != null) {
            mMapView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }
}
