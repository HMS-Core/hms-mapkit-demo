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

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Create a simple activity with a map and a marker on the map.
 */
public class SupportMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "SupportMapDemoActivity";

    private static final LatLng Beijing = new LatLng(48.893478, 2.334595);

    private static final LatLng Shanghai = new LatLng(48.7, 2.12);

    private HuaweiMap hMap;

    private Marker mBeijing;

    private Marker mShanghai;

    private SupportMapFragment mSupportMapFragment;

    private boolean visible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supportmapfragment_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.supportMap);
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hMap = map;
        hMap.getUiSettings().setCompassEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 14));
        hMap.setOnMapLongClickListener(new HuaweiMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(TAG, "onMapLongClick: latLng " + " please input latLng");
            }
        });
    }

    public void addMarker(View view) {
        if (mBeijing == null && mShanghai == null) {
            // Uses a colored icon.
            mBeijing = hMap.addMarker(new MarkerOptions().position(Beijing).title("Beijing").clusterable(true));
            mShanghai = hMap.addMarker(new MarkerOptions().position(Shanghai)
                .alpha(0.8f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)));
        }
        if (null != mBeijing) {
            mBeijing.setTitle("hello");
            mBeijing.setSnippet("world");
            mBeijing.setTag("huaweimap");
            mBeijing.setDraggable(true);
        }
        if (null != mShanghai) {
            mShanghai.setTitle("Hello");
            mShanghai.setDraggable(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSupportMapFragment.onSaveInstanceState(outState);
    }

    public void setVisiable(View view) {
        if (null != mBeijing) {
            if (visible) {
                mBeijing.setVisible(true);
                visible = false;
            } else {
                mBeijing.setVisible(false);
                visible = true;
            }
        }
    }

    public void setAlpha(View view) {
        if (null != mBeijing) {
            if (visible) {
                mBeijing.setAlpha(0.5f);
                visible = false;
            } else {
                mBeijing.setAlpha(1.0f);
                visible = true;
            }
        }
    }

    public void setFlat(View view) {
        if (null != mBeijing) {
            if (visible) {
                mBeijing.setFlat(true);
                visible = false;
            } else {
                mBeijing.setFlat(false);
                visible = true;
            }
        }
    }

    public void setZIndex(View view) {
        if (null != mBeijing) {
            if (visible) {
                mBeijing.setZIndex(20f);
                visible = false;
            } else {
                mBeijing.setZIndex(-20f);
                visible = true;
            }
        }
    }

    public void setRotation(View view) {
        if (null != mBeijing) {
            if (visible) {
                mBeijing.setRotation(30.0f);
                visible = false;
            } else {
                mBeijing.setRotation(60.0f);
                visible = true;
            }
        }
    }

    public void removeMarker(View view) {
        if (null != mBeijing) {
            mBeijing.remove();
            mBeijing = null;
        }

        if (null != mShanghai) {
            mShanghai.remove();
            mShanghai = null;
        }
    }

    public void showInfoWindow(View view) {
        if (null != mBeijing) {
            if (visible) {
                mBeijing.showInfoWindow();
                visible = false;
            } else {
                mBeijing.hideInfoWindow();
                visible = true;
            }
        }
    }

    public void setAnchor(View view) {
        if (null != mBeijing) {
            mBeijing.setMarkerAnchor(0.9F, 0.9F);
        }
    }

    public void setIcon(View view) {
        if (null != mBeijing) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.badge_tr);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            mBeijing.setIcon(bitmapDescriptor);
        }
    }
}
