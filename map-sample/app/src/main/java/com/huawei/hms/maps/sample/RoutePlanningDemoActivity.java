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

package com.huawei.hms.maps.sample;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
import com.huawei.hms.maps.sample.utils.NetworkRequestManager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Route Planning
 */
public class RoutePlanningDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "RoutePlanningDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private Marker mMarkerOrigin;

    private Marker mMarkerDestination;

    private EditText edtOriginLat;

    private EditText edtOriginLng;

    private EditText edtDestinationLat;

    private EditText edtDestinationLng;

    private LatLng latLng1 = new LatLng(54.216608, -4.66529);

    private LatLng latLng2 = new LatLng(54.209673, -4.64002);

    private List<Polyline> mPolylines = new ArrayList<>();

    private List<List<LatLng>> mPaths = new ArrayList<>();

    private LatLngBounds mLatLngBounds;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    renderRoute(mPaths, mLatLngBounds);
                    break;
                case 1:
                    Bundle bundle = msg.getData();
                    String errorMsg = bundle.getString("errorMsg");
                    Toast.makeText(RoutePlanningDemoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_planning_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapfragment_routeplanningdemo);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        edtOriginLat = findViewById(R.id.edt_origin_lat);
        edtOriginLng = findViewById(R.id.edt_origin_lng);
        edtDestinationLat = findViewById(R.id.edt_destination_lat);
        edtDestinationLng = findViewById(R.id.edt_destination_lng);
    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        hMap = huaweiMap;
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13));
        addOriginMarker(latLng1);
        addDestinationMarker(latLng2);
    }

    public void getWalkingRouteResult(View view) {
        removePolylines();
        NetworkRequestManager.getWalkingRoutePlanningResult(latLng1, latLng2,
            new NetworkRequestManager.OnNetworkListener() {
                @Override
                public void requestSuccess(String result) {
                    generateRoute(result);
                }

                @Override
                public void requestFail(String errorMsg) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("errorMsg", errorMsg);
                    msg.what = 1;
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            });
    }

    public void getBicyclingRouteResult(View view) {
        removePolylines();
        NetworkRequestManager.getBicyclingRoutePlanningResult(latLng1, latLng2,
            new NetworkRequestManager.OnNetworkListener() {
                @Override
                public void requestSuccess(String result) {
                    generateRoute(result);
                }

                @Override
                public void requestFail(String errorMsg) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("errorMsg", errorMsg);
                    Log.d("sfj", errorMsg);
                    msg.what = 1;
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            });
    }

    public void getDrivingRouteResult(View view) {
        removePolylines();
        NetworkRequestManager.getDrivingRoutePlanningResult(latLng1, latLng2,
            new NetworkRequestManager.OnNetworkListener() {
                @Override
                public void requestSuccess(String result) {
                    generateRoute(result);
                }

                @Override
                public void requestFail(String errorMsg) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("errorMsg", errorMsg);
                    msg.what = 1;
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
            });
    }

    private void generateRoute(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray routes = jsonObject.optJSONArray("routes");
            if (null == routes || routes.length() == 0) {
                return;
            }
            JSONObject route = routes.getJSONObject(0);

            // get route bounds
            JSONObject bounds = route.optJSONObject("bounds");
            if (null != bounds && bounds.has("southwest") && bounds.has("northeast")) {
                JSONObject southwest = bounds.optJSONObject("southwest");
                JSONObject northeast = bounds.optJSONObject("northeast");
                LatLng sw = new LatLng(southwest.optDouble("lat"), southwest.optDouble("lng"));
                LatLng ne = new LatLng(northeast.optDouble("lat"), northeast.optDouble("lng"));
                mLatLngBounds = new LatLngBounds(sw, ne);
            }

            // get paths
            JSONArray paths = route.optJSONArray("paths");
            for (int i = 0; i < paths.length(); i++) {
                JSONObject path = paths.optJSONObject(i);
                List<LatLng> mPath = new ArrayList<>();

                JSONArray steps = path.optJSONArray("steps");
                for (int j = 0; j < steps.length(); j++) {
                    JSONObject step = steps.optJSONObject(j);

                    JSONArray polyline = step.optJSONArray("polyline");
                    for (int k = 0; k < polyline.length(); k++) {
                        if (j > 0 && k == 0) {
                            continue;
                        }
                        JSONObject line = polyline.getJSONObject(k);
                        double lat = line.optDouble("lat");
                        double lng = line.optDouble("lng");
                        LatLng latLng = new LatLng(lat, lng);
                        mPath.add(latLng);
                    }
                }
                mPaths.add(i, mPath);
            }
            mHandler.sendEmptyMessage(0);

        } catch (JSONException e) {
            Log.e(TAG, "JSONException" + e.toString());
        }
    }

    /**
     * Render the route planning result
     *
     * @param paths
     * @param latLngBounds
     */
    private void renderRoute(List<List<LatLng>> paths, LatLngBounds latLngBounds) {
        if (null == paths || paths.size() <= 0 || paths.get(0).size() <= 0) {
            return;
        }

        for (int i = 0; i < paths.size(); i++) {
            List<LatLng> path = paths.get(i);
            PolylineOptions options = new PolylineOptions().color(Color.BLUE).width(5);
            for (LatLng latLng : path) {
                options.add(latLng);
            }

            Polyline polyline = hMap.addPolyline(options);
            mPolylines.add(i, polyline);
        }

        addOriginMarker(paths.get(0).get(0));
        addDestinationMarker(paths.get(0).get(paths.get(0).size() - 1));

        if (null != latLngBounds) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, 5);
            hMap.moveCamera(cameraUpdate);
        } else {
            hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paths.get(0).get(0), 13));
        }

    }

    public void setOrigin(View view) {
        String mOriginLat = edtOriginLat.getText().toString().trim();
        String mOriginLng = edtOriginLng.getText().toString().trim();
        if (!TextUtils.isEmpty(mOriginLat) && !TextUtils.isEmpty(mOriginLng)) {
            try {
                latLng1 = new LatLng(Double.valueOf(mOriginLat), Double.valueOf(mOriginLng));

                removePolylines();
                addOriginMarker(latLng1);
                hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 13));
                mMarkerOrigin.showInfoWindow();
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "IllegalArgumentException " + e);
                Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Log.e(TAG, "NullPointerException " + e);
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setDestination(View view) {
        String mDestinationLat = edtDestinationLat.getText().toString().trim();
        String mDestinationLng = edtDestinationLng.getText().toString().trim();
        if (!TextUtils.isEmpty(mDestinationLat) && !TextUtils.isEmpty(mDestinationLng)) {
            try {
                latLng2 = new LatLng(Double.valueOf(mDestinationLat), Double.valueOf(mDestinationLng));

                removePolylines();
                addDestinationMarker(latLng2);
                hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 13));
                mMarkerDestination.showInfoWindow();
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "IllegalArgumentException " + e);
                Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Log.e(TAG, "NullPointerException " + e);
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addOriginMarker(LatLng latLng) {
        if (null != mMarkerOrigin) {
            mMarkerOrigin.remove();
        }
        mMarkerOrigin = hMap.addMarker(new MarkerOptions().position(latLng)
            .anchor(0.5f, 0.9f)
            // .anchorMarker(0.5f, 0.9f)
            .title("Origin")
            .snippet(latLng.toString()));
    }

    private void addDestinationMarker(LatLng latLng) {
        if (null != mMarkerDestination) {
            mMarkerDestination.remove();
        }
        mMarkerDestination = hMap.addMarker(
            new MarkerOptions().position(latLng).anchor(0.5f, 0.9f).title("Destination").snippet(latLng.toString()));
    }

    private void removePolylines() {
        for (Polyline polyline : mPolylines) {
            polyline.remove();
        }

        mPolylines.clear();
        mPaths.clear();
        mLatLngBounds = null;
    }

}
