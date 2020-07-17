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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This shows how we create a basic activity with a map.
 */
public class BasicMapDemoActivity extends AppCompatActivity {
    private static final String TAG = "BasicMapDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_demo);
    }

    /**
     * Start the createMapView activity
     */
    public void createMapView(View view) {
        Log.d(TAG, "createMapView: ");
        Intent i = new Intent(this, MapViewDemoActivity.class);
        startActivity(i);
    }

    /**
     * Start the createMapViewCode activity
     */
    public void createMapViewCode(View view) {
        Log.d(TAG, "createMapViewCode: ");
        Intent i = new Intent(this, MapViewCodeDemoActivity.class);
        startActivity(i);
    }

    /**
     * Start the createMapFragment activity
     */
    public void createMapFragment(View view) {
        Log.d(TAG, "createMapFragment: ");
        Intent i = new Intent(this, MapFragmentDemoActivity.class);
        startActivity(i);
    }

    /**
     * Start the createMapFragmentCode activity
     */
    public void createMapFragmentCode(View view) {
        Log.d(TAG, "createMapFragmentCode: ");
        Intent i = new Intent(this, MapFragmentCodeDemoActivity.class);
        startActivity(i);
    }

    /**
     * Start the createSupportMapFragment activity
     */
    public void createSupportMapFragment(View view) {
        Log.d(TAG, "createSupportMapFragment: ");
        Intent i = new Intent(this, SupportMapDemoActivity.class);
        startActivity(i);
    }

    /**
     * Start the createSupportMapFragmentCode activity
     */
    public void createSupportMapFragmentCode(View view) {
        Log.d(TAG, "createSupportMapFragmentCode: ");
        Intent i = new Intent(this, SupportMapCodeDemoActivity.class);
        startActivity(i);
    }
}
