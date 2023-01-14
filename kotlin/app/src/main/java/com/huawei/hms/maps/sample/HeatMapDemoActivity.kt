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
 *  2020.1.3-Changed modify the import classes type and add some groundOverlay demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.HeatMap
import com.huawei.hms.maps.model.HeatMapOptions
import com.huawei.hms.maps.model.HeatMapOptions.RadiusUnit
import java.util.*

/**
 * about HeatMap
 */
@Suppress("UNUSED_PARAMETER")
class HeatMapDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "HeatMapDemoActivity"
        private const val BAR_MAX = 100
    }

    private var hMap: HuaweiMap? = null

    private var mSupportMapFragment: SupportMapFragment? = null

    private var heatMap: HeatMap? = null

    private lateinit var mOpacityBar: SeekBar

    private lateinit var mRadiusBar: SeekBar

    private lateinit var mIntensityBar: SeekBar

    private var colorClickCount = 0

    private var dataClickCount = 0

    private var heatMapVisible = true

    private var unit: RadiusUnit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heatmap)
        val fragment = supportFragmentManager.findFragmentById(R.id.mapfragment_heatmapdemo)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
        mOpacityBar = findViewById(R.id.OpacitySeekBar)
        mOpacityBar.max = BAR_MAX
        mOpacityBar.progress = 0

        mRadiusBar = findViewById(R.id.RadiusSeekBar)
        mRadiusBar.max = BAR_MAX * BAR_MAX * 5
        mRadiusBar.progress = 0

        mIntensityBar = findViewById(R.id.IntensitySeekBar)
        mIntensityBar.max = BAR_MAX
        mIntensityBar.progress = 0
    }

    override fun onMapReady(huaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = huaweiMap
        hMap?.isMyLocationEnabled = true
        setListeners()
    }

    /**
     * Add Heat Map
     */
    fun addHeatMap(view: View?) {
        if (hMap == null) {
            return
        }
        if (heatMap != null) {
            heatMap?.remove()
            heatMap = null
        }
        val heatMapAttribute: HashMap<Float, Float> = object : HashMap<Float, Float>() {
            init {
                put(9.0f, 0.0f)
                put(13.0f, 1.0f)
            }
        }
        val heatMapAttribute2: Map<Float, Float> = object : HashMap<Float, Float>() {
            init {
                put(3.0f, 3.0f)
                put(13.0f, 20.0f)
            }
        }
        val heatMapOptions1 = HeatMapOptions()
        heatMapOptions1.radius(heatMapAttribute2)
        heatMapOptions1.opacity(heatMapAttribute)
        heatMapOptions1.intensity(-0.1f)
        heatMapOptions1.dataSet(R.raw.earthquakes)
        heatMap = hMap?.addHeatMap("heatMap", heatMapOptions1)
        Log.i(TAG, "addHeatMap: " + heatMap?.id + " ")

        mOpacityBar.progress = 0
        mRadiusBar.progress = 0
        mIntensityBar.progress = 0
    }

    /**
     * Set the listener associated with the heat map
     */
    private fun setListeners() {
        mOpacityBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (heatMap != null) {
                    heatMap?.setOpacity(progress.toFloat() / BAR_MAX.toFloat())
                    Log.d(TAG, "heatMap.setOpacity " + progress.toFloat() / BAR_MAX.toFloat())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        mRadiusBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                @Suppress("NAME_SHADOWING")
                var progress = progress
                if (heatMap != null) {
                    if (heatMap?.radiusUnit == RadiusUnit.PIXEL) {
                        progress /= (BAR_MAX * 5)
                    }
                    heatMap?.setRadius(progress.toFloat())
                    Log.d(TAG, "heatMap.setRadius " + progress.toFloat())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        mIntensityBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (heatMap != null) {
                    heatMap?.setIntensity(progress.toFloat() * 5 / BAR_MAX.toFloat())
                    Log.d(TAG, "heatMap.setIntensity " + progress.toFloat() * 5 / BAR_MAX.toFloat())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    /**
     * Remove the heat map
     */
    fun removeHeatMap(view: View?) {
        if (heatMap != null) {
            heatMap?.remove()
            heatMap = null
        }
    }

    fun changeColor(view: View?) {
        if (heatMap == null) {
            return
        }
        if (colorClickCount == 0) {
            val color: Map<Float, Int> = object : HashMap<Float, Int>() {
                init {
                    put(0f, Color.argb(0, 33, 102, 172))
                    put(0.2f, Color.argb(255, 103, 169, 207))
                    put(0.4f, Color.argb(255, 209, 229, 240))
                    put(0.6f, Color.argb(255, 253, 219, 199))
                    put(0.8f, Color.argb(255, 239, 138, 98))
                    put(1.0f, Color.argb(255, 178, 24, 43))
                }
            }
            heatMap?.setColor(color)
        }
        if (colorClickCount == 1) {
            val color2: Map<Float, Int> = object : HashMap<Float, Int>() {
                init {
                    put(0.0f, Color.argb(0, 2, 108, 57))
                    put(0.15f, Color.argb(255 / 3 * 2, 254, 233, 229))
                    put(0.3f, Color.rgb(252, 196, 192))
                    put(0.45f, Color.rgb(249, 134, 172))
                    put(0.6f, Color.rgb(226, 62, 153))
                    put(0.75f, Color.rgb(161, 1, 124))
                    put(0.9f, Color.rgb(93, 0, 111))
                    put(1.0f, Color.rgb(75, 0, 106))
                }
            }
            heatMap?.setColor(color2)
        }
        if (colorClickCount == 2) {
            heatMap?.setColor(null)
            heatMap?.setIntensity(null as Map<Float?, Float?>?)
            heatMap?.setOpacity(null as Map<Float?, Float?>?)
            heatMap?.setRadius(null as Map<Float?, Float?>?)
        }
        colorClickCount = (colorClickCount + 1) % 3
    }

    /**
     * Modifying the Data Source of a Heat Map
     */
    fun changeData(view: View?) {
        if (heatMap == null) {
            return
        }
        if (dataClickCount == 0) {
            heatMap?.changeDataSet(this, R.raw.earthquakes_with_usa)
        }
        if (dataClickCount == 1) {
            heatMap?.changeDataSet(this, R.raw.earthquakes)
        }
        if (dataClickCount == 2) {
            heatMap?.changeDataSet(this, R.raw.citycenter)
        }
        dataClickCount = (dataClickCount + 1) % 3
    }

    /**
     * Modifying the Visibility of a Heat Map
     */
    fun setHeatMapVisible(view: View?) {
        if (heatMap == null) {
            return
        }
        heatMapVisible = !heatMapVisible
        heatMap?.setVisible(heatMapVisible)
    }

    /**
     * Modifying the Radius Units of a Heat Map
     */
    fun setHeatMapRadiusUnit(view: View?) {
        if (heatMap == null) {
            return
        }
        unit = if (unit == RadiusUnit.METER) {
            RadiusUnit.PIXEL
        } else {
            RadiusUnit.METER
        }
        heatMap?.radiusUnit = unit
    }
}