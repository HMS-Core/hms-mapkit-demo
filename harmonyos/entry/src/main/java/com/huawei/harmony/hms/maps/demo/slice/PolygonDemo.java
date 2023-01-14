/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.MapView;
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
import com.huawei.hms.maps.harmony.model.Polygon;
import com.huawei.hms.maps.harmony.model.PolygonOptions;
import ohos.agp.utils.Color;

public class PolygonDemo extends AbilitySlice {
    private HuaweiMap mHuaweiMap;

    /**
     * Declare a MapView object.
     */
    private MapView mMapView;

    /**
     * Declare a Polygon object.
     */
    private Polygon mPolygon;

    private boolean fillColorStatus = true;

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
        mMapView.getMapAsync(huaweiMap -> {
            mHuaweiMap = huaweiMap;

            // If mHuaweiMap is null, the program stops running.
            if (null == mHuaweiMap) {
                return;
            }

            // If mPolygon is not null, remove it from the map and then set it to null.
            if (null != mPolygon) {
                mPolygon.remove();
                mPolygon = null;
            }

            LatLng center = new LatLng(48.893478, 2.334595);
            double halfHeight = 0.1;
            double halfWidth = 0.1;

            mPolygon = mHuaweiMap
                    .addPolygon(new PolygonOptions().add(
                            new LatLng(center.mLatitude - halfHeight, center.mLongitude - halfWidth),
                            new LatLng(center.mLatitude - halfHeight, center.mLongitude + halfWidth),
                            new LatLng(center.mLatitude + halfHeight, center.mLongitude + halfWidth),
                            new LatLng(center.mLatitude + halfHeight, center.mLongitude - halfWidth))
                            .fillColor(Color.GREEN.getValue())
                            .strokeColor(Color.BLACK.getValue()));

            // move camera
            mHuaweiMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 8));
        });

        this.rootLayout.addComponent(mMapView);
    }

    private void addButtons() {
        int height = ScreenUtil.getScreenHeight(this);

        Button buttonStrokeColor = createButton();
        buttonStrokeColor.setText("StrokeColor");
        buttonStrokeColor.setContentPosition(50, (float) height / 2 - 100);
        this.rootLayout.addComponent(buttonStrokeColor);

        Button buttonFillColor = createButton();
        buttonFillColor.setText("FillColor");
        buttonFillColor.setContentPosition(50, (float) height / 2);
        this.rootLayout.addComponent(buttonFillColor);


        buttonStrokeColor.setClickedListener(component -> {
            if (null == mPolygon) {
                return;
            }
            if (strokeColorStatus) {
                // Set the stroke color of the polygon (mPolygon) to red.
                mPolygon.setStrokeColor(Color.RED.getValue());
            } else {
                // Set the stroke color of the polygon (mPolygon) to green.
                mPolygon.setStrokeColor(Color.GREEN.getValue());
            }
            strokeColorStatus = !strokeColorStatus;
        });

        buttonFillColor.setClickedListener(component -> {
            if (null == mPolygon) {
                return;
            }
            if (fillColorStatus) {
                // Set the fill color of the polygon (mCircle) to red.
                mPolygon.setFillColor(Color.RED.getValue());
            } else {
                // Set the fill color of the polygon (mPolygon) to green.
                mPolygon.setFillColor(Color.GREEN.getValue());
            }
            fillColorStatus = !fillColorStatus;
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
