/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.harmony.hms.maps.demo.ResourceTable;
import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.HuaweiMapOptions;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.OnMapClickListener;
import com.huawei.hms.maps.harmony.OnInfoWindowClickListener;
import com.huawei.hms.maps.harmony.OnMarkerClickListener;
import com.huawei.hms.maps.harmony.model.CameraPosition;
import com.huawei.hms.maps.harmony.model.LatLng;
import com.huawei.hms.maps.harmony.model.Marker;
import com.huawei.hms.maps.harmony.model.MarkerOptions;
import com.huawei.hms.maps.harmony.CommonContext;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.window.dialog.ToastDialog;
import ohos.global.resource.NotExistException;
import ohos.global.resource.Resource;

import java.io.IOException;

public class MarkerDemo extends AbilitySlice {
    private HuaweiMap mHuaweiMap;

    /**
     * Declare a MapView object.
     */
    private MapView mMapView;

    /**
     * Declare a Polyline object.
     */
    private Marker mMarker;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        CommonContext.setContext(this);

        // Declaring and Initializing the HuaweiMapOptions Object
        HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();

        // Initialize Camera Properties
        CameraPosition cameraPosition =
                new CameraPosition(new LatLng(48.893478, 2.334595), 6, 0, 0);

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

        // Initialize MapView Object.
        mMapView = new MapView(this, huaweiMapOptions);
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

                // If mMarker is not null, remove it from the map and then set it to null.
                if (null != mMarker) {
                    mMarker.remove();
                    mMarker = null;
                }

                // Add a marker to the map.
                MarkerOptions options = new MarkerOptions()
                        .position(new LatLng(48.893478, 2.334595))
                        .title("Hello Huawei Map")
                        .snippet("This is a snippet!");
                mMarker = mHuaweiMap.addMarker(options);

                // Set the marker title.
                if (mMarker != null) {
                    mMarker.setTitle("Marker title");
                }

                // Set whether the marker can be dragged.
                if (mMarker != null) {
                    mMarker.setDraggable(true);
                }

                // Set the marker anchor point.
                if (mMarker != null) {
                    mMarker.setMarkerAnchor(0.9F, 0.9F);
                }

                // Customizing the Marker Icon
                addCustomMarker();

                mHuaweiMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        new ToastDialog(CommonContext.getContext()).setText("onMarkerClick: " + marker.getTitle()).show();
                        return false;
                    }
                });

                mHuaweiMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        new ToastDialog(CommonContext.getContext()).setText("onInfoWindowClick：").show();
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

    /**
     * addCustomMarker
     */
    private void addCustomMarker() {
        Resource resource = null;
        try {
            resource = getResourceManager().getResource(ResourceTable.Media_icon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotExistException e) {
            e.printStackTrace();
        }

        if (resource != null) {
            mHuaweiMap.addMarker(new MarkerOptions()
                    .position(new LatLng(47.8333, 2.8333))
                    .icon(resource));
        }
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
