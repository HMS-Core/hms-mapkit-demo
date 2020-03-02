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
 *  2020.1.3-Changed modify the import classes type and add some locationSource demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnMapClickListener;
import com.huawei.hms.maps.LocationSource;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Get location source information
 */
public class LocationSourceDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "LocationSourceDemoActiv";

    private PressLocationSource pressLocationSource;

    /**
     * Report LocationSource for new location whenever the user taps on the map
     */
    private static class PressLocationSource implements LocationSource, OnMapClickListener {

        private OnLocationChangedListener locationChangedListener;

        private boolean mPaused;

        @Override
        public void activate(OnLocationChangedListener listener) {
            Log.d(TAG, "activate listener " + listener);
            locationChangedListener = listener;
        }

        @Override
        public void deactivate() {
            Log.d(TAG, "deactivate listener ");
            locationChangedListener = null;
        }

        public void onPause() {
            mPaused = true;
        }

        public void onResume() {
            mPaused = false;
        }

        @Override
        public void onMapClick(LatLng latLng) {
            if (locationChangedListener != null && !mPaused) {
                locationChangedListener.onLocationChanged(getLocation(latLng));
            }
        }

        private Location getLocation(LatLng latLng) {
            Location location = new Location("Provider");
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
            location.setAccuracy(200);
            return location;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationsource_demo);

        pressLocationSource = new PressLocationSource();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapForLocationDemo);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressLocationSource.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pressLocationSource.onPause();
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        map.setOnMapClickListener(pressLocationSource);
        map.setMyLocationEnabled(true);
        map.setLocationSource(pressLocationSource);
    }
}
