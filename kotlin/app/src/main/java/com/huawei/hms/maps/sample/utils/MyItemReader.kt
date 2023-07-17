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
 *  2020.1.3-Changed modify the import classes type and add some camera events.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample.utils

import android.util.Log
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStream
import java.util.Scanner

/**
 * MyItemReader
 */
class MyItemReader {
    companion object {
        private const val TAG = "MyItemReader"
        private const val REGEX_INPUT_BOUNDARY_BEGINNING = "\\A"
    }

    /**
     * read item from inputstream.
     *
     * @param inputStream inputStream
     * @return List<MarkerOptions>
     * @throws JSONException JSONException
     */
    @Throws(JSONException::class)
    fun read(inputStream: InputStream?): List<MarkerOptions> {
        val items: MutableList<MarkerOptions> = ArrayList()
        val json = Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            val jsonObject = array.getJSONObject(i)
            val lat = jsonObject.getDouble("lat")
            val lng = jsonObject.getDouble("lng")
            val latLng = LatLng(lat, lng)
            items.add(MarkerOptions().position(latLng).clusterable(true))
        }
        try {
            inputStream?.close()
        } catch (e: IOException) {
            Log.e(TAG, "inputStream error")
        }
        return items
    }
}