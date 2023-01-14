/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.CommonContext;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class GestureDemo extends AbilitySlice {
    private HuaweiMap mHuaweiMap;

    /**
     * Declare a MapView object.
     */
    private MapView mMapView;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        CommonContext.setContext(this);

        // Initialize MapView Object.
        mMapView = new MapView(this);
        mMapView.onCreate();

        // Obtains the HuaweiMap object.
        mMapView.getMapAsync(huaweiMap -> {
            mHuaweiMap = huaweiMap;

            // If mHuaweiMap is null, the program stops running.
            if (null == mHuaweiMap) {
                return;
            }

            mHuaweiMap.setOnMapClickListener(latLng -> new ToastDialog(CommonContext.getContext()).setText("onMapClick ").show());

            // Set whether to enable the compass.
            mHuaweiMap.getUiSettings().setCompassEnabled(true);

            // Set whether to enable the zoom controls.
            mHuaweiMap.getUiSettings().setZoomControlsEnabled(true);

            // Set whether to enable the zoom gestures.
            mHuaweiMap.getUiSettings().setZoomGesturesEnabled(true);

            // Set whether to enable the scroll gestures.
            mHuaweiMap.getUiSettings().setScrollGesturesEnabled(true);

            // Set whether to enable the tilt gestures.
            mHuaweiMap.getUiSettings().setTiltGesturesEnabled(true);

            // Set whether to enable the rotation gestures.
            mHuaweiMap.getUiSettings().setRotateGesturesEnabled(true);
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
