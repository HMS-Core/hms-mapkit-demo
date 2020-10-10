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

package com.huawei.hms.maps.sample.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.huawei.hms.maps.model.LatLng;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * NetClient
 */
public class NetClient {
    private static final String TAG = "NetClient";

    private static NetClient netClient;

    private static OkHttpClient client;

    // Please place your API KEY here. If the API KEY contains special characters, you need to encode it using
    // encodeURI.
    private String mDefaultKey = "API KEY";

    private String mWalkingRoutePlanningURL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/walking";

    private String mBicyclingRoutePlanningURL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/bicycling";

    private String mDrivingRoutePlanningURL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/driving";

    private static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private NetClient() {
        client = initOkHttpClient();
    }

    public OkHttpClient initOkHttpClient() {
        if (client == null) {
            client = new OkHttpClient.Builder().readTimeout(10000, TimeUnit.MILLISECONDS)// Set the read timeout.
                .connectTimeout(10000, TimeUnit.MILLISECONDS)// Set the connect timeout.
                .build();
        }
        return client;
    }

    public static NetClient getNetClient() {
        if (netClient == null) {
            netClient = new NetClient();
        }
        return netClient;
    }

    /**
     * @param latLng1 origin latitude and longitude
     * @param latLng2 destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return
     */
    public Response getWalkingRoutePlanningResult(LatLng latLng1, LatLng latLng2, boolean needEncode) {
        String key = mDefaultKey;
        if (needEncode) {
            try {
                key = URLEncoder.encode(mDefaultKey, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = mWalkingRoutePlanningURL + "?key=" + key;

        Response response = null;
        JSONObject origin = new JSONObject();
        JSONObject destination = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            origin.put("lat", latLng1.latitude);
            origin.put("lng", latLng1.longitude);

            destination.put("lat", latLng2.latitude);
            destination.put("lng", latLng2.longitude);

            json.put("origin", origin);
            json.put("destination", destination);

            RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
            Request request = new Request.Builder().url(url).post(requestBody).build();
            response = getNetClient().initOkHttpClient().newCall(request).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * @param latLng1 origin latitude and longitude
     * @param latLng2 destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return
     */
    public Response getBicyclingRoutePlanningResult(LatLng latLng1, LatLng latLng2, boolean needEncode) {
        String key = mDefaultKey;
        if (needEncode) {
            try {
                key = URLEncoder.encode(mDefaultKey, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = mBicyclingRoutePlanningURL + "?key=" + key;

        Response response = null;
        JSONObject origin = new JSONObject();
        JSONObject destination = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            origin.put("lat", latLng1.latitude);
            origin.put("lng", latLng1.longitude);

            destination.put("lat", latLng2.latitude);
            destination.put("lng", latLng2.longitude);

            json.put("origin", origin);
            json.put("destination", destination);

            RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
            Request request = new Request.Builder().url(url).post(requestBody).build();
            response = getNetClient().initOkHttpClient().newCall(request).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * @param latLng1 origin latitude and longitude
     * @param latLng2 destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return
     */
    public Response getDrivingRoutePlanningResult(LatLng latLng1, LatLng latLng2, boolean needEncode) {
        String key = mDefaultKey;
        if (needEncode) {
            try {
                key = URLEncoder.encode(mDefaultKey, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = mDrivingRoutePlanningURL + "?key=" + key;

        Response response = null;
        JSONObject origin = new JSONObject();
        JSONObject destination = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            origin.put("lat", latLng1.latitude);
            origin.put("lng", latLng1.longitude);

            destination.put("lat", latLng2.latitude);
            destination.put("lng", latLng2.longitude);

            json.put("origin", origin);
            json.put("destination", destination);

            RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
            Request request = new Request.Builder().url(url).post(requestBody).build();
            response = getNetClient().initOkHttpClient().newCall(request).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
