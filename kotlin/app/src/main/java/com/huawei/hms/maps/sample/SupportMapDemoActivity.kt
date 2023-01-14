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

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions

/**
 * Create a simple activity with a map and a marker on the map.
 */
@Suppress("UNUSED_PARAMETER")
class SupportMapDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "SupportMapDemoActivity"
        private val Beijing = LatLng(48.893478, 2.334595)
        private val Shanghai = LatLng(48.7, 2.12)
    }

    private var hMap: HuaweiMap? = null
    private var mBeijing: Marker? = null
    private var mShanghai: Marker? = null
    private var mSupportMapFragment: SupportMapFragment? = null
    private var mVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supportmapfragment_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.supportMap) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: HuaweiMap) {
        Log.d(TAG, "onMapReady: ")
        hMap = map
        hMap?.uiSettings?.isCompassEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 14f))
        hMap?.setOnMapLongClickListener { Log.d(TAG, "onMapLongClick: latLng " + " please input latLng") }
    }

    fun addMarker(view: View?) {
        if (mBeijing == null && mShanghai == null) {
            // Uses a colored icon.
            mBeijing = hMap?.addMarker(MarkerOptions().position(Beijing).title("Beijing").clusterable(true))
            mShanghai = hMap?.addMarker(MarkerOptions().position(Shanghai)
                    .alpha(0.8f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)))
        }
        if (null != mBeijing) {
            mBeijing?.title = "hello"
            mBeijing?.snippet = "world"
            mBeijing?.tag = "huaweimap"
            mBeijing?.isDraggable = true
        }
        if (null != mShanghai) {
            mShanghai?.title = "Hello"
            mShanghai?.isDraggable = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mSupportMapFragment?.onSaveInstanceState(outState)
    }

    fun setVisiable(view: View?) {
        if (null != mBeijing) {
            if (mVisible) {
                mBeijing?.isVisible = true
                mVisible = false
            } else {
                mBeijing?.isVisible = false
                mVisible = true
            }
        }
    }

    fun setAlpha(view: View?) {
        if (null != mBeijing) {
            if (mVisible) {
                mBeijing?.alpha = 0.5f
                mVisible = false
            } else {
                mBeijing?.alpha = 1.0f
                mVisible = true
            }
        }
    }

    fun setFlat(view: View?) {
        if (null != mBeijing) {
            if (mVisible) {
                mBeijing?.isFlat = true
                mVisible = false
            } else {
                mBeijing?.isFlat = false
                mVisible = true
            }
        }
    }

    fun setZIndex(view: View?) {
        if (null != mBeijing) {
            if (mVisible) {
                mBeijing?.zIndex = 20f
                mVisible = false
            } else {
                mBeijing?.zIndex = -20f
                mVisible = true
            }
        }
    }

    fun setRotation(view: View?) {
        if (null != mBeijing) {
            if (mVisible) {
                mBeijing?.rotation = 30.0f
                mVisible = false
            } else {
                mBeijing?.rotation = 60.0f
                mVisible = true
            }
        }
    }

    fun removeMarker(view: View?) {
        if (null != mBeijing) {
            mBeijing?.remove()
            mBeijing = null
        }
        if (null != mShanghai) {
            mShanghai?.remove()
            mShanghai = null
        }
    }

    fun showInfoWindow(view: View?) {
        if (null != mBeijing) {
            mVisible = if (mVisible) {
                mBeijing?.showInfoWindow()
                false
            } else {
                mBeijing?.hideInfoWindow()
                true
            }
        }
    }

    fun setAnchor(view: View?) {
        if (null != mBeijing) {
            mBeijing?.setMarkerAnchor(0.9f, 0.9f)
        }
    }

    fun setIcon(view: View?) {
        if (null != mBeijing) {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.badge_tr)
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
            mBeijing?.setIcon(bitmapDescriptor)
        }
    }
}
