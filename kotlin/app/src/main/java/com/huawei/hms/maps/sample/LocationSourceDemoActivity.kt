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
package com.huawei.hms.maps.sample

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.HuaweiMap.OnMapClickListener
import com.huawei.hms.maps.LocationSource
import com.huawei.hms.maps.LocationSource.OnLocationChangedListener
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.LatLng

/**
 * Get location source information
 */
class LocationSourceDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "LocationSourceDemoActiv"
    }

    private var pressLocationSource: PressLocationSource? = null

    /**
     * Report LocationSource for new location whenever the user taps on the map
     */
    private class PressLocationSource : LocationSource, OnMapClickListener {
        private var locationChangedListener: OnLocationChangedListener? = null
        private var mPaused = false
        override fun activate(listener: OnLocationChangedListener) {
            Log.d(TAG, "activate listener $listener")
            locationChangedListener = listener
        }

        override fun deactivate() {
            Log.d(TAG, "deactivate listener ")
            locationChangedListener = null
        }

        fun onPause() {
            mPaused = true
        }

        fun onResume() {
            mPaused = false
        }

        override fun onMapClick(latLng: LatLng) {
            if (locationChangedListener != null && !mPaused) {
                locationChangedListener?.onLocationChanged(getLocation(latLng))
            }
        }

        private fun getLocation(latLng: LatLng): Location {
            val location = Location("Provider")
            location.latitude = latLng.latitude
            location.longitude = latLng.longitude
            location.accuracy = 200f
            return location
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locationsource_demo)
        pressLocationSource = PressLocationSource()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapForLocationDemo) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        pressLocationSource?.onResume()
    }

    override fun onPause() {
        super.onPause()
        pressLocationSource?.onPause()
    }

    override fun onMapReady(map: HuaweiMap) {
        map.setOnMapClickListener(pressLocationSource)
        map.isMyLocationEnabled = true
        map.setLocationSource(pressLocationSource)
    }
}