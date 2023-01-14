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

package com.huawei.hms.maps.sample;

import java.io.InputStream;
import java.util.List;

import org.json.JSONException;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.sample.utils.MyItemReader;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * MarkerClustering related
 */
@SuppressLint("LongLogTag")
public class MarkerClusteringDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MarkerClusteringDemoActivity";
    @SuppressWarnings("FieldCanBeLocal")
    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_clustering_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapfragment_markerdemo);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4934813, -0.5013019988494029), 9));
    }

    /**
     * Add marker to the map
     *
     * @param view view
     */
    public void addMarkers(View view) {
        if (null == hMap) {
            return;
        }
        if (isAdded) {
            Toast.makeText(this, "Markers has already added.", Toast.LENGTH_LONG).show();
            return;
        }
        InputStream inputStream = getResources().openRawResource(R.raw.marker_200);
        List<MarkerOptions> markerOptionsList = null;
        try {
            markerOptionsList = new MyItemReader().read(inputStream);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException.");
        }
        if (markerOptionsList == null) {
            return;
        }
        for (MarkerOptions item : markerOptionsList) {
            hMap.addMarker(item);
        }
        isAdded = true;
    }

    /**
     * enable clustering
     *
     * @param view view
     */
    public void enableMarkerClustering(View view) {
        if (null != hMap) {
            hMap.setMarkersClustering(true);
        }
    }

    /**
     * enable clustering
     *
     * @param view view
     */
    public void disableMarkerClustering(View view) {
        if (null != hMap) {
            hMap.setMarkersClustering(false);
        }
    }

    /**
     * clear map
     *
     * @param view view
     */
    public void clearMap(View view) {
        if (null != hMap) {
            hMap.clear();
            isAdded = false;
        }
    }

    /**
     * set default
     *
     * @param view view
     */
    public void resetMarkerCluster(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setMarkerClusterIcon(null);
        }
    }

    /**
     * set marker cluster icon
     *
     * @param view view
     */
    public void setMarkerClusterIcon(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setMarkerClusterIcon(BitmapDescriptorFactory.fromResource(R.drawable.avocado));
        }
    }

    /**
     * set marker cluster text color
     *
     * @param view view
     */
    public void setMarkerClusterTextColor(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setMarkerClusterTextColor(Color.RED);
        }
    }

    /**
     * set marker cluster color
     *
     * @param view view
     */
    public void setMarkerClusterColor(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setMarkerClusterColor(Color.RED);
        }
    }
}
