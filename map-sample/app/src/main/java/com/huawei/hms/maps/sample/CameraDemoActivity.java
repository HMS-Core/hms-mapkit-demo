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
 *  2020.1.3-Changed modify the import classes type and add some camera events.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMap.OnCameraIdleListener;
import com.huawei.hms.maps.HuaweiMap.OnCameraMoveListener;
import com.huawei.hms.maps.HuaweiMap.OnCameraMoveStartedListener;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * Show how to move a map camera
 */
public class CameraDemoActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,
    OnCameraMoveStartedListener, OnCameraMoveListener, OnCameraIdleListener {
    private static final String TAG = "CameraDemoActivity";

    public static final int REQUEST_CODE = 0X01;

    private static final String[] PERMISION =
        {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private static final float ZOOM_DELTA = 2.0f;

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private TextView cameraChange;

    private float mMaxZoom = 22.0f;

    private float mMinZoom = 0.0f;

    private EditText cameraLat;

    private EditText cameraLng;

    private EditText cameraZoom;

    private EditText cameraTilt;

    private EditText cameraBearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_demo);
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, PERMISION, REQUEST_CODE);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapInCamera);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        cameraLat = findViewById(R.id.cameraLat);
        cameraLng = findViewById(R.id.cameraLng);
        cameraZoom = findViewById(R.id.cameraZoom);
        cameraTilt = findViewById(R.id.cameraTilt);
        cameraBearing = findViewById(R.id.cameraBearing);
        Button btn1 = findViewById(R.id.animateCamera);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.getCameraPosition);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.moveCamera);
        btn3.setOnClickListener(this);
        Button btn4 = findViewById(R.id.ZoomBy);
        btn4.setOnClickListener(this);
        Button btn5 = findViewById(R.id.newLatLngBounds);
        btn5.setOnClickListener(this);
        Button btn6 = findViewById(R.id.setCameraPosition);
        btn6.setOnClickListener(this);

        cameraChange = findViewById(R.id.cameraChange);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " permission setting successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " permission setting failed", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    /**
     * Determine if you have the location permission
     */
    private boolean hasLocationPermission() {
        for (String permission : PERMISION) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine whether to turn on GPS
     */
    private boolean isGPSOpen(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = huaweiMap;
        if (isGPSOpen(this)) {
            hMap.setMyLocationEnabled(true);
            hMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            hMap.setMyLocationEnabled(false);
            hMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        hMap.setOnCameraMoveStartedListener(this);
        hMap.setOnCameraIdleListener(this);
        hMap.setOnCameraMoveListener(this);
        hMap.setOnMapLoadedCallback(new HuaweiMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.i(TAG, "onMapLoaded:successful");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (null == hMap) {
            Log.w(TAG, "map is null");
            return;
        }
        if (v.getId() == R.id.animateCamera) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(20, 120));
            Toast.makeText(this, hMap.getCameraPosition().target.toString(), Toast.LENGTH_LONG).show();
            hMap.animateCamera(cameraUpdate);
        }
        if (v.getId() == R.id.getCameraPosition) {
            CameraPosition position = hMap.getCameraPosition();
            Toast.makeText(getApplicationContext(), position.toString(), Toast.LENGTH_LONG).show();

            // Displays the maximum zoom level and minimum scaling level of the current camera.
            Log.i(TAG, position.toString());
            Log.i(TAG, "MaxZoomLevel:" + hMap.getMaxZoomLevel() + " MinZoomLevel:" + hMap.getMinZoomLevel());
        }
        if (R.id.moveCamera == v.getId()) {
            CameraPosition build = new CameraPosition.Builder().target(new LatLng(60, 60)).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(build);
            Toast.makeText(this, hMap.getCameraPosition().toString(), Toast.LENGTH_LONG).show();
            hMap.moveCamera(cameraUpdate);
        }
        if (R.id.ZoomBy == v.getId()) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(2);
            Toast.makeText(this, "amount = 2", Toast.LENGTH_LONG).show();
            hMap.moveCamera(cameraUpdate);
        }
        if (R.id.newLatLngBounds == v.getId()) {
            LatLng southwest = new LatLng(30, 60);
            LatLng northeast = new LatLng(60, 120);
            LatLngBounds latLngBounds = new LatLngBounds(southwest, northeast);
            Toast
                .makeText(this,
                    "southwest =" + southwest.toString() + " northeast=" + northeast.toString() + " padding=2",
                    Toast.LENGTH_LONG)
                .show();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 2);
            hMap.moveCamera(cameraUpdate);
        }
        if (R.id.setCameraPosition == v.getId()) {
            LatLng southwest = new LatLng(30, 60);
            CameraPosition cameraPosition =
                CameraPosition.builder().target(southwest).zoom(10).bearing(2.0f).tilt(2.5f).build();
            Toast.makeText(this, cameraPosition.toString(), Toast.LENGTH_LONG).show();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            hMap.moveCamera(cameraUpdate);
        }
    }

    /**
     * Callback when the camera starts moving
     */
    @Override
    public void onCameraMoveStarted(int reason) {
        Log.i(TAG, "onCameraMoveStarted: susccessful");
        if (reason == REASON_DEVELOPER_ANIMATION) {
            Log.i(TAG, "onCameraMove");
        }
    }

    /**
     * Callback when the camera move ends
     */
    @Override
    public void onCameraIdle() {
        cameraChange.setText(hMap.getCameraPosition().toString());
        Log.i(TAG, "onCameraIdle: sucessful");
    }

    /**
     * Set camera move callback
     */
    @Override
    public void onCameraMove() {
        Log.i(TAG, "onCameraMove: successful");
    }

    /**
     * Set the upper limit of camera zoom
     */
    public void onSetMaxZoomClamp(View view) {
        mMaxZoom -= ZOOM_DELTA;
        // Constrains the maximum zoom level.
        hMap.setMaxZoomPreference(mMaxZoom);
        Toast.makeText(this, "Max zoom preference set to: " + mMaxZoom, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set the lower limit of camera zoom
     */
    public void onSetMinZoomClamp(View view) {
        mMinZoom += ZOOM_DELTA;
        // Constrains the minimum zoom level.
        hMap.setMinZoomPreference(mMinZoom);
        Toast.makeText(this, "Min zoom preference set to: " + mMinZoom, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set the map camera based on the latitude and longitude, zoom factor, tilt angle, and rotation angle
     */
    public void setCameraPosition(View view) {
        try {
            LatLng latLng = null;
            float zoom = 2.0f;
            float bearing = 2.0f;
            float tilt = 2.0f;
            if (!TextUtils.isEmpty(cameraLng.getText()) && !TextUtils.isEmpty(cameraLat.getText())) {
                latLng = new LatLng(Float.valueOf(cameraLat.getText().toString().trim()),
                    Float.valueOf(cameraLng.getText().toString().trim()));
            }
            if (!TextUtils.isEmpty(cameraZoom.getText())) {
                zoom = Float.valueOf(cameraZoom.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(cameraBearing.getText())) {
                bearing = Float.valueOf(cameraBearing.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(cameraTilt.getText())) {
                tilt = Float.valueOf(cameraTilt.getText().toString().trim());
            }
            CameraPosition cameraPosition =
                CameraPosition.builder().target(latLng).zoom(zoom).bearing(bearing).tilt(tilt).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            hMap.moveCamera(cameraUpdate);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException " + e.toString());
            Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException " + e.toString());
            Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
        }
    }
}
