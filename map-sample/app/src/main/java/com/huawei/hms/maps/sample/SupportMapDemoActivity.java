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
 *  2020.1.3-Changed modify the import classes type and add some SupportMapFragment demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;
import com.huawei.hms.maps.util.LogM;
import com.huawei.hms.maps.util.ResourceBitmapDescriptor;

/**
 * Create a simple activity with a map and a marker on the map.
 */
public class SupportMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "SupportMapDemoActivity";

    private HuaweiMap hMap;

    private SupportMapFragment mSupportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogM.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportmapfragment_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.supportMap);
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        LogM.d(TAG, "onMapReady: ");
        hMap = map;
        hMap.getUiSettings().setCompassEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 14));
        hMap.setOnMapLongClickListener(new HuaweiMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                LogM.d(TAG, "onMapLongClick: latLng " + " please input latLng");
            }
        });
    }

    Marker mBeijing;

    Marker mShanghai;

    Marker mNanjing;

    private static final LatLng Beijing = new LatLng(48.893478, 2.334595);

    private static final LatLng Nanjing = new LatLng(32.0603, 118.7969);

    private static final LatLng Shanghai = new LatLng(48.7, 2.12);

    public void addMarker(View view) {
        if (mBeijing == null && mShanghai == null && mNanjing == null) {
            // Uses a colored icon.
            mBeijing = hMap.addMarker(new MarkerOptions().position(Beijing).title("Beijing").clusterable(true));
            mShanghai = hMap.addMarker(new MarkerOptions().position(Shanghai)
                    .alpha(0.8f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)));
        }
        if (mBeijing != null) {
            mBeijing.setTitle("hello");
            mBeijing.setSnippet("world");
            mBeijing.setTag("huaweimap");
            mShanghai.setTitle("Hello");
        }
        mBeijing.setDraggable(true);
        mShanghai.setDraggable(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSupportMapFragment.onSaveInstanceState(outState);
    }

    boolean visiable = true;

    public void setVisiable(View view) {
        if (visiable) {
            mBeijing.setVisible(true);
            visiable = false;
        } else {
            mBeijing.setVisible(false);
            visiable = true;
        }
    }

    public void setAlpha(View view) {
        if (visiable) {
            mBeijing.setAlpha(0.5f);
            visiable = false;
        } else {
            mBeijing.setAlpha(1.0f);
            visiable = true;
        }
    }

    public void setFlat(View view) {
        if (visiable) {
            mBeijing.setFlat(true);
            visiable = false;
        } else {
            mBeijing.setFlat(false);
            visiable = true;
        }
    }

    public void setZIndex(View view) {
        if (visiable) {
            mBeijing.setZIndex(20f);
            visiable = false;
        } else {
            mBeijing.setZIndex(-20f);
            visiable = true;
        }
    }

    public void setRotation(View view) {
        if (visiable) {
            mBeijing.setRotation(30.0f);
            visiable = false;
        } else {
            mBeijing.setRotation(60.0f);
            visiable = true;
        }
    }

    public void removeMarker(View view) {
        mBeijing.remove();
        mBeijing = null;
    }

    public void showInfoWindow(View view) {
        if (visiable) {
            mBeijing.showInfoWindow();
            visiable = false;
        } else {
            mBeijing.hideInfoWindow();
            visiable = true;
        }
    }

    public void setAnchor(View view) {
        if (mBeijing != null) {
            mBeijing.setAnchor(0.9F, 0.9F);
        }
    }

    public void setIcon(View view) {
        if (null != mBeijing) {
            Bitmap bitmap =
                    ResourceBitmapDescriptor.drawableToBitmap(this, ContextCompat.getDrawable(this, R.drawable.badge_tr));
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            mBeijing.setIcon(bitmapDescriptor);
        }
    }
}
