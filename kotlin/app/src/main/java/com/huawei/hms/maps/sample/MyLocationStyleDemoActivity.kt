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
 *  2020.1.3-Changed modify the import classes type and add some support languages demo.
 *                  Huawei Technologies Co., Ltd.
 *
 */
package com.huawei.hms.maps.sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.HuaweiMap
import android.widget.TextView
import android.os.Bundle
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.model.LatLng
import android.widget.Toast
import com.huawei.hms.maps.model.MyLocationStyle
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.huawei.hms.feature.dynamic.ObjectWrapper

/**
 * about MyLocationStyle
 */
@SuppressLint("LongLogTag")
class MyLocationStyleDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "MyLocationStyleDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private lateinit var myLocationStyleShown: TextView
    private lateinit var imageViewIcon: ImageView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mylocationstyle_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapInPolyline) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        myLocationStyleShown = findViewById(R.id.myLocationStyleShown)
        imageViewIcon = findViewById(R.id.imageViewIcon)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.uiSettings?.isMyLocationButtonEnabled = true
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
        hMap?.setOnMyLocationButtonClickListener {
            Toast.makeText(this@MyLocationStyleDemoActivity,
                    "MyLocation button clicked", Toast.LENGTH_SHORT).show()
            false
        }
        hMap?.setOnMyLocationClickListener { location ->
            Toast.makeText(this@MyLocationStyleDemoActivity, "onMyLocationClick: ("
                    + location.latitude +
                    "," + location.longitude + ")", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set my location style.
     *
     * @param view view
     */
    fun setMyLocationStyle(view: View?) {
        // resourceId为图标资源
        val resourceId = R.drawable.avocado
        val style = MyLocationStyle()
                .anchor(0.5f, 0.5f)
                .radiusFillColor(Color.RED)
                .myLocationIcon(BitmapDescriptorFactory.fromResource(resourceId))
        hMap?.myLocationStyle = style
    }

    /**
     * Get my location style.
     *
     * @param view view
     */
    fun getMyLocationStyle(view: View?) {
        val myLocationStyle = hMap?.myLocationStyle
        if (myLocationStyle == null) {
            Toast.makeText(this, "Please call the 'setMyLocationStyle' interface first.", Toast.LENGTH_SHORT).show()
            return
        }
        val locationIcon = myLocationStyle.myLocationIcon
        val bitmap = ObjectWrapper.unwrap<Bitmap>(locationIcon.getObject())
        imageViewIcon.setImageBitmap(bitmap)
        val anchorU = myLocationStyle.anchorU
        val anchorV = myLocationStyle.anchorV
        val color = myLocationStyle.radiusFillColor
        val text = """
             getMyLocationStyle:
             anchorU:$anchorU,anchorV:$anchorV,color:${Integer.toHexString(color)},icon:$bitmap
             """.trimIndent()
        myLocationStyleShown.text = text
    }
}