/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.CameraUpdate;
import com.huawei.hms.maps.harmony.CameraUpdateFactory;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.OnMapClickListener;
import com.huawei.hms.maps.harmony.model.LatLng;
import com.huawei.hms.maps.harmony.model.CameraPosition;
import com.huawei.hms.maps.harmony.model.LatLngBounds;
import com.huawei.hms.maps.harmony.model.Point;
import com.huawei.hms.maps.harmony.OnMapLongClickListener;
import com.huawei.hms.maps.harmony.CommonContext;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;

public class MapViewCameraDemo extends AbilitySlice {
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

        // Creating a MapView
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

                // move camera
                CameraUpdate cameraUpdate = buildCameraUpdate();
                mHuaweiMap.moveCamera(cameraUpdate);

                // Sets the minimum preferred zoom level. The value ranges from 3 to 20.
                mHuaweiMap.setMinZoomPreference(3);
                // Sets the maximum preferred zoom level. The value ranges from 3 to 20.
                mHuaweiMap.setMaxZoomPreference(14);
                // Reset maximum and minimum zoom levels
                mHuaweiMap.resetMinMaxZoomPreference();
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

    /**
     * new CameraUpdate
     *
     * @return CameraUpdate
     */
    private CameraUpdate buildCameraUpdate() {
        // Method 1: Increase the camera zoom level by 1 and retain other attribute settings.
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomIn();

        // Method 2: Decrease the camera zoom level by 1 and retain other attribute settings.
        CameraUpdate cameraUpdate1 = CameraUpdateFactory.zoomOut();

        // Method 3: Set the camera zoom level to a specified value and retain other attribute settings.
        float zoom = 8.0f;
        CameraUpdate cameraUpdate2 = CameraUpdateFactory.zoomTo(zoom);

        // Method 4: Increase or decrease the camera zoom level by a specified value.
        float amount = 2.0f;
        CameraUpdate cameraUpdate3 = CameraUpdateFactory.zoomBy(amount);

        // Method 5: Move the camera to the specified center point and increase or decrease the camera zoom level
        // by a specified value.
        Point point = new Point(31, 118);
        float amount2 = 2.0f;
        CameraUpdate cameraUpdate4 = CameraUpdateFactory.zoomBy(amount2, point);

        // Method 6: Set the latitude and longitude of the camera and retain other attribute settings.
        LatLng latLng = new LatLng(31.5, 118.9);
        CameraUpdate cameraUpdate5 = CameraUpdateFactory.newLatLng(latLng);

        // Method 7: Set the visible region and padding.
        int padding = 100;
        LatLng latLng1 = new LatLng(31.5, 118.9);
        LatLng latLng2 = new LatLng(32.5, 119.9);
        LatLngBounds latLngBounds = new LatLngBounds(latLng1, latLng2);
        CameraUpdate cameraUpdate6 = CameraUpdateFactory.newLatLngBounds(latLngBounds, padding);

        // Method 8: Set the center point and zoom level of the camera.
        float zoom2 = 0.0f;
        LatLng latLng3 = new LatLng(32.5, 119.9);
        CameraUpdate cameraUpdate7 = CameraUpdateFactory.newLatLngZoom(latLng3, zoom2);

        // Method 9: Scroll the camera by specified number of pixels.
        float x = 100.0f;
        float y = 100.0f;
        CameraUpdate cameraUpdate8 = CameraUpdateFactory.scrollBy(x, y);

        // Method 10: Specify the camera position.
        // Set the tilt.
        float tilt = 2.2f;
        // Set the bearing.
        float bearing = 31.5f;
        CameraPosition cameraPosition = new CameraPosition(latLng1, zoom, tilt, bearing);
        CameraUpdate cameraUpdate9 = CameraUpdateFactory.newCameraPosition(cameraPosition);

        return cameraUpdate9;
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
