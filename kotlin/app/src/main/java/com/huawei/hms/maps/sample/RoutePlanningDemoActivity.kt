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
 *  2020.1.3-Changed modify the import classes type and add some map display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.LatLngBounds
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.huawei.hms.maps.model.Polyline
import com.huawei.hms.maps.model.PolylineOptions
import com.huawei.hms.maps.sample.utils.NetworkRequestManager
import com.huawei.hms.maps.sample.utils.NetworkRequestManager.OnNetworkListener
import org.json.JSONException
import org.json.JSONObject

/**
 * Route Planning
 */
@SuppressLint("LongLogTag")
class RoutePlanningDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "RoutePlanningDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var mMarkerOrigin: Marker? = null
    private var mMarkerDestination: Marker? = null
    private lateinit var edtOriginLat: EditText
    private lateinit var edtOriginLng: EditText
    private lateinit var edtDestinationLat: EditText
    private lateinit var edtDestinationLng: EditText
    private var latLng1 = LatLng(54.216608, -4.66529)
    private var latLng2 = LatLng(54.209673, -4.64002)
    private val mPolylines: MutableList<Polyline> = ArrayList()
    private val mPaths: MutableList<List<LatLng>> = ArrayList()
    private var mLatLngBounds: LatLngBounds? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_planning_demo)
        val fragment = supportFragmentManager.findFragmentById(R.id.mapfragment_routeplanningdemo)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
        edtOriginLat = findViewById(R.id.edt_origin_lat)
        edtOriginLng = findViewById(R.id.edt_origin_lng)
        edtDestinationLat = findViewById(R.id.edt_destination_lat)
        edtDestinationLng = findViewById(R.id.edt_destination_lng)
    }

    override fun onMapReady(huaweiMap: HuaweiMap) {
        hMap = huaweiMap
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13f))
        addOriginMarker(latLng1)
        addDestinationMarker(latLng2)
    }

    fun getWalkingRouteResult(view: View?) {
        removePolylines()
        NetworkRequestManager.getWalkingRoutePlanningResult(latLng1, latLng2,
                object : OnNetworkListener {
                    override fun requestSuccess(result: String?) {
                        generateRoute(result)
                    }

                    override fun requestFail(errorMsg: String?) {
                        runOnUiThread { Toast.makeText(this@RoutePlanningDemoActivity, errorMsg, Toast.LENGTH_SHORT).show() }
                    }
                })
    }

    fun getBicyclingRouteResult(view: View?) {
        removePolylines()
        NetworkRequestManager.getBicyclingRoutePlanningResult(latLng1, latLng2,
                object : OnNetworkListener {
                    override fun requestSuccess(result: String?) {
                        generateRoute(result)
                    }

                    override fun requestFail(errorMsg: String?) {
                        runOnUiThread { Toast.makeText(this@RoutePlanningDemoActivity, errorMsg, Toast.LENGTH_SHORT).show() }
                    }
                })
    }

    fun getDrivingRouteResult(view: View?) {
        removePolylines()
        NetworkRequestManager.getDrivingRoutePlanningResult(latLng1, latLng2,
                object : OnNetworkListener {
                    override fun requestSuccess(result: String?) {
                        generateRoute(result)
                    }

                    override fun requestFail(errorMsg: String?) {
                        runOnUiThread { Toast.makeText(this@RoutePlanningDemoActivity, errorMsg, Toast.LENGTH_SHORT).show() }
                    }
                })
    }

    private fun generateRoute(json: String?) {
        try {
            val jsonObject = JSONObject(json)
            val routes = jsonObject.optJSONArray("routes")
            if (null == routes || routes.length() == 0) {
                return
            }
            val route = routes.getJSONObject(0)

            // get route bounds
            val bounds = route.optJSONObject("bounds")
            if (null != bounds && bounds.has("southwest") && bounds.has("northeast")) {
                val southwest = bounds.optJSONObject("southwest")
                val northeast = bounds.optJSONObject("northeast")
                val sw = LatLng(southwest.optDouble("lat"), southwest.optDouble("lng"))
                val ne = LatLng(northeast.optDouble("lat"), northeast.optDouble("lng"))
                mLatLngBounds = LatLngBounds(sw, ne)
            }

            // get paths
            val paths = route.optJSONArray("paths")
            for (i in 0 until paths.length()) {
                val path = paths.optJSONObject(i)
                val mPath: MutableList<LatLng> = ArrayList()
                val steps = path.optJSONArray("steps")
                for (j in 0 until steps.length()) {
                    val step = steps.optJSONObject(j)
                    val polyline = step.optJSONArray("polyline")
                    for (k in 0 until polyline.length()) {
                        if (j > 0 && k == 0) {
                            continue
                        }
                        val line = polyline.getJSONObject(k)
                        val lat = line.optDouble("lat")
                        val lng = line.optDouble("lng")
                        val latLng = LatLng(lat, lng)
                        mPath.add(latLng)
                    }
                }
                mPaths.add(i, mPath)
            }
            runOnUiThread { renderRoute(mPaths, mLatLngBounds) }
        } catch (e: JSONException) {
            Log.e(TAG, "JSONException$e")
        }
    }

    /**
     * Render the route planning result
     *
     * @param paths
     * @param latLngBounds
     */
    private fun renderRoute(paths: List<List<LatLng>>?, latLngBounds: LatLngBounds?) {
        if (null == paths || paths.isEmpty() || paths[0].isEmpty()) {
            return
        }
        for (i in paths.indices) {
            val path = paths[i]
            val options = PolylineOptions().color(Color.BLUE).width(5f)
            for (latLng in path) {
                options.add(latLng)
            }
            val polyline = hMap?.addPolyline(options)
            if (polyline != null) {
                mPolylines.add(i, polyline)
            }
        }
        addOriginMarker(paths[0][0])
        addDestinationMarker(paths[0][paths[0].size - 1])
        if (null != latLngBounds) {
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 5)
            hMap?.moveCamera(cameraUpdate)
        } else {
            hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(paths[0][0], 13f))
        }
    }

    fun setOrigin(view: View?) {
        val originLatStr = edtOriginLat.text.toString().trim()
        val originLngStr = edtOriginLng.text.toString().trim()
        if (originLatStr.isNotEmpty() && originLngStr.isNotEmpty()) {
            try {
                latLng1 = LatLng(originLatStr.toDouble(), originLngStr.toDouble())
                removePolylines()
                addOriginMarker(latLng1)
                hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13f))
                mMarkerOrigin?.showInfoWindow()
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "IllegalArgumentException $e")
                Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show()
            } catch (e: NullPointerException) {
                Log.e(TAG, "NullPointerException $e")
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                Log.e(TAG, "NumberFormatException $e")
                Toast.makeText(this, "NumberFormatException", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setDestination(view: View?) {
        val destinationLatStr = edtDestinationLat.text.toString().trim()
        val destinationLngStr = edtDestinationLng.text.toString().trim()
        if (destinationLatStr.isNotEmpty() && destinationLngStr.isNotEmpty()) {
            try {
                latLng2 = LatLng(destinationLatStr.toDouble(), destinationLngStr.toDouble())
                removePolylines()
                addDestinationMarker(latLng2)
                hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 13f))
                mMarkerDestination?.showInfoWindow()
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "IllegalArgumentException $e")
                Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show()
            } catch (e: NullPointerException) {
                Log.e(TAG, "NullPointerException $e")
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show()
            } catch (e: NumberFormatException) {
                Log.e(TAG, "NumberFormatException $e")
                Toast.makeText(this, "NumberFormatException", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addOriginMarker(latLng: LatLng) {
        if (null != mMarkerOrigin) {
            mMarkerOrigin?.remove()
        }
        mMarkerOrigin = hMap?.addMarker(MarkerOptions().position(latLng)
                .anchor(0.5f, 0.9f) // .anchorMarker(0.5f, 0.9f)
                .title("Origin")
                .snippet(latLng.toString()))
    }

    private fun addDestinationMarker(latLng: LatLng) {
        if (null != mMarkerDestination) {
            mMarkerDestination?.remove()
        }
        mMarkerDestination = hMap?.addMarker(
                MarkerOptions().position(latLng).anchor(0.5f, 0.9f).title("Destination").snippet(latLng.toString()))
    }

    private fun removePolylines() {
        for (polyline in mPolylines) {
            polyline.remove()
        }
        mPolylines.clear()
        mPaths.clear()
        mLatLngBounds = null
    }
}