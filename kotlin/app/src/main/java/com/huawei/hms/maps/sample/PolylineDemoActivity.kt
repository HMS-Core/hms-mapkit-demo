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
 *  2020.1.3-Changed modify the import classes type and add some polyline demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.graphics.Color
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
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Polyline
import com.huawei.hms.maps.model.PolylineOptions
import com.huawei.hms.maps.sample.utils.CheckUtils
import com.huawei.hms.maps.sample.utils.CheckUtils.checkIsEdit
import com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight
import com.huawei.hms.maps.sample.utils.MapUtils

/**
 * about polyline
 */
class PolylineDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "PolylineDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var mPolyline: Polyline? = null
    private lateinit var polylineShown: TextView
    private lateinit var oneLatitude: EditText
    private lateinit var oneLongtitude: EditText
    private lateinit var polylineStokeWidth: EditText
    private lateinit var polylineTag: EditText
    private val points: MutableList<LatLng> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polyline_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapInPolyline) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        polylineShown = findViewById(R.id.polylineShown)
        oneLatitude = findViewById(R.id.oneLatitude)
        oneLongtitude = findViewById(R.id.oneLongtitude)
        polylineStokeWidth = findViewById(R.id.polylineStokeWidth)
        polylineTag = findViewById(R.id.polylineTag)
        points.add(MapUtils.HUAWEI_CENTER)
        points.add(MapUtils.APARTMENT_CENTER)
        points.add(MapUtils.EPARK_CENTER)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }

    /**
     * Add polyline to the map
     */
    fun addPolyline(view: View?) {
        if (null != mPolyline) {
            mPolyline?.remove()
        }
        mPolyline = hMap?.addPolyline(
                PolylineOptions().add(MapUtils.FRANCE, MapUtils.FRANCE1, MapUtils.FRANCE2, MapUtils.FRANCE3)
                        .color(Color.BLUE)
                        .width(3f))
        hMap?.setOnPolylineClickListener { Log.i(TAG, "onMapReady:onPolylineClick ") }
    }

    /**
     * Remove the polyline
     */
    fun removePolyline(view: View?) {
        if (null != mPolyline) {
            mPolyline?.remove()
        }
        points.clear()
        points.add(MapUtils.HUAWEI_CENTER)
        points.add(MapUtils.APARTMENT_CENTER)
        points.add(MapUtils.EPARK_CENTER)
    }

    /**
     * Set the point position information of the polyline
     */
    fun setOnePoint(v: View?) {
        if (null != mPolyline) {
            val latitude = oneLatitude.text.toString().trim()
            val longtitude = oneLongtitude.text.toString().trim()
            if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is Edited", Toast.LENGTH_SHORT).show()
            } else {
                if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    points.add(LatLng(latitude.toDouble(), longtitude.toDouble()))
                    mPolyline?.points = points
                }
            }
        }
    }

    /**
     * Get the point position information of the polyline
     */
    fun getPoints(v: View?) {
        if (null != mPolyline) {
            val stringBuilder = StringBuilder()
            for (i in mPolyline!!.points.toTypedArray().indices) {
                stringBuilder.append(mPolyline!!.points[i].toString())
            }
            Toast.makeText(this, "Polyline points is $stringBuilder", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Set the outline color of the polyline
     */
    fun setStokeColor(v: View?) {
        if (null != mPolyline) {
            mPolyline?.color = Color.YELLOW
        }
    }

    /**
     * Get the outline color of the polyline
     */
    fun getStokeColor(v: View?) {
        if (null != mPolyline) {
            polylineShown.text = "Polyline color is ${Integer.toHexString(mPolyline!!.color)}"
        }
    }

    /**
     * Set the width of the polyline
     */
    fun setWidth(v: View?) {
        if (null != mPolyline) {
            val width = polylineStokeWidth.text.toString().trim()
            if (checkIsEdit(width)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show()
            } else {
                if (!checkIsRight(width)) {
                    Toast.makeText(this, "Please make sure the width is right", Toast.LENGTH_SHORT).show()
                } else {
                    if (width.toFloat() < 0.0f) {
                        Toast
                                .makeText(this,
                                        "Please make sure the width is right, this value must be non-negative",
                                        Toast.LENGTH_SHORT)
                                .show()
                        return
                    }
                    mPolyline?.width = width.toFloat()
                }
            }
        }
    }

    /**
     * Get the width of the polyline
     */
    fun getWidth(v: View?) {
        if (null != mPolyline) {
            polylineShown.text = "Polyline width is ${mPolyline?.width}"
        }
    }

    /**
     * Set the tag of the polyline
     */
    fun setTag(v: View?) {
        if (null != mPolyline) {
            val tag = polylineTag.text.toString().trim()
            if (CheckUtils.checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show()
            } else {
                mPolyline?.tag = tag
            }
        }
    }

    /**
     * Get the tag of the polyline
     */
    fun getTag(v: View?) {
        if (null != mPolyline) {
            polylineShown.text = if (mPolyline?.tag == null) "Tag is null" else mPolyline?.tag.toString()
        }
    }

    /**
     * Add polyline click event
     */
    fun addClickEvent(v: View?) {
        if (null != mPolyline) {
            hMap?.setOnPolylineClickListener { Toast.makeText(applicationContext, "Polyline is clicked.", Toast.LENGTH_LONG).show() }
        }
    }

    /**
     * Set polyline clickable
     */
    fun setClickableTrue(v: View?) {
        if (null != mPolyline) {
            mPolyline?.isClickable = true
        }
    }

    /**
     * Set polyline are not clickable
     */
    fun setClickableFalse(v: View?) {
        if (null != mPolyline) {
            mPolyline?.isClickable = false
        }
    }

    /**
     * Set the colors of the polyline
     *
     * @param view view
     */
    fun setColorValues(view: View?) {
        if (null != mPolyline) {
            val colorValueList: MutableList<Int> = ArrayList()
            colorValueList.add(Color.RED)
            colorValueList.add(Color.GREEN)
            colorValueList.add(Color.BLUE)
            mPolyline!!.colorValues = colorValueList
        }
    }

    /**
     * Set the gradient of the polyline
     *
     * @param view view
     */
    fun setGradientTrue(view: View?) {
        if (null != mPolyline) {
            mPolyline!!.isGradient = true
        }
    }

    /**
     * Set the gradient of the polyline
     *
     * @param view view
     */
    fun setGradientFalse(view: View?) {
        if (null != mPolyline) {
            mPolyline!!.isGradient = false
        }
    }
}