/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2023. All rights reserved.
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
 *  2020.1.3-Changed modify the import classes type and add some polyline demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */
package com.huawei.hms.maps.sample

import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.HuaweiMap
import android.os.Bundle
import android.util.Log
import android.view.View
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.model.LatLng
import android.widget.Toast

/**
 * Traffic related
 */
class TrafficDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "TrafficDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_demo)
        val fragment = supportFragmentManager.findFragmentById(R.id.support_map_fragment)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }

    /**
     * Set the trafficEnabled property of the map to false
     *
     * @param view
     */
    fun setTrafficEnabled(view: View?) {
        if (null != hMap) {
            val isTrafficEnabled = !hMap!!.isTrafficEnabled
            hMap?.isTrafficEnabled = isTrafficEnabled
            Toast.makeText(this, "setTrafficEnabled: $isTrafficEnabled", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Get the trafficEnabled property of the map
     *
     * @param view
     */
    fun isTrafficEnabled(view: View?) {
        if (null != hMap) {
            val isTrafficEnabled = hMap?.isTrafficEnabled
            Toast.makeText(this, "isTrafficEnabled: $isTrafficEnabled", Toast.LENGTH_SHORT).show()
        }
    }
}