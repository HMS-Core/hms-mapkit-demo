/*
 * Copyright 2019 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.huawei.hms.maps.sample.utils

import com.huawei.hms.maps.model.LatLng
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * NetClient
 */
class NetClient private constructor() {
    companion object {
        private const val TAG = "NetClient"

        @JvmStatic
        var netClient: NetClient? = null
            get() {
                if (field == null) {
                    field = NetClient()
                }
                return field
            }
            private set
        private var client: OkHttpClient? = null
        private val JSON = MediaType.parse("application/json; charset=utf-8")
    }

    init {
        client = initOkHttpClient()
    }

    // Please place your API KEY here. If the API KEY contains special characters, you need to encode it using
    // encodeURI.
    private val mDefaultKey = MapUtils.API_KEY

    //    private val mDefaultKey = "API KEY"
    private val mWalkingRoutePlanningURL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/walking"
    private val mBicyclingRoutePlanningURL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/bicycling"
    private val mDrivingRoutePlanningURL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/driving"
    private fun initOkHttpClient(): OkHttpClient? {
        if (client == null) {
            client = OkHttpClient.Builder().readTimeout(10000, TimeUnit.MILLISECONDS) // Set the read timeout.
                    .connectTimeout(10000, TimeUnit.MILLISECONDS) // Set the connect timeout.
                    .build()
        }
        return client
    }

    /**
     * @param latLng1 origin latitude and longitude
     * @param latLng2 destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return
     */
    fun getWalkingRoutePlanningResult(latLng1: LatLng, latLng2: LatLng, needEncode: Boolean): Response? {
        var key = mDefaultKey
        if (needEncode) {
            try {
                key = URLEncoder.encode(mDefaultKey, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        val url = "$mWalkingRoutePlanningURL?key=$key"
        var response: Response? = null
        val origin = JSONObject()
        val destination = JSONObject()
        val json = JSONObject()
        try {
            origin.put("lat", latLng1.latitude)
            origin.put("lng", latLng1.longitude)
            destination.put("lat", latLng2.latitude)
            destination.put("lng", latLng2.longitude)
            json.put("origin", origin)
            json.put("destination", destination)
            val requestBody = RequestBody.create(JSON, json.toString())
            val request = Request.Builder().url(url).post(requestBody).build()
            response = netClient?.initOkHttpClient()?.newCall(request)?.execute()
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    /**
     * @param latLng1 origin latitude and longitude
     * @param latLng2 destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return
     */
    fun getBicyclingRoutePlanningResult(latLng1: LatLng, latLng2: LatLng, needEncode: Boolean): Response? {
        var key = mDefaultKey
        if (needEncode) {
            try {
                key = URLEncoder.encode(mDefaultKey, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        val url = "$mBicyclingRoutePlanningURL?key=$key"
        var response: Response? = null
        val origin = JSONObject()
        val destination = JSONObject()
        val json = JSONObject()
        try {
            origin.put("lat", latLng1.latitude)
            origin.put("lng", latLng1.longitude)
            destination.put("lat", latLng2.latitude)
            destination.put("lng", latLng2.longitude)
            json.put("origin", origin)
            json.put("destination", destination)
            val requestBody = RequestBody.create(JSON, json.toString())
            val request = Request.Builder().url(url).post(requestBody).build()
            response = netClient?.initOkHttpClient()?.newCall(request)?.execute()
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }

    /**
     * @param latLng1 origin latitude and longitude
     * @param latLng2 destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return
     */
    fun getDrivingRoutePlanningResult(latLng1: LatLng, latLng2: LatLng, needEncode: Boolean): Response? {
        var key = mDefaultKey
        if (needEncode) {
            try {
                key = URLEncoder.encode(mDefaultKey, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
        val url = "$mDrivingRoutePlanningURL?key=$key"
        var response: Response? = null
        val origin = JSONObject()
        val destination = JSONObject()
        val json = JSONObject()
        try {
            origin.put("lat", latLng1.latitude)
            origin.put("lng", latLng1.longitude)
            destination.put("lat", latLng2.latitude)
            destination.put("lng", latLng2.longitude)
            json.put("origin", origin)
            json.put("destination", destination)
            val requestBody = RequestBody.create(JSON, json.toString())
            val request = Request.Builder().url(url).post(requestBody).build()
            response = netClient?.initOkHttpClient()?.newCall(request)?.execute()
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return response
    }
}