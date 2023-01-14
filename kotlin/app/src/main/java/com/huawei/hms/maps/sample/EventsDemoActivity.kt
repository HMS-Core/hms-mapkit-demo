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
 *  2020.1.3-Changed modify the import classes type and add some events demo.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.HuaweiMap.*
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.LatLng

/**
 * This shows how to listen to some HuaweiMap events
 */
class EventsDemoActivity : AppCompatActivity(), OnMapClickListener, OnMapLongClickListener
        , OnCameraIdleListener, OnMapReadyCallback, OnMyLocationButtonClickListener {
    companion object {
        private const val TAG = "EventsDemoActivity"
    }

    private lateinit var mTapView: TextView
    private lateinit var mToLatLngView: TextView
    private lateinit var mToPointView: TextView
    private var hMap: HuaweiMap? = null
    private var mSupportMapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        mTapView = findViewById(R.id.tap_text)
        mToPointView = findViewById(R.id.toPoint)
        mToLatLngView = findViewById(R.id.toLatlng)
    }

    override fun onMapReady(map: HuaweiMap) {
        hMap = map
        hMap?.setOnMapClickListener(this)
        hMap?.setOnMapLongClickListener(this)
        hMap?.setOnCameraIdleListener(this)
        hMap?.isMyLocationEnabled = true
        hMap?.setOnMyLocationButtonClickListener(this)
    }

    /**
     * Map click event
     */
    override fun onMapClick(latLng: LatLng) {
        mTapView.text = "point=$latLng is tapped"
        val point = hMap?.projection?.toScreenLocation(latLng)
        mToPointView.text = "to point, point=$point"
        val newLatlng = hMap?.projection?.fromScreenLocation(point)
        mToLatLngView.text = "to latlng, latlng=$newLatlng"
        val visibleRegion = hMap?.projection?.visibleRegion
        Log.i(TAG, visibleRegion.toString())
    }

    /**
     * Map long click event
     */
    override fun onMapLongClick(point: LatLng) {
        mTapView.text = "long pressed, point=$point"
    }

    /**
     * Callback when the camera move ends
     */
    override fun onCameraIdle() {
        Toast.makeText(this, "camera move stoped", Toast.LENGTH_LONG).show()
    }

    /**
     * Map click event
     */
    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }
}
