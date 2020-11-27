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
 *  2020.1.3-Changed modify the import classes type and add some map display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */
package com.huawei.hms.maps.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * This shows how we create a basic activity with a map.
 */
class BasicMapDemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "BasicMapDemoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_demo)
    }

    /**
     * Start the createMapView activity
     */
    fun createMapView(view: View?) {
        Log.d(TAG, "createMapView: ")
        val i = Intent(this, MapViewDemoActivity::class.java)
        startActivity(i)
    }

    /**
     * Start the createMapViewCode activity
     */
    fun createMapViewCode(view: View?) {
        Log.d(TAG, "createMapViewCode: ")
        val i = Intent(this, MapViewCodeDemoActivity::class.java)
        startActivity(i)
    }

    /**
     * Start the createMapFragment activity
     */
    fun createMapFragment(view: View?) {
        Log.d(TAG, "createMapFragment: ")
        val i = Intent(this, MapFragmentDemoActivity::class.java)
        startActivity(i)
    }

    /**
     * Start the createMapFragmentCode activity
     */
    fun createMapFragmentCode(view: View?) {
        Log.d(TAG, "createMapFragmentCode: ")
        val i = Intent(this, MapFragmentCodeDemoActivity::class.java)
        startActivity(i)
    }

    /**
     * Start the createSupportMapFragment activity
     */
    fun createSupportMapFragment(view: View?) {
        Log.d(TAG, "createSupportMapFragment: ")
        val i = Intent(this, SupportMapDemoActivity::class.java)
        startActivity(i)
    }

    /**
     * Start the createSupportMapFragmentCode activity
     */
    fun createSupportMapFragmentCode(view: View?) {
        Log.d(TAG, "createSupportMapFragmentCode: ")
        val i = Intent(this, SupportMapCodeDemoActivity::class.java)
        startActivity(i)
    }
}