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
 *  2020.1.3-Changed modify the import classes type and add some gesture controls demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.UiSettings

/**
 * about gesture
 */
class GestureDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "GestureDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var mUiSettings: UiSettings? = null
    private lateinit var mMyLocationButtonCheckbox: CheckBox
    private lateinit var mMyLocationLayerCheckbox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestures_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapInGestures) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        mMyLocationButtonCheckbox = findViewById<View>(R.id.isShowMylocationButton) as CheckBox
        mMyLocationLayerCheckbox = findViewById<View>(R.id.isMyLocationLayerEnabled) as CheckBox
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = false
        hMap?.uiSettings?.isCompassEnabled = false
        hMap?.uiSettings?.isZoomControlsEnabled = false
        hMap?.uiSettings?.isMyLocationButtonEnabled = false
        mUiSettings = hMap?.uiSettings
    }

    /**
     * Set map zoom button available
     */
    fun setZoomButtonsEnabled(v: View) {
        mUiSettings?.isZoomControlsEnabled = (v as CheckBox).isChecked
    }

    /**
     * Set compass available
     */
    fun setCompassEnabled(v: View) {
        mUiSettings?.isCompassEnabled = (v as CheckBox).isChecked
    }

    /**
     * Set my location button is available
     */
    fun setMyLocationButtonEnabled(v: View?) {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMyLocationLayerCheckbox.isChecked) {
                mUiSettings?.isMyLocationButtonEnabled = mMyLocationButtonCheckbox.isChecked
            } else {
                Toast.makeText(this, "Please open My Location Layer first", Toast.LENGTH_LONG).show()
                mMyLocationButtonCheckbox.isChecked = false
            }
        } else {
            Toast.makeText(this,
                    "System positioning permission was not obtained, please turn on system positioning permission first",
                    Toast.LENGTH_LONG).show()
            mMyLocationButtonCheckbox.isChecked = false
        }
    }

    /**
     * Set my location layer available
     */
    fun setMyLocationLayerEnabled(v: View?) {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            hMap?.isMyLocationEnabled = mMyLocationLayerCheckbox.isChecked
        } else {
            mMyLocationLayerCheckbox.isChecked = false
        }
    }

    /**
     * Set scroll gestures available
     */
    fun setScrollGesturesEnabled(v: View) {
        mUiSettings?.isScrollGesturesEnabled = (v as CheckBox).isChecked
    }

    /**
     * Set zoom gestures available
     */
    fun setZoomGesturesEnabled(v: View) {
        mUiSettings?.isZoomGesturesEnabled = (v as CheckBox).isChecked
    }

    /**
     * Set tilt gestures available
     */
    fun setTiltGesturesEnabled(v: View) {
        mUiSettings?.isTiltGesturesEnabled = (v as CheckBox).isChecked
    }

    /**
     * Set the rotation gesture available
     */
    fun setRotateGesturesEnabled(v: View) {
        mUiSettings?.isRotateGesturesEnabled = (v as CheckBox).isChecked
    }
}