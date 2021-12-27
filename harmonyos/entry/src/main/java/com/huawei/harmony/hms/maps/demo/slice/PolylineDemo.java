/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.CameraUpdateFactory;
import com.huawei.hms.maps.harmony.CommonContext;
import com.huawei.hms.maps.harmony.model.LatLng;
import com.huawei.harmony.hms.maps.demo.ResourceTable;
import com.huawei.harmony.hms.maps.demo.utils.ScreenUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.element.ShapeElement;
import com.huawei.hms.maps.harmony.model.Polyline;
import com.huawei.hms.maps.harmony.model.PolylineOptions;
import ohos.agp.utils.Color;

public class PolylineDemo extends AbilitySlice {
    private HuaweiMap mHuaweiMap;

    /**
     * Declare a MapView object.
     */
    private MapView mMapView;

    /**
     * Declare a Polyline object.
     */
    private Polyline mPolyline;

    private boolean strokeColorStatus = true;

    /**
     * the layout
     */
    private PositionLayout rootLayout;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        initPositionLayout();
        initMapView();
        addButtons();
        super.setUIContent(this.rootLayout);
    }

    private void initPositionLayout() {
        rootLayout = new PositionLayout(this);
        this.rootLayout.setContentPosition(0, 0);
        this.rootLayout.setWidth(ComponentContainer.LayoutConfig.MATCH_PARENT);
        this.rootLayout.setHeight(ComponentContainer.LayoutConfig.MATCH_PARENT);

        ShapeElement element = new ShapeElement();
        element.setShape(ShapeElement.RECTANGLE);
        element.setRgbColor(new RgbColor(255, 255, 255));
        this.rootLayout.setBackground(element);
    }

    private void initMapView() {
        CommonContext.setContext(this);

        // Initialize MapView Object.
        mMapView = new MapView(this);
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

                // If mPolyline is not null, remove it from the map and then set it to null.
                if (null != mPolyline) {
                    mPolyline.remove();
                    mPolyline = null;
                }

                // Add a polyline to a map.
                mPolyline = mHuaweiMap.addPolyline(new PolylineOptions()
                        // polyline coordinate
                        .add(new LatLng(47.893478, 2.334595), new LatLng(48.993478, 3.434595),
                                new LatLng(48.693478, 2.134595), new LatLng(48.793478, 2.334595))
                        // Polyline Color
                        .color(Color.BLUE.getValue())
                        // Polyline Width
                        .width(3));

                // move camera
                mHuaweiMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 6));
            }
        });

        this.rootLayout.addComponent(mMapView);
    }

    private void addButtons() {
        int height = ScreenUtil.getScreenHeight(this);

        Button buttonStrokeColor = createButton();
        buttonStrokeColor.setText("StrokeColor");
        buttonStrokeColor.setContentPosition(50, (float) height / 2);
        this.rootLayout.addComponent(buttonStrokeColor);

        buttonStrokeColor.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (null == mPolyline) {
                    return;
                }
                if (strokeColorStatus) {
                    // Set the color of the polyline (mPolyline) to red.
                    mPolyline.setColor(Color.RED.getValue());
                } else {
                    // Set the color of the polyline (mPolyline) to green.
                    mPolyline.setColor(Color.GREEN.getValue());
                }
                // Set the width of the polyline (mPolyline) to 10 pixels.
                mPolyline.setWidth(10);
                strokeColorStatus = !strokeColorStatus;
            }
        });
    }

    /**
     * Create button
     *
     * @return Button
     */
    private Button createButton() {
        Component component = LayoutScatter.getInstance(getContext())
                .parse(ResourceTable.Layout_button_layout, null, false);
        return (Button) component;
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
