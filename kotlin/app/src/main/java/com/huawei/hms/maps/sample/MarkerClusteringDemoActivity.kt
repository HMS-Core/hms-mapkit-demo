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

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.MarkerOptions
import com.huawei.hms.maps.sample.utils.MyItemReader
import org.json.JSONException

/**
 * MarkerClustering related
 */
@SuppressLint("LongLogTag")
class MarkerClusteringDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "MarkerClusteringDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var isAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_clustering_demo)
        val fragment = supportFragmentManager.findFragmentById(R.id.mapfragment_markerdemo)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.4934813, -0.5013019988494029), 9f))
    }

    /**
     * Add marker to the map
     */
    fun addMarkers(view: View?) {
        if (null == hMap) {
            return
        }
        if (isAdded) {
            Toast.makeText(this, "Markers has already added.", Toast.LENGTH_LONG).show()
            return
        }
        val inputStream = resources.openRawResource(R.raw.marker_200)
        var markerOptionsList: List<MarkerOptions>? = null
        try {
            markerOptionsList = MyItemReader().read(inputStream)
        } catch (e: JSONException) {
            Log.e(TAG, "JSONException.")
        }
        if (null != markerOptionsList) {
            for (item in markerOptionsList) {
                hMap?.addMarker(item)
            }
            isAdded = true
        }
    }

    /**
     * enable clustering
     */
    fun enableMarkerClustering(view: View?) {
        hMap?.setMarkersClustering(true)
    }

    /**
     * enable clustering
     */
    fun disableMarkerClustering(view: View?) {
        hMap?.setMarkersClustering(false)
    }

    /**
     * clear map
     */
    fun clearMap(view: View?) {
        hMap?.clear()
        isAdded = false
    }

    /**
     * set default
     */
    fun resetMarkerCluster(view: View?) {
        hMap?.uiSettings?.setMarkerClusterIcon(null)
    }

    /**
     * set marker cluster icon
     */
    fun setMarkerClusterIcon(view: View?) {
        hMap?.uiSettings?.setMarkerClusterIcon(BitmapDescriptorFactory.fromResource(R.drawable.niuyouguo))
    }

    /**
     * set marker cluster text color
     */
    fun setMarkerClusterTextColor(view: View?) {
        hMap?.uiSettings?.setMarkerClusterTextColor(Color.RED)
    }

    /**
     * set marker cluster color
     */
    fun setMarkerClusterColor(view: View?) {
        hMap?.uiSettings?.setMarkerClusterColor(Color.RED)
    }
}