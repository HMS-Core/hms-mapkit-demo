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
 *  2020.1.3-Changed modify the import classes type and add some circle demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
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
import com.huawei.hms.maps.model.Circle
import com.huawei.hms.maps.model.CircleOptions
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.sample.utils.CheckUtils

/**
 * circle related
 */
@Suppress("UNUSED_PARAMETER")
class CircleDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "CircleDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var mCircle: Circle? = null
    private lateinit var circleShown: TextView
    private lateinit var centerLatitude: EditText
    private lateinit var centerLongtitude: EditText
    private lateinit var circleRadius: EditText
    private lateinit var circleStokeWidth: EditText
    private lateinit var circleTag: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapInCircle) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        circleShown = findViewById(R.id.circleShown)
        centerLatitude = findViewById(R.id.centerLatitude)
        centerLongtitude = findViewById(R.id.centerLongtitude)
        circleRadius = findViewById(R.id.circleRadius)
        circleStokeWidth = findViewById(R.id.circleStokeWidth)
        circleTag = findViewById(R.id.circleTag)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 14f))
    }

    /**
     * remove the circle
     */
    fun removeCircle(view: View?) {
        if (null != mCircle) {
            mCircle?.remove()
        }
    }

    /**
     * add a circle on the map
     */
    fun addCircle(view: View?) {
        if (null != mCircle) {
            mCircle?.remove()
        }
        mCircle = hMap?.addCircle(CircleOptions().center(LatLng(48.893478, 2.334595))
                .radius(500.0)
                .fillColor(-0xff0001)
                .strokeWidth(10f)
                .strokeColor(Color.RED))
    }

    /**
     * Set the center of the circle
     */
    fun setCenter(v: View?) {
        if (null != mCircle) {
            var center: LatLng? = null
            if (!TextUtils.isEmpty(centerLatitude.text) && !TextUtils.isEmpty(centerLongtitude.text)) {
                val latitude = centerLatitude.text.toString().trim()
                val longtitude = centerLongtitude.text.toString().trim()
                center = LatLng(latitude.toDouble(), longtitude.toDouble())
            }
            try {
                mCircle?.center = center
            } catch (e: NullPointerException) {
                Log.e(TAG, "NullPointerException $e")
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Get the center coordinates
     */
    fun getCenter(v: View?) {
        if (null != mCircle) {
            circleShown.text = "Circle center is ${mCircle?.center.toString()}"
        }
    }

    /**
     * Set the radius of the circle
     */
    fun setRadius(v: View?) {
        if (null != mCircle) {
            val radius = circleRadius.text.toString().trim()
            if (CheckUtils.checkIsEdit(radius)) {
                Toast.makeText(this, "Please make sure the radius is Edited", Toast.LENGTH_SHORT).show()
            } else {
                if (!CheckUtils.checkIsRight(radius)) {
                    Toast.makeText(this, "Please make sure the radius is right", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        mCircle?.radius = radius.toDouble()
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(this, "IllegalArgumentException ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Get the radius of the circle
     */
    fun getRadius(v: View?) {
        if (null != mCircle) {
            circleShown.text = "Circle radius is ${mCircle?.radius}"
        }
    }

    /**
     * Get the fill color of the circle
     */
    fun setFillColor(v: View?) {
        if (null != mCircle) {
            mCircle?.fillColor = Color.RED
        }
    }

    /**
     * Set the fill color of the circle
     */
    fun getFillColor(v: View?) {
        if (null != mCircle) {
            circleShown.text = "Circle fill color is ${Integer.toHexString(mCircle!!.fillColor)}"
        }
    }

    var flag = false
    fun setStokeColor(v: View?) {
        if (null != mCircle) {
            if (flag) {
                mCircle?.strokeColor = Color.RED
                flag = false
            } else {
                mCircle?.strokeColor = Color.GRAY
                flag = true
            }
        }
    }

    /**
     * Get the outline color of the circle
     */
    fun getStokeColor(v: View?) {
        if (null != mCircle) {
            circleShown.text = "Circle stroke color is ${Integer.toHexString(mCircle!!.strokeColor)}"
        }
    }

    /**
     * Set the outline width of the circle
     */
    fun setWidth(v: View?) {
        if (null != mCircle) {
            val width = circleStokeWidth.text.toString().trim()
            if (CheckUtils.checkIsEdit(width)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show()
            } else {
                if (!CheckUtils.checkIsRight(width)) {
                    Toast.makeText(this, "Please make sure the width is right", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        mCircle?.strokeWidth = width.toFloat()
                    } catch (e: IllegalArgumentException) {
                        Log.e(TAG, "IllegalArgumentException $e")
                        Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Get the outline width of the circle
     */
    fun getWidth(v: View?) {
        if (null != mCircle) {
            circleShown.text = "Circle stroke width is ${mCircle?.strokeWidth}"
        }
    }

    /**
     * Set the tag of the circle
     */
    fun setTag(v: View?) {
        if (null != mCircle) {
            val tag = circleTag.text.toString().trim()
            if (CheckUtils.checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show()
            } else {
                mCircle?.setTag(tag)
            }
        }
    }

    /**
     * Get the tag of the circle
     */
    fun getTag(v: View?) {
        if (null != mCircle) {
            circleShown.text = mCircle?.tag.toString()
        }
    }

    /**
     * Set click event for circle
     */
    fun addClickEvent(v: View?) {
        if (null != mCircle) {
            hMap?.setOnCircleClickListener { circle ->
                if (mCircle == circle) {
                    Toast.makeText(applicationContext, "Circle is clicked.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * Set the circle clickable to true
     */
    fun setClickableTrue(v: View?) {
        if (null != mCircle) {
            mCircle?.isClickable = true
        }
    }

    /**
     * Set the circle clickable to false
     */
    fun setClickableFalse(v: View?) {
        if (null != mCircle) {
            mCircle?.isClickable = false
        }
    }
}
