/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.hms.maps.harmony.HuaweiMap;
import com.huawei.hms.maps.harmony.OnMapReadyCallback;
import com.huawei.hms.maps.harmony.CameraUpdateFactory;
import com.huawei.hms.maps.harmony.MapView;
import com.huawei.hms.maps.harmony.CommonContext;
import com.huawei.hms.maps.harmony.model.LatLng;
import com.huawei.harmony.hms.maps.demo.ResourceTable;
import com.huawei.harmony.hms.maps.demo.utils.ScreenUtil;
import com.huawei.hms.maps.harmony.model.Circle;
import com.huawei.hms.maps.harmony.model.CircleOptions;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.agp.components.PositionLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;

public class CircleDemo extends AbilitySlice{
    private HuaweiMap mHuaweiMap;

    /**
     * Declare a MapView object.
     */
    private MapView mMapView;

    /**
     * Declare a Circle object.
     */
    private Circle mCircle;

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

        // Obtain a HuaweiMap object.
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(HuaweiMap huaweiMap) {
                mHuaweiMap = huaweiMap;

                // If mHuaweiMap is null, the program stops running.
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

                int strokeColor = Color.RED.getValue();
                float strokeWidth = 15.0f;

                // Set the edge color of a circle
                mCircle.setStrokeColor(strokeColor);

                // Sets the edge width of a circle
                mCircle.setStrokeWidth(strokeWidth);

                // move camera
                mHuaweiMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 12));
            }
        });

        this.rootLayout.addComponent(mMapView);
    }

    private void addButtons() {
        int height = ScreenUtil.getScreenHeight(this);

        Button buttonHollowCircle = createButton();
        buttonHollowCircle.setText("HollowCircle");
        buttonHollowCircle.setContentPosition(50, (float) height / 2 - 100);
        this.rootLayout.addComponent(buttonHollowCircle);

        Button buttonStrokeColor = createButton();
        buttonStrokeColor.setText("StrokeColor");
        buttonStrokeColor.setContentPosition(50, (float) height / 2);
        this.rootLayout.addComponent(buttonStrokeColor);

        Button buttonFillColor = createButton();
        buttonFillColor.setText("FillColor");
        buttonFillColor.setContentPosition(50, (float) height / 2 + 100);
        this.rootLayout.addComponent(buttonFillColor);

        buttonHollowCircle.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                // Set a hollow circle.
                if (null != mCircle) {
                    // Set the fill color of the circle to transparent.
                    mCircle.setFillColor(Color.TRANSPARENT.getValue());
                    // Set the stroke color of the circle.
                    mCircle.setStrokeColor(Color.RED.getValue());
                    // Set the stroke width of the circle.
                    mCircle.setStrokeWidth(15.0f);
                }
            }
        });

        buttonStrokeColor.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (null == mCircle) {
                    return;
                }
                if (strokeColorStatus) {
                    // Set the stroke color of the circle (mCircle) to red.
                    mCircle.setStrokeColor(Color.RED.getValue());
                } else {
                    // Set the stroke color of the circle (mCircle) to green.
                    mCircle.setStrokeColor(Color.GREEN.getValue());
                }
                strokeColorStatus = !strokeColorStatus;
            }
        });

        buttonFillColor.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (null == mCircle) {
                    return;
                }
                if (fillColorStatus) {
                    // Set the fill color of the circle (mCircle) to red.
                    mCircle.setFillColor(Color.RED.getValue());
                } else {
                    // Set the fill color of the circle (mCircle) to green.
                    mCircle.setFillColor(Color.GREEN.getValue());
                }
                fillColorStatus = !fillColorStatus;
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
