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
package com.huawei.hms.maps.sample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.HuaweiMap.*
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.CameraPosition
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.LatLngBounds
import com.huawei.hms.maps.sample.utils.MapUtils

/**
 * Show how to move a map camera
 */
class CameraDemoActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener, OnCameraMoveStartedListener, OnCameraMoveListener, OnCameraIdleListener {
    companion object {
        private const val TAG = "CameraDemoActivity"
        const val REQUEST_CODE = 0X01
        private val PERMISION = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        private const val ZOOM_DELTA = 2.0f
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var mMaxZoom = 20.0f
    private var mMinZoom = 3.0f

    private var hMap: HuaweiMap? = null
    private lateinit var cameraChange: TextView
    private lateinit var cameraLat: EditText
    private lateinit var cameraLng: EditText
    private lateinit var cameraZoom: EditText
    private lateinit var cameraTilt: EditText
    private lateinit var cameraBearing: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_demo)
        if (!hasLocationPermission()) {
            ActivityCompat.requestPermissions(this, PERMISION, REQUEST_CODE)
        }
        val fragment = supportFragmentManager.findFragmentById(R.id.mapInCamera)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
        cameraLat = findViewById(R.id.cameraLat)
        cameraLng = findViewById(R.id.cameraLng)
        cameraZoom = findViewById(R.id.cameraZoom)
        cameraTilt = findViewById(R.id.cameraTilt)
        cameraBearing = findViewById(R.id.cameraBearing)
        val btn1 = findViewById<Button>(R.id.animateCamera)
        btn1.setOnClickListener(this)
        val btn2 = findViewById<Button>(R.id.getCameraPosition)
        btn2.setOnClickListener(this)
        val btn3 = findViewById<Button>(R.id.moveCamera)
        btn3.setOnClickListener(this)
        val btn4 = findViewById<Button>(R.id.ZoomBy)
        btn4.setOnClickListener(this)
        val btn5 = findViewById<Button>(R.id.newLatLngBounds)
        btn5.setOnClickListener(this)
        val btn6 = findViewById<Button>(R.id.setCameraPosition)
        btn6.setOnClickListener(this)
        cameraChange = findViewById(R.id.cameraChange)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " permission setting successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, permissions[i] + " permission setting failed", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }

    /**
     * Determine if you have the location permission
     */
    private fun hasLocationPermission(): Boolean {
        for (permission in PERMISION) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * Determine whether to turn on GPS
     */
    private fun isGPSOpen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }

    override fun onMapReady(huaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = huaweiMap
        if (isGPSOpen(this)) {
            hMap?.isMyLocationEnabled = true
            hMap?.uiSettings?.isMyLocationButtonEnabled = true
        } else {
            hMap?.isMyLocationEnabled = false
            hMap?.uiSettings?.isMyLocationButtonEnabled = false
        }
        hMap?.setOnCameraMoveStartedListener(this)
        hMap?.setOnCameraIdleListener(this)
        hMap?.setOnCameraMoveListener(this)
        hMap?.setOnMapLoadedCallback { Log.i(TAG, "onMapLoaded:successful") }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.animateCamera -> {
                val cameraUpdate = CameraUpdateFactory.newLatLng(LatLng(20.0, 120.0))
                Toast.makeText(this, hMap?.cameraPosition?.target.toString(), Toast.LENGTH_LONG).show()
                hMap?.animateCamera(cameraUpdate)
            }
            R.id.getCameraPosition -> {
                val position = hMap?.cameraPosition
                Toast.makeText(applicationContext, position.toString(), Toast.LENGTH_LONG).show()

                // Displays the maximum zoom level and minimum scaling level of the current camera.
                Log.i(TAG, position.toString())
                Log.i(TAG, "MaxZoomLevel:" + hMap?.maxZoomLevel + " MinZoomLevel:" + hMap?.minZoomLevel)
            }
            R.id.moveCamera -> {
                val build = CameraPosition.Builder().target(LatLng(60.0, 60.0)).build()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(build)
                Toast.makeText(this, hMap?.cameraPosition.toString(), Toast.LENGTH_LONG).show()
                hMap?.moveCamera(cameraUpdate)
            }
            R.id.ZoomBy -> {
                val cameraUpdate = CameraUpdateFactory.zoomBy(2f)
                Toast.makeText(this, "amount = 2", Toast.LENGTH_LONG).show()
                hMap?.moveCamera(cameraUpdate)
            }
            R.id.newLatLngBounds -> {
                val southwest = LatLng(30.0, 60.0)
                val northeast = LatLng(60.0, 120.0)
                val latLngBounds = LatLngBounds(southwest, northeast)
                Toast
                        .makeText(this,
                                "southwest =$southwest northeast=$northeast padding=2",
                                Toast.LENGTH_LONG)
                        .show()
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 2)
                hMap?.moveCamera(cameraUpdate)
            }
            R.id.setCameraPosition -> {
                val southwest = LatLng(30.0, 60.0)
                val cameraPosition = CameraPosition.builder().target(southwest).zoom(10f).bearing(2.0f).tilt(2.5f).build()
                Toast.makeText(this, cameraPosition.toString(), Toast.LENGTH_LONG).show()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                hMap?.moveCamera(cameraUpdate)
            }
            else -> {
            }
        }
    }

    /**
     * Callback when the camera starts moving
     */
    override fun onCameraMoveStarted(reason: Int) {
        Log.i(TAG, "onCameraMoveStarted: susccessful")
        if (reason == OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {
            Log.i(TAG, "onCameraMove")
        }
    }

    /**
     * Callback when the camera move ends
     */
    override fun onCameraIdle() {
        cameraChange.text = hMap?.cameraPosition.toString()
        Log.i(TAG, "onCameraIdle: sucessful")
    }

    /**
     * Set camera move callback
     */
    override fun onCameraMove() {
        Log.i(TAG, "onCameraMove: successful")
    }

    /**
     * Set the upper limit of camera zoom
     */
    fun onSetMaxZoomClamp(view: View?) {
        mMaxZoom -= ZOOM_DELTA
        if (mMaxZoom < MapUtils.MIN_ZOOM_LEVEL) {
            Toast
                    .makeText(this, "The minimum zoom level is ${MapUtils.MIN_ZOOM_LEVEL}, and cannot be decreased.",
                            Toast.LENGTH_SHORT)
                    .show()
            mMaxZoom = MapUtils.MIN_ZOOM_LEVEL
            hMap?.setMaxZoomPreference(mMaxZoom)
            return
        }
        // Constrains the maximum zoom level.
        hMap?.setMaxZoomPreference(mMaxZoom)
        Toast.makeText(this, "Max zoom preference set to: $mMaxZoom", Toast.LENGTH_SHORT).show()
    }

    /**
     * Set the lower limit of camera zoom
     */
    fun onSetMinZoomClamp(view: View?) {
        mMinZoom += ZOOM_DELTA
        if (mMinZoom > MapUtils.MAX_ZOOM_LEVEL) {
            Toast
                    .makeText(this, "The maximum zoom level is ${MapUtils.MAX_ZOOM_LEVEL}, and cannot be increased.",
                            Toast.LENGTH_SHORT)
                    .show()
            mMinZoom = MapUtils.MAX_ZOOM_LEVEL
            hMap?.setMinZoomPreference(mMinZoom)
            return
        }
        // Constrains the minimum zoom level.
        hMap?.setMinZoomPreference(mMinZoom)
        Toast.makeText(this, "Min zoom preference set to: $mMinZoom", Toast.LENGTH_SHORT).show()
    }

    /**
     * Set the map camera based on the latitude and longitude, zoom factor, tilt angle, and rotation angle
     */
    fun setCameraPosition(view: View?) {
        try {
            var latLng: LatLng? = null
            var zoom = 2.0f
            var bearing = 2.0f
            var tilt = 2.0f
            if (!cameraLng.text.isNullOrEmpty() && !cameraLat.text.isNullOrEmpty()) {
                latLng = LatLng(cameraLat.text.toString().trim().toDouble(), cameraLng.text.toString().trim().toDouble())
            }
            if (!cameraZoom.text.isNullOrEmpty()) {
                zoom = cameraZoom.text.toString().trim().toFloat()
            }
            if (!cameraBearing.text.isNullOrEmpty()) {
                bearing = cameraBearing.text.toString().trim().toFloat()
            }
            if (!cameraTilt.text.isNullOrEmpty()) {
                tilt = cameraTilt.text.toString().trim().toFloat()
            }
            val cameraPosition = CameraPosition.builder().target(latLng).zoom(zoom).bearing(bearing).tilt(tilt).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            hMap?.moveCamera(cameraUpdate)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "IllegalArgumentException $e")
            Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            Log.e(TAG, "NullPointerException $e")
            Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show()
        }
    }
}