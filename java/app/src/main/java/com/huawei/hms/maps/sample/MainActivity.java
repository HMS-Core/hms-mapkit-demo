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
import android.widget.Button;

import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.sample.utils.MapUtils;

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

        findViewById(R.id.Camera).setOnClickListener(this);
        findViewById(R.id.BasicMap).setOnClickListener(this);
        findViewById(R.id.GestureDemo).setOnClickListener(this);
        findViewById(R.id.ControlsDemo).setOnClickListener(this);
        findViewById(R.id.CircleDemo).setOnClickListener(this);
        findViewById(R.id.PolygonDemo).setOnClickListener(this);
        findViewById(R.id.PolylineDemo).setOnClickListener(this);
        findViewById(R.id.GroudOverlayDemo).setOnClickListener(this);
        findViewById(R.id.LiteModeDemo).setOnClickListener(this);
        findViewById(R.id.MoreLanguageDemo).setOnClickListener(this);
        findViewById(R.id.MapFunctions).setOnClickListener(this);
        findViewById(R.id.AddMarkerDemo).setOnClickListener(this);
        findViewById(R.id.MarkerClusteringDemo).setOnClickListener(this);
        findViewById(R.id.EventsDemo).setOnClickListener(this);
        findViewById(R.id.MapStyle).setOnClickListener(this);
        findViewById(R.id.LocationSourceDemo).setOnClickListener(this);
        findViewById(R.id.HeatMapDemo).setOnClickListener(this);
        findViewById(R.id.RoutePlanningDemo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (R.id.Camera == view.getId()) {
            Log.i(TAG, "onClick: cameraDemo");
            Intent i = new Intent(this, CameraDemoActivity.class);
            startActivity(i);
        } else if (R.id.GestureDemo == view.getId()) {
            Log.i(TAG, "onClick: GestureDemoActivity");
            Intent intent = new Intent(this, GestureDemoActivity.class);
            startActivity(intent);

        } else if (R.id.ControlsDemo == view.getId()) {
            Log.i(TAG, "onClick: ControlsDemoActivity");
            Intent intent = new Intent(this, ControlsDemoActivity.class);
            startActivity(intent);
        } else if (R.id.CircleDemo == view.getId()) {
            Log.i(TAG, "onClick: CircleDemoActivity");
            Intent intent = new Intent(this, CircleDemoActivity.class);
            startActivity(intent);

        } else if (R.id.PolygonDemo == view.getId()) {
            Log.i(TAG, "onClick: PolygonDemoActivity");
            Intent intent = new Intent(this, PolygonDemoActivity.class);
            startActivity(intent);

        } else if (R.id.PolylineDemo == view.getId()) {
            Log.i(TAG, "onClick: GestureDemoActivity");
            Intent intent = new Intent(this, PolylineDemoActivity.class);
            startActivity(intent);

        } else if (R.id.GroudOverlayDemo == view.getId()) {
            Log.i(TAG, "onClick: GroundOverlayDemoActivity");
            Intent intent = new Intent(this, GroundOverlayDemoActivity.class);
            startActivity(intent);

        } else if (R.id.LiteModeDemo == view.getId()) {
            Log.i(TAG, "onClick: LiteModeDemoActivity");
            Intent intent = new Intent(this, LiteModeDemoActivity.class);
            startActivity(intent);

        } else if (R.id.MoreLanguageDemo == view.getId()) {
            Log.i(TAG, "onClick: MoreLanguageDemoActivity");
            Intent intent = new Intent(this, MoreLanguageDemoActivity.class);
            startActivity(intent);

        } else if (R.id.MapFunctions == view.getId()) {
            Log.i(TAG, "onClick: MapFunctionsDemoActivity");
            Intent intent = new Intent(this, MapFunctionsDemoActivity.class);
            startActivity(intent);
        } else if (R.id.BasicMap == view.getId()) {
            Log.i(TAG, "onClick: BasicMap");
            Intent i = new Intent(this, BasicMapDemoActivity.class);
            startActivity(i);
        } else if (R.id.AddMarkerDemo == view.getId()) {
            Log.i(TAG, "onClick: AddMarkerDemo");
            Intent i = new Intent(this, MarkerDemoActivity.class);
            startActivity(i);
        }  else if (R.id.MarkerClusteringDemo == view.getId()) {
            Log.i(TAG, "onClick: MarkerClusteringDemo");
            Intent i = new Intent(this, MarkerClusteringDemoActivity.class);
            startActivity(i);
        } else if (R.id.EventsDemo == view.getId()) {
            Log.i(TAG, "onClick: EventsDemo");
            Intent i = new Intent(this, EventsDemoActivity.class);
            startActivity(i);
        } else if (R.id.LocationSourceDemo == view.getId()) {
            Log.i(TAG, "onClick: LocationSourceDemo");
            Intent i = new Intent(this, LocationSourceDemoActivity.class);
            startActivity(i);
        } else if (R.id.MapStyle == view.getId()) {
            Log.i(TAG, "onClick: StyleMapDemo");
            Intent i = new Intent(this, StyleMapDemoActivity.class);
            startActivity(i);

        }  else if (R.id.HeatMapDemo == view.getId()) {
            Log.i(TAG, "onClick: HeatMapDemo");
            Intent i = new Intent(this, HeatMapDemoActivity.class);
            startActivity(i);

        } else if (R.id.RoutePlanningDemo == view.getId()) {
            Log.i(TAG, "onClick: RoutePlanningDemo");
            Intent i = new Intent(this, RoutePlanningDemoActivity.class);
            startActivity(i);

        } else {
            Log.i(TAG, "onClick:  " + view.getId());
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