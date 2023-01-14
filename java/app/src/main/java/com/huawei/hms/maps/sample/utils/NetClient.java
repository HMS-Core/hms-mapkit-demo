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

    private static OkHttpClient client;

    // Please place your API KEY here. If the API KEY contains special characters, you need to encode it using
    // encodeURI.
    private static final String DEFAULT_KEY = MapUtils.API_KEY;

    private static final String WALKING_ROUTE_PLANNING_URL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/walking";

    private static final String BICYCLING_ROUTE_PLANNING_URL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/bicycling";

    private static final String DRIVING_ROUTE_PLANNING_URL = "https://mapapi.cloud.huawei.com/mapApi/v1/routeService/driving";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static class Holder {
        private static final NetClient INSTANCE = new NetClient();
    }

    private NetClient() {
    }

    private static NetClient getInstance() {
        return Holder.INSTANCE;
    }

    public OkHttpClient initOkHttpClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    // Set the read timeout.
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    // Set the connect timeout.
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .build();
        }
        return client;
    }

    public static NetClient getNetClient() {
        return getInstance();
    }

    /**
     * Obtaining the Results of Walking Path Planning
     *
     * @param latLng1    origin latitude and longitude
     * @param latLng2    destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return Response
     */
    public Response getWalkingRoutePlanningResult(LatLng latLng1, LatLng latLng2, boolean needEncode) {
        String key = DEFAULT_KEY;
        if (needEncode) {
            try {
                key = URLEncoder.encode(DEFAULT_KEY, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = WALKING_ROUTE_PLANNING_URL + "?key=" + key;

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

            RequestBody requestBody = RequestBody.Companion.create(String.valueOf(json), JSON);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            response = getNetClient().initOkHttpClient().newCall(request).execute();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Obtaining the Results of Bicycling Path Planning
     *
     * @param latLng1    origin latitude and longitude
     * @param latLng2    destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return Response
     */
    public Response getBicyclingRoutePlanningResult(LatLng latLng1, LatLng latLng2, boolean needEncode) {
        String key = DEFAULT_KEY;
        if (needEncode) {
            try {
                key = URLEncoder.encode(DEFAULT_KEY, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = BICYCLING_ROUTE_PLANNING_URL + "?key=" + key;

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

            RequestBody requestBody = RequestBody.Companion.create(String.valueOf(json), JSON);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            response = getNetClient().initOkHttpClient().newCall(request).execute();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Obtaining the Results of Driving Path Planning
     *
     * @param latLng1    origin latitude and longitude
     * @param latLng2    destination latitude and longitude
     * @param needEncode dose the api key need to be encoded
     * @return Response
     */
    public Response getDrivingRoutePlanningResult(LatLng latLng1, LatLng latLng2, boolean needEncode) {
        String key = DEFAULT_KEY;
        if (needEncode) {
            try {
                key = URLEncoder.encode(DEFAULT_KEY, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = DRIVING_ROUTE_PLANNING_URL + "?key=" + key;

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

            RequestBody requestBody = RequestBody.Companion.create(String.valueOf(json), JSON);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            response = getNetClient().initOkHttpClient().newCall(request).execute();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
