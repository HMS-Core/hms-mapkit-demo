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

package com.huawei.hms.maps.sample.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;

import android.util.Log;

/**
 * MyItemReader
 */
public class MyItemReader {
    private static final String TAG = "MyItemReader";

    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    /**
     * read item from inputstream.
     *
     * @param inputStream inputStream
     * @return List<MarkerOptions>
     * @throws JSONException JSONException
     */
    public List<MarkerOptions> read(InputStream inputStream) throws JSONException {
        List<MarkerOptions> items = new ArrayList<>();
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            double lat = jsonObject.getDouble("lat");
            double lng = jsonObject.getDouble("lng");

            LatLng latLng = new LatLng(lat, lng);
            items.add(new MarkerOptions().position(latLng).clusterable(true));
        }
        try {
            if (null != inputStream) {
                inputStream.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "inputStream error");
        }
        return items;
    }
}
