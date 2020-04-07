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
 *  2020.1.3-Changed modify the import classes type and add some map  style display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapFragment;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MapStyleOptions;

/**
 * StyleMap related
 */
public class StyleMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "StyleMapDemoActivity";

    private HuaweiMap hMap;

    private MapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_map_demo);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment_mapstyle);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hMap = map;
        hMap.getUiSettings().setMyLocationButtonEnabled(false);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));
    }

    public void setNightStyle(View view) {
        MapStyleOptions styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night);
        hMap.setMapStyle(styleOptions);
    }

    public void setSimpleStyle(View view) {
        MapStyleOptions styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_simple);
        hMap.setMapStyle(styleOptions);
    }
}
