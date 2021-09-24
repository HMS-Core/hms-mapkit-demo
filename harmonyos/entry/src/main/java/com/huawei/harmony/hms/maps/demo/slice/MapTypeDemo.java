/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.HuaweiMapOptions;
import com.huawei.hms.maps.harmony.MapView;
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

public class MapTypeDemo extends AbilitySlice {
    int longClickCounter = 0;

    HuaweiMap mHuaweiMap;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        CommonContext.setContext(this);

        // Declaring MapView Objects
        MapView mMapView;

        // Declaring and Initializing the HuaweiMapOptions Object
        HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();

        // Enable the lite mode map.
        huaweiMapOptions.liteMode(true);

        // Initializing MapView Objects
        mMapView = new MapView(this, huaweiMapOptions);
        mMapView.onCreate();

        // Obtains the HuaweiMap object.
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(HuaweiMap huaweiMap) {
                mHuaweiMap = huaweiMap;
                mHuaweiMap.setOnMapClickListener(new  OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        new ToastDialog(CommonContext.getContext()).setText("onMapClick ").show();
                    }
                });

                mHuaweiMap.setOnMapLongClickListener(new OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        if (longClickCounter % 2 ==0 ) {
                            mHuaweiMap.setMapType(HuaweiMap.MAP_TYPE_NONE);
                            new ToastDialog(CommonContext.getContext()).setText("HuaweiMap.MAP_TYPE_NONE ").show();
                            longClickCounter++;
                        } else {
                            mHuaweiMap.setMapType(HuaweiMap.MAP_TYPE_NORMAL);
                            new ToastDialog(CommonContext.getContext()).setText("HuaweiMap.MAP_TYPE_NORMAL ").show();
                            longClickCounter++;
                        }
                    }
                });
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
