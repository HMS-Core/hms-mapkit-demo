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
 *  2020.1.3-Changed modify the import classes type and add some polygon display demos.
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
import com.huawei.hms.maps.model.Polygon
import com.huawei.hms.maps.model.PolygonOptions
import com.huawei.hms.maps.sample.utils.CheckUtils
import com.huawei.hms.maps.sample.utils.CheckUtils.checkIsEdit
import com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight
import com.huawei.hms.maps.sample.utils.MapUtils

/**
 * about polygon
 */
class PolygonDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "PolygonDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var mPolygon: Polygon? = null
    private lateinit var polygonShown: TextView
    private lateinit var oneLatitude: EditText
    private lateinit var oneLongtitude: EditText
    private lateinit var polygonTag: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polygon_demo)
        val fragment = supportFragmentManager.findFragmentById(R.id.mapInPolygon)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
        polygonShown = findViewById(R.id.polygonShown)
        oneLatitude = findViewById(R.id.oneLatitude)
        oneLongtitude = findViewById(R.id.oneLongtitude)
        polygonTag = findViewById(R.id.polygonTag)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }

    /**
     * Add polygons to the map
     */
    fun addPolygon(view: View?) {
        if (null != mPolygon) {
            mPolygon?.remove()
        }
        mPolygon = hMap?.addPolygon(PolygonOptions().addAll(MapUtils.createRectangle(LatLng(48.893478, 2.334595), 0.1, 0.1))
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK))
        hMap?.setOnPolygonClickListener { Log.i(TAG, "addPolygon and onPolygonClick start ") }
    }

    /**
     * Remove the polygon
     */
    fun removePolygon(view: View?) {
        if (null != mPolygon) {
            mPolygon?.remove()
        }
    }

    /**
     * Set the point position information of the polygon
     */
    fun setPoints(v: View?) {
        if (null != mPolygon) {
            val latitude = oneLatitude.text.toString().trim()
            val longtitude = oneLongtitude.text.toString().trim()
            if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is Edited", Toast.LENGTH_SHORT).show()
            } else {
                if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    mPolygon?.points = MapUtils
                            .createRectangle(LatLng(latitude.toDouble(), longtitude.toDouble()), 0.5, 0.5)
                }
            }
        }
    }

    /**
     * Get the point position information of the polygon
     */
    fun getPoints(v: View?) {
        if (null != mPolygon) {
            val stringBuilder = StringBuilder()
            for (i in mPolygon!!.points.toTypedArray().indices) {
                stringBuilder.append(mPolygon!!.points[i].toString())
            }
            Toast.makeText(this, "Polygon points is $stringBuilder", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Set the outline color of the polygon
     */
    fun setStokeColor(v: View?) {
        if (null != mPolygon) {
            mPolygon?.strokeColor = Color.YELLOW
        }
    }

    /**
     * Get the outline color of the polygon
     */
    fun getStokeColor(v: View?) {
        if (null != mPolygon) {
            polygonShown.text = "Polygon color is ${Integer.toHexString(mPolygon!!.strokeColor)}"
        }
    }

    /**
     * Set the fill color of the polygon
     */
    fun setFillColor(v: View?) {
        if (null != mPolygon) {
            mPolygon?.fillColor = Color.CYAN
        }
    }

    /**
     * Get the fill color of the polygon
     */
    fun getFillColor(v: View?) {
        if (null != mPolygon) {
            polygonShown.text = "Polygon color is ${Integer.toHexString(mPolygon!!.fillColor)}"
        }
    }

    /**
     * Set the tag of the polygon
     */
    fun setTag(v: View?) {
        if (null != mPolygon) {
            val tag = polygonTag.text.toString().trim()
            if (CheckUtils.checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show()
            } else {
                mPolygon?.tag = tag
            }
        }
    }

    /**
     * Get the tag of the polygon
     */
    fun getTag(v: View?) {
        if (null != mPolygon) {
            polygonShown.text = if (mPolygon?.tag == null) "Tag is null" else mPolygon?.tag.toString()
        }
    }

    /**
     * Add polygon click event
     */
    fun addClickEvent(v: View?) {
        if (null != mPolygon) {
            hMap?.setOnPolygonClickListener { Toast.makeText(applicationContext, "Polygon is clicked.", Toast.LENGTH_LONG).show() }
        }
    }

    /**
     * Set polygons clickable
     */
    fun setClickableTrue(v: View?) {
        if (null != mPolygon) {
            mPolygon?.isClickable = true
        }
    }

    /**
     * Set polygons are not clickable
     */
    fun setClickableFalse(v: View?) {
        if (null != mPolygon) {
            mPolygon?.isClickable = false
        }
    }
}