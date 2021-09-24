/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.OnMapClickListener;
import com.huawei.hms.maps.harmony.OnMapLongClickListener;
import com.huawei.hms.maps.harmony.model.LatLng;
import com.huawei.hms.maps.harmony.CommonContext;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class MapViewCameraDemo extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        CommonContext.setContext(this);

        // Declare and initialize MapView objects
        MapView mMapView = new MapView(this);

        // Creating a MapView
        mMapView.onCreate();

        // Obtains the HuaweiMap object.
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(HuaweiMap huaweiMap) {
                HuaweiMap mHuaweiMap = huaweiMap;
                mHuaweiMap.setOnMapClickListener(new OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        new ToastDialog(CommonContext.getContext()).setText("onMapClick：").show();
                    }
                });

                mHuaweiMap.setOnMapLongClickListener(new OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        new ToastDialog(CommonContext.getContext()).setText("onMapLongClick：").show();
                    }
                });

                // Sets the minimum preferred zoom level. The value ranges from 3 to 20.
                mHuaweiMap.setMinZoomPreference(3);
                // Sets the maximum preferred zoom level. The value ranges from 3 to 20.
                mHuaweiMap.setMaxZoomPreference(14);
                // Reset maximum and minimum zoom levels
                mHuaweiMap.resetMinMaxZoomPreference();
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
