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
 *  2020.1.3-Changed modify the import classes type and add some groundOverlay demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */
package com.huawei.hms.maps.sample

import android.content.Context
import android.content.res.Configuration
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
 * DarkMode related
 */
class DarkModeDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var isDark = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_darkmode_demo)
        val fragment = supportFragmentManager.findFragmentById(R.id.support_map_fragment)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
        isDark = isSystemModeNight(this)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isDark != isSystemModeNight(this)) {
            isDark = isSystemModeNight(this)
            hMap?.isDark = isDark
        }
    }

    fun setDark(view: View?) {
        val result = !hMap!!.isDark
        hMap?.isDark = result
        Toast.makeText(this, "setDark: $result", Toast.LENGTH_SHORT).show()
    }

    fun isDark(view: View?) {
        val isDark = hMap?.isDark
        Toast.makeText(this, "isDark: $isDark", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAG = DarkModeDemoActivity::class.java.simpleName
        fun isSystemModeNight(context: Context): Boolean {
            val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return Configuration.UI_MODE_NIGHT_YES == currentNightMode
        }
    }
}