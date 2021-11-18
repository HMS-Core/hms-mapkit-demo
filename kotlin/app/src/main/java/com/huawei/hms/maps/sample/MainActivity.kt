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

package com.huawei.hms.maps.sample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.sample.utils.MapUtils

/**
 * Home page only provides function module entry for map
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val TAG = "MainActivity"
        private val RUNTIME_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET)
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!hasPermissions(this, *RUNTIME_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE)
        }

        // please replace "Your API key" with api_key field value in
        // agconnect-services.json if the field is null.
        MapsInitializer.setApiKey(MapUtils.API_KEY)
        findViewById<Button>(R.id.Camera).setOnClickListener(this)
        findViewById<Button>(R.id.BasicMap).setOnClickListener(this)
        findViewById<Button>(R.id.GestureDemo).setOnClickListener(this)
        findViewById<Button>(R.id.ControlsDemo).setOnClickListener(this)
        findViewById<Button>(R.id.CircleDemo).setOnClickListener(this)
        findViewById<Button>(R.id.PolygonDemo).setOnClickListener(this)
        findViewById<Button>(R.id.PolylineDemo).setOnClickListener(this)
        findViewById<Button>(R.id.GroudOverlayDemo).setOnClickListener(this)
        findViewById<Button>(R.id.LiteModeDemo).setOnClickListener(this)
        findViewById<Button>(R.id.MoreLanguageDemo).setOnClickListener(this)
        findViewById<Button>(R.id.MapFunctions).setOnClickListener(this)
        findViewById<Button>(R.id.AddMarkerDemo).setOnClickListener(this)
        findViewById<Button>(R.id.MarkerClusteringDemo).setOnClickListener(this)
        findViewById<Button>(R.id.EventsDemo).setOnClickListener(this)
        findViewById<Button>(R.id.MapStyle).setOnClickListener(this)
        findViewById<Button>(R.id.LocationSourceDemo).setOnClickListener(this)
        findViewById<Button>(R.id.HeatMapDemo).setOnClickListener(this)
        findViewById<Button>(R.id.RoutePlanningDemo).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Camera -> {
                Log.i(TAG, "onClick: cameraDemo")
                startActivity(Intent(this, CameraDemoActivity::class.java))
            }
            R.id.GestureDemo -> {
                Log.i(TAG, "onClick: GestureDemoActivity")
                startActivity(Intent(this, GestureDemoActivity::class.java))
            }
            R.id.ControlsDemo -> {
                Log.i(TAG, "onClick: ControlsDemoActivity")
                startActivity(Intent(this, ControlsDemoActivity::class.java))
            }
            R.id.CircleDemo -> {
                Log.i(TAG, "onClick: CircleDemoActivity")
                startActivity(Intent(this, CircleDemoActivity::class.java))
            }
            R.id.PolygonDemo -> {
                Log.i(TAG, "onClick: PolygonDemoActivity")
                startActivity(Intent(this, PolygonDemoActivity::class.java))
            }
            R.id.PolylineDemo -> {
                Log.i(TAG, "onClick: GestureDemoActivity")
                startActivity(Intent(this, PolylineDemoActivity::class.java))
            }
            R.id.GroudOverlayDemo -> {
                Log.i(TAG, "onClick: GroundOverlayDemoActivity")
                startActivity(Intent(this, GroundOverlayDemoActivity::class.java))
            }
            R.id.LiteModeDemo -> {
                Log.i(TAG, "onClick: LiteModeDemoActivity")
                startActivity(Intent(this, LiteModeDemoActivity::class.java))
            }
            R.id.MoreLanguageDemo -> {
                Log.i(TAG, "onClick: MoreLanguageDemoActivity")
                startActivity(Intent(this, MoreLanguageDemoActivity::class.java))
            }
            R.id.MapFunctions -> {
                Log.i(TAG, "onClick: MapFunctionsDemoActivity")
                startActivity(Intent(this, MapFunctionsDemoActivity::class.java))
            }
            R.id.BasicMap -> {
                Log.i(TAG, "onClick: BasicMap")
                startActivity(Intent(this, BasicMapDemoActivity::class.java))
            }
            R.id.AddMarkerDemo -> {
                Log.i(TAG, "onClick: AddMarkerDemo")
                startActivity(Intent(this, MarkerDemoActivity::class.java))
            }
            R.id.MarkerClusteringDemo -> {
                Log.i(TAG, "onClick: MarkerClusteringDemo")
                startActivity(Intent(this, MarkerClusteringDemoActivity::class.java))
            }
            R.id.EventsDemo -> {
                Log.i(TAG, "onClick: EventsDemo")
                startActivity(Intent(this, EventsDemoActivity::class.java))
            }
            R.id.LocationSourceDemo -> {
                Log.i(TAG, "onClick: LocationSourceDemo")
                startActivity(Intent(this, LocationSourceDemoActivity::class.java))
            }
            R.id.MapStyle -> {
                Log.i(TAG, "onClick: StyleMapDemo")
                startActivity(Intent(this, StyleMapDemoActivity::class.java))
            }
            R.id.HeatMapDemo -> {
                Log.i(TAG, "onClick: HeatMapDemo")
                startActivity(Intent(this, HeatMapDemoActivity::class.java))
            }
            R.id.RoutePlanningDemo -> {
                Log.i(TAG, "onClick: RoutePlanningDemo")
                startActivity(Intent(this, RoutePlanningDemoActivity::class.java))
            }
            else -> {
            }
        }
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
}