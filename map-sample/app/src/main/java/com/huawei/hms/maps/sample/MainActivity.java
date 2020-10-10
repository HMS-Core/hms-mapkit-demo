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
 *  2020.1.3-Changed modify the import classes type and add some map display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * Home page only provides function module entry for map
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private static final String[] RUNTIME_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasPermissions(this, RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE);
        }

        Button btn1 = findViewById(R.id.Camera);
        Button btn2 = findViewById(R.id.BasicMap);
        btn2.setOnClickListener(this);
        Button GestureDemo = findViewById(R.id.GestureDemo);
        Button ControlsDemo = findViewById(R.id.ControlsDemo);
        Button CircleDemo = findViewById(R.id.CircleDemo);
        Button PolygonDemo = findViewById(R.id.PolygonDemo);
        Button PolylineDemo = findViewById(R.id.PolylineDemo);
        Button GroudOverlayDemo = findViewById(R.id.GroudOverlayDemo);
        Button LiteModeDemo = findViewById(R.id.LiteModeDemo);
        Button MoreLanguageDemo = findViewById(R.id.MoreLanguageDemo);
        Button MapFounctions = findViewById(R.id.MapFunctions);
        Button addMarkerDemo = findViewById(R.id.AddMarkerDemo);
        Button eventsDemo = findViewById(R.id.EventsDemo);
        Button MapStyleDemo = findViewById(R.id.MapStyle);
        Button locationSourceDemo = findViewById(R.id.LocationSourceDemo);
        Button RoutePlanningDemo = findViewById(R.id.RoutePlanningDemo);

        btn1.setOnClickListener(this);
        GestureDemo.setOnClickListener(this);
        ControlsDemo.setOnClickListener(this);
        CircleDemo.setOnClickListener(this);
        PolygonDemo.setOnClickListener(this);
        PolylineDemo.setOnClickListener(this);
        GroudOverlayDemo.setOnClickListener(this);
        LiteModeDemo.setOnClickListener(this);
        MoreLanguageDemo.setOnClickListener(this);
        MapFounctions.setOnClickListener(this);
        addMarkerDemo.setOnClickListener(this);
        eventsDemo.setOnClickListener(this);
        MapStyleDemo.setOnClickListener(this);
        locationSourceDemo.setOnClickListener(this);
        RoutePlanningDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.Camera == v.getId()) {
            Log.i(TAG, "onClick: cameraDemo");
            Intent i = new Intent(this, CameraDemoActivity.class);
            startActivity(i);
        } else if (R.id.GestureDemo == v.getId()) {
            Log.i(TAG, "onClick: GestureDemoActivity");
            Intent intent = new Intent(this, GestureDemoActivity.class);
            startActivity(intent);

        } else if (R.id.ControlsDemo == v.getId()) {
            Log.i(TAG, "onClick: ControlsDemoActivity");
            Intent intent = new Intent(this, ControlsDemoActivity.class);
            startActivity(intent);
        } else if (R.id.CircleDemo == v.getId()) {
            Log.i(TAG, "onClick: CircleDemoActivity");
            Intent intent = new Intent(this, CircleDemoActivity.class);
            startActivity(intent);

        } else if (R.id.PolygonDemo == v.getId()) {
            Log.i(TAG, "onClick: PolygonDemoActivity");
            Intent intent = new Intent(this, PolygonDemoActivity.class);
            startActivity(intent);

        } else if (R.id.PolylineDemo == v.getId()) {
            Log.i(TAG, "onClick: GestureDemoActivity");
            Intent intent = new Intent(this, PolylineDemoActivity.class);
            startActivity(intent);

        } else if (R.id.GroudOverlayDemo == v.getId()) {
            Log.i(TAG, "onClick: GroundOverlayDemoActivity");
            Intent intent = new Intent(this, GroundOverlayDemoActivity.class);
            startActivity(intent);

        } else if (R.id.LiteModeDemo == v.getId()) {
            Log.i(TAG, "onClick: LiteModeDemoActivity");
            Intent intent = new Intent(this, LiteModeDemoActivity.class);
            startActivity(intent);

        } else if (R.id.MoreLanguageDemo == v.getId()) {
            Log.i(TAG, "onClick: MoreLanguageDemoActivity");
            Intent intent = new Intent(this, MoreLanguageDemoActivity.class);
            startActivity(intent);

        } else if (R.id.MapFunctions == v.getId()) {
            Log.i(TAG, "onClick: MapFunctionsDemoActivity");
            Intent intent = new Intent(this, MapFunctionsDemoActivity.class);
            startActivity(intent);
        } else if (R.id.BasicMap == v.getId()) {
            Log.i(TAG, "onClick: BasicMap");
            Intent i = new Intent(this, BasicMapDemoActivity.class);
            startActivity(i);
        } else if (R.id.AddMarkerDemo == v.getId()) {
            Log.i(TAG, "onClick: AddMarkerDemo");
            Intent i = new Intent(this, MarkerDemoActivity.class);
            startActivity(i);
        } else if (R.id.EventsDemo == v.getId()) {
            Log.i(TAG, "onClick: EventsDemo");
            Intent i = new Intent(this, EventsDemoActivity.class);
            startActivity(i);
        } else if (R.id.LocationSourceDemo == v.getId()) {
            Log.i(TAG, "onClick: LocationSourceDemo");
            Intent i = new Intent(this, LocationSourceDemoActivity.class);
            startActivity(i);
        } else if (R.id.MapStyle == v.getId()) {
            Intent i = new Intent(this, StyleMapDemoActivity.class);
            startActivity(i);

        } else if (R.id.RoutePlanningDemo == v.getId()) {
            Intent i = new Intent(this, RoutePlanningDemoActivity.class);
            startActivity(i);

        } else {
            Log.i(TAG, "onClick:  " + v.getId());
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}