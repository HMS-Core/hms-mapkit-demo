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
 *  2020.1.3-Changed modify the import classes type and add some map function demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */
package com.huawei.hms.maps.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight
import com.huawei.hms.maps.sample.utils.MapUtils

/**
 * Basical functions
 */

@SuppressLint("LongLogTag")
class MapFunctionsDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "MapFunctionsDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private lateinit var left: EditText
    private lateinit var right: EditText
    private lateinit var top: EditText
    private lateinit var bottom: EditText
    private lateinit var minZoomlevel: EditText
    private lateinit var maxZoomlevel: EditText
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_map_founctions_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapInFunctions) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        left = findViewById(R.id.paddingleft)
        right = findViewById(R.id.paddingright)
        top = findViewById(R.id.paddingtop)
        bottom = findViewById(R.id.paddingbottom)
        text = findViewById(R.id.founctionsshow)
        minZoomlevel = findViewById(R.id.minZoomlevel)
        maxZoomlevel = findViewById(R.id.maxZoomlevel)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.resetMinMaxZoomPreference()
    }

    /**
     * Get the maximum zoom level parameter
     */
    fun getMaxZoomLevel(view: View?) {
        text.text = hMap?.maxZoomLevel.toString()
    }

    /**
     * Get the minimum zoom level parameter
     */
    fun getMinZoomLevel(view: View?) {
        text.text = hMap?.minZoomLevel.toString()
    }

    /**
     * Get map type
     */
    fun getMapType(view: View?) {
        text.text = if (hMap?.mapType == MapUtils.MAP_TYPE_NONE) "MAP_TYPE_NONE" else "MAP_TYPE_NORMAL"
    }

    /**
     * Set map type
     */
    fun setMapType(view: View?) {
        if (null != hMap) {
            synchronized(hMap!!) {
                if (hMap?.mapType == MapUtils.MAP_TYPE_NORMAL) {
                    hMap?.mapType = HuaweiMap.MAP_TYPE_NONE
                } else {
                    hMap?.mapType = HuaweiMap.MAP_TYPE_NORMAL
                }
            }
        }
    }

    /**
     * Get 3D mode settings
     */
    fun is3DMode(view: View?) {
        text.text = hMap?.isBuildingsEnabled.toString()
    }

    /**
     * Turn on the 3D switch
     */
    fun Set3DMode(view: View?) {
        hMap?.isBuildingsEnabled = !hMap?.isBuildingsEnabled!!
    }

    /**
     * Set the maximum value of the desired camera zoom level
     */
    fun setMaxZoomPreference(view: View?) {
        val text = maxZoomlevel.text.toString().trim()
        if (text.isEmpty()) {
            Toast.makeText(this, "Please make sure the maxZoom is Edited", Toast.LENGTH_SHORT).show()
        } else {
            if (!checkIsRight(text)) {
                Toast.makeText(this, "Please make sure the maxZoom is right", Toast.LENGTH_SHORT).show()
                return
            }
            if (text.toFloat() > MapUtils.MAX_ZOOM_LEVEL
                    || text.toFloat() < MapUtils.MIN_ZOOM_LEVEL) {
                Toast
                        .makeText(this, "The zoom level ranges from ${MapUtils.MIN_ZOOM_LEVEL} to ${MapUtils.MAX_ZOOM_LEVEL}.",
                                Toast.LENGTH_SHORT)
                        .show()
            } else {
                val maxZoom = maxZoomlevel.text.toString().toFloat()
                Log.i(TAG, "setMaxZoomPreference: $maxZoom")
                hMap?.setMaxZoomPreference(maxZoom)
            }
        }
    }

    /**
     * Test the maximum zoom parameter
     */
    fun testMaxZoom(view: View?) {
        val cameraUpdate = CameraUpdateFactory.zoomBy(1.0f)
        hMap?.moveCamera(cameraUpdate)
    }

    /**
     * Set the minimum value of the desired camera zoom level
     */
    fun setMinZoomPreference(view: View?) {
        val text = minZoomlevel.text.toString().trim()
        if (text.isEmpty()) {
            Toast.makeText(this, "Please make sure the minZoom is Edited", Toast.LENGTH_SHORT).show()
        } else {
            if (!checkIsRight(text)) {
                Toast.makeText(this, "Please make sure the minZoom is right", Toast.LENGTH_SHORT).show()
                return
            }
            if (text.toFloat() > MapUtils.MAX_ZOOM_LEVEL
                    || text.toFloat() < MapUtils.MIN_ZOOM_LEVEL) {
                Toast
                        .makeText(this, "The zoom level ranges from ${MapUtils.MIN_ZOOM_LEVEL} to ${MapUtils.MAX_ZOOM_LEVEL}.",
                                Toast.LENGTH_SHORT)
                        .show()
            } else {
                hMap?.setMinZoomPreference(minZoomlevel.text.toString().toFloat())
            }
        }
    }

    /**
     * Remove the previously set zoom level upper and lower boundary values
     */
    fun resetMinMaxZoomPreference(view: View?) {
        hMap?.resetMinMaxZoomPreference()
    }

    /**
     * Set the map border fill width for the map
     */
    fun setPadding(view: View?) {
        val leftString = left.text.toString().trim()
        val topString = top.text.toString().trim()
        val rightString = right.text.toString().trim()
        val bottomString = bottom.text.toString().trim()
        if (leftString.isEmpty() || topString.isEmpty() || rightString.isEmpty() || bottomString.isEmpty()) {
        } else {
            if (!checkIsRight(leftString) || !checkIsRight(topString) || !checkIsRight(rightString) || !checkIsRight(bottomString)) {
                Toast.makeText(this, "Please make sure the padding value is right", Toast.LENGTH_SHORT).show()
            } else {
                hMap?.setPadding(Integer.valueOf(left.text.toString()),
                        Integer.valueOf(top.text.toString()), Integer.valueOf(right.text.toString()),
                        Integer.valueOf(bottom.text.toString()))
            }
        }
    }
}