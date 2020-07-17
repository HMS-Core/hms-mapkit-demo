/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  2020.1.3-Changed modify the import classes type and add some events demo.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnCameraIdleListener;
import com.huawei.hms.maps.HuaweiMap.OnMapLongClickListener;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.VisibleRegion;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This shows how to listen to some HuaweiMap events
 */
public class EventsDemoActivity extends AppCompatActivity implements HuaweiMap.OnMapClickListener,
    OnMapLongClickListener, OnCameraIdleListener, OnMapReadyCallback, HuaweiMap.OnMyLocationButtonClickListener {
    private static final String TAG = "EventsDemoActivity";

    private TextView mTapView;

    private TextView mToLatLngView;

    private TextView mToPointView;

    private HuaweiMap hMap;

    private SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(this);
        mTapView = (TextView) findViewById(R.id.tap_text);
        mToPointView = findViewById(R.id.toPoint);
        mToLatLngView = findViewById(R.id.toLatlng);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        hMap = map;
        hMap.setOnMapClickListener(this);
        hMap.setOnMapLongClickListener(this);
        hMap.setOnCameraIdleListener(this);
        hMap.setMyLocationEnabled(true);
        hMap.setOnMyLocationButtonClickListener(this);
    }

    /**
     * Map click event
     */
    @Override
    public void onMapClick(LatLng latLng) {
        mTapView.setText("point=" + latLng + "is tapped");
        Point point = hMap.getProjection().toScreenLocation(latLng);
        mToPointView.setText("to point, point=" + point);
        LatLng newLatlng = hMap.getProjection().fromScreenLocation(point);
        mToLatLngView.setText("to latlng, latlng=" + newLatlng);
        VisibleRegion visibleRegion = hMap.getProjection().getVisibleRegion();
        Log.i(TAG, visibleRegion.toString());
    }

    /**
     * Map long click event
     */
    @Override
    public void onMapLongClick(LatLng point) {
        mTapView.setText("long pressed, point=" + point);
    }

    /**
     * Callback when the camera move ends
     */
    @Override
    public void onCameraIdle() {
        Toast.makeText(this, "camera move stoped", Toast.LENGTH_LONG).show();
    }

    /**
     * Map click event
     */
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }
}
