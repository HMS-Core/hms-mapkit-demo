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
 *  2020.1.3-Changed modify the import classes type and add some map  style display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */
package com.huawei.hms.maps.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.MapStyleOptions

/**
 * StyleMap related
 */
class StyleMapDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "StyleMapDemoActivity"
    }

    private var hMap: HuaweiMap? = null
    private var mSupportMapFragment: SupportMapFragment? = null
    private lateinit var edtStyleId: EditText
    private lateinit var edtPreviewId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style_map_demo)
        edtStyleId = findViewById(R.id.edt_style_id)
        edtPreviewId = findViewById(R.id.edt_preview_id)

        val fragmentManager = supportFragmentManager
        mSupportMapFragment = fragmentManager.findFragmentByTag("support_map_fragment") as SupportMapFragment?

        if (mSupportMapFragment == null) {
            val huaweiMapOptions = HuaweiMapOptions()
            // please replace "styleId" with style ID field value in
            huaweiMapOptions.styleId("styleId")
            // please replace "previewId" with preview ID field value in
            huaweiMapOptions.previewId("previewId")
            mSupportMapFragment = SupportMapFragment.newInstance(huaweiMapOptions)
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.map_container_layout, mSupportMapFragment!!, "support_map_fragment")
            fragmentTransaction.commit()
        }

        mSupportMapFragment?.getMapAsync(this)
        mSupportMapFragment?.onAttach(this)
    }

    override fun onMapReady(map: HuaweiMap) {
        Log.d(TAG, "onMapReady: ")
        hMap = map
        hMap?.uiSettings?.isMyLocationButtonEnabled = false
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }

    /**
     * set map style:night
     */
    fun setNightStyle(view: View?) {
        val styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night_hms)
        hMap?.setMapStyle(styleOptions)
    }

    /**
     * set map style:grayscale
     */
    fun setGrayscaleStyle(view: View?) {
        val styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grayscale_hms)
        hMap?.setMapStyle(styleOptions)
    }

    /**
     * set map style:retro
     */
    fun setRetroStyle(view: View?) {
        val styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro_hms)
        hMap?.setMapStyle(styleOptions)
    }

    /**
     * set style ID
     */
    fun setStyleId(view: View?) {
        val styleIdStr = edtStyleId.text.toString()
        if (styleIdStr.isEmpty()) {
            Toast.makeText(this, "Please make sure the style ID is edited", Toast.LENGTH_SHORT).show()
            return
        }
        hMap?.setStyleId(styleIdStr)
    }

    /**
     * set preview ID
     */
    fun setPreviewId(view: View?) {
        val previewIdStr = edtPreviewId.text.toString()
        if (previewIdStr.isEmpty()) {
            Toast.makeText(this, "Please make sure the preview ID is edited", Toast.LENGTH_SHORT).show()
            return
        }
        hMap?.previewId(previewIdStr)
    }
}