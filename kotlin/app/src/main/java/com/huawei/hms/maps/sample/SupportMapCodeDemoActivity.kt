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
 *  2020.1.3-Changed modify the import classes type and add some SupportMapFragment demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.CameraPosition
import com.huawei.hms.maps.model.LatLng

/**
 * Create a simple activity with a map and a marker on the map.
 */
class SupportMapCodeDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "SupportMapCodeActivity"
    }

    private var hMap: HuaweiMap? = null
    private var mSupportMapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supportmapcode_demo)

        val fragmentManager = supportFragmentManager
        mSupportMapFragment = fragmentManager.findFragmentByTag("support_map_fragment") as SupportMapFragment?

        if (mSupportMapFragment == null) {
            val cameraPosition = CameraPosition.builder().target(LatLng(43.0, 2.0)).zoom(2f).bearing(2.0f).tilt(2.5f).build()
            val huaweiMapOptions = HuaweiMapOptions().camera(cameraPosition)
            mSupportMapFragment = SupportMapFragment.newInstance(huaweiMapOptions)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.frame_supportmapcode, mSupportMapFragment!!, "support_map_fragment")
            fragmentTransaction.commit()
        }

        mSupportMapFragment?.getMapAsync(this)
        mSupportMapFragment?.onAttach(this)
    }

    override fun onMapReady(map: HuaweiMap) {
        Log.d(TAG, "onMapReady: ")
        hMap = map
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }
}
