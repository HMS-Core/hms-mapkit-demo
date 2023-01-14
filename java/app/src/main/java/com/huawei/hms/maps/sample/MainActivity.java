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

import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.sample.utils.MapUtils;

import androidx.annotation.NonNull;
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

		// this line is needed before using huawei map kit, since version 'com.huawei.hms:maps:6.5.0.301'
        MapsInitializer.initialize(this);
		
        // please replace "Your API key" with api_key field value in
        // agconnect-services.json if the field is null.
        MapsInitializer.setApiKey(MapUtils.API_KEY);

        findViewById(R.id.button_camera).setOnClickListener(this);
        findViewById(R.id.button_basic_map).setOnClickListener(this);
        findViewById(R.id.button_gesture).setOnClickListener(this);
        findViewById(R.id.button_gesture_controls).setOnClickListener(this);
        findViewById(R.id.button_circle).setOnClickListener(this);
        findViewById(R.id.button_polygon).setOnClickListener(this);
        findViewById(R.id.button_polyline).setOnClickListener(this);
        findViewById(R.id.button_ground_overlay).setOnClickListener(this);
        findViewById(R.id.button_lite_mode).setOnClickListener(this);
        findViewById(R.id.button_more_language).setOnClickListener(this);
        findViewById(R.id.button_map_functions).setOnClickListener(this);
        findViewById(R.id.button_marker).setOnClickListener(this);
        findViewById(R.id.button_marker_clustering).setOnClickListener(this);
        findViewById(R.id.button_events).setOnClickListener(this);
        findViewById(R.id.button_map_style).setOnClickListener(this);
        findViewById(R.id.button_location_source).setOnClickListener(this);
        findViewById(R.id.button_heat_map).setOnClickListener(this);
        findViewById(R.id.button_route_planning).setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View view) {
        if (R.id.button_camera == view.getId()) {
            startActivityByClass(CameraDemoActivity.class);
        } else if (R.id.button_gesture == view.getId()) {
            startActivityByClass(GestureDemoActivity.class);
        } else if (R.id.button_gesture_controls == view.getId()) {
            startActivityByClass(ControlsDemoActivity.class);
        } else if (R.id.button_circle == view.getId()) {
            startActivityByClass(CircleDemoActivity.class);
        } else if (R.id.button_polygon == view.getId()) {
            startActivityByClass(PolygonDemoActivity.class);
        } else if (R.id.button_polyline == view.getId()) {
            startActivityByClass(PolylineDemoActivity.class);
        } else if (R.id.button_ground_overlay == view.getId()) {
            startActivityByClass(GroundOverlayDemoActivity.class);
        } else if (R.id.button_lite_mode == view.getId()) {
            startActivityByClass(LiteModeDemoActivity.class);
        } else if (R.id.button_more_language == view.getId()) {
            startActivityByClass(MoreLanguageDemoActivity.class);
        } else if (R.id.button_map_functions == view.getId()) {
            startActivityByClass(MapFunctionsDemoActivity.class);
        } else if (R.id.button_basic_map == view.getId()) {
            startActivityByClass(BasicMapDemoActivity.class);
        } else if (R.id.button_marker == view.getId()) {
            startActivityByClass(MarkerDemoActivity.class);
        }  else if (R.id.button_marker_clustering == view.getId()) {
            startActivityByClass(MarkerClusteringDemoActivity.class);
        } else if (R.id.button_events == view.getId()) {
            startActivityByClass(EventsDemoActivity.class);
        } else if (R.id.button_map_style == view.getId()) {
            startActivityByClass(StyleMapDemoActivity.class);
        } else if (R.id.button_location_source == view.getId()) {
            startActivityByClass(LocationSourceDemoActivity.class);
        } else if (R.id.button_heat_map == view.getId()) {
            startActivityByClass(HeatMapDemoActivity.class);
        } else if (R.id.button_route_planning == view.getId()) {
            startActivityByClass(RoutePlanningDemoActivity.class);
        }
    }

    private void startActivityByClass(@NonNull Class<?> cls) {
        Log.i(TAG, "onClick: " + cls.getSimpleName());
        Intent intent = new Intent(this, cls);
        startActivity(intent);
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