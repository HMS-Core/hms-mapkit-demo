/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
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
 *  2020.1.3-Changed modify the import classes type and add some polyline demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Traffic related
 */
public class TrafficDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "TrafficDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.support_map_fragment);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));
    }

    /**
     * Set the trafficEnabled property of the map to false
     *
     * @param view
     */
    public void setTrafficEnabled(View view) {
        if (null != hMap) {
            boolean isTrafficEnabled = !hMap.isTrafficEnabled();
            hMap.setTrafficEnabled(isTrafficEnabled);
            Toast.makeText(this, "setTrafficEnabled: " + isTrafficEnabled, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get the trafficEnabled property of the map
     *
     * @param view
     */
    public void isTrafficEnabled(View view) {
        if (null != hMap) {
            boolean isTrafficEnabled = hMap.isTrafficEnabled();
            Toast.makeText(this, "isTrafficEnabled: " + isTrafficEnabled, Toast.LENGTH_SHORT).show();
        }
    }
}
