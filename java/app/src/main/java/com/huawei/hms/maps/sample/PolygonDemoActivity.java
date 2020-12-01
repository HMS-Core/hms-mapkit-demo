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
 *  2020.1.3-Changed modify the import classes type and add some polygon display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsEdit;
import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Polygon;
import com.huawei.hms.maps.model.PolygonOptions;
import com.huawei.hms.maps.sample.utils.MapUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * about polygon
 */
public class PolygonDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "PolygonDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private Polygon mPolygon;

    private TextView polygonShown;

    private EditText oneLatitude;

    private EditText oneLongtitude;

    private EditText polygonTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapInPolygon);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        polygonShown = findViewById(R.id.polygonShown);
        oneLatitude = findViewById(R.id.oneLatitude);
        oneLongtitude = findViewById(R.id.oneLongtitude);
        polygonTag = findViewById(R.id.polygonTag);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));
    }

    /**
     * Add polygons to the map
     */
    public void addPolygon(View view) {
        if (null == hMap) {
            return;
        }
        if (null != mPolygon) {
            mPolygon.remove();
        }
        mPolygon = hMap
            .addPolygon(new PolygonOptions().addAll(MapUtils.createRectangle(new LatLng(48.893478, 2.334595), 0.1, 0.1))
                .fillColor(Color.GREEN)
                .strokeColor(Color.BLACK));
        hMap.setOnPolygonClickListener(new HuaweiMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                Log.i(TAG, "addPolygon and onPolygonClick start ");
            }
        });
    }

    /**
     * Remove the polygon
     */
    public void removePolygon(View view) {
        if (null != mPolygon) {
            mPolygon.remove();
        }
    }

    /**
     * Set the point position information of the polygon
     */
    public void setPoints(View v) {
        if (null != mPolygon) {
            String latitude = oneLatitude.getText().toString().trim();
            String longtitude = oneLongtitude.getText().toString().trim();
            if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    mPolygon.setPoints(MapUtils
                        .createRectangle(new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude)), 0.5, 0.5));
                }
            }
        }
    }

    /**
     * Get the point position information of the polygon
     */
    public void getPoints(View v) {
        if (null != mPolygon) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mPolygon.getPoints().toArray().length; i++) {
                stringBuilder.append(mPolygon.getPoints().get(i).toString());
            }
            Toast.makeText(this, "Polygon points is " + stringBuilder, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set the outline color of the polygon
     */
    public void setStokeColor(View v) {
        if (null != mPolygon) {
            mPolygon.setStrokeColor(Color.YELLOW);
        }
    }

    /**
     * Get the outline color of the polygon
     */
    public void getStokeColor(View v) {
        if (null != mPolygon) {
            polygonShown.setText("Polygon color is " + Integer.toHexString(mPolygon.getStrokeColor()));
        }
    }

    /**
     * Set the fill color of the polygon
     */
    public void setFillColor(View v) {
        if (null != mPolygon) {
            mPolygon.setFillColor(Color.CYAN);
        }
    }

    /**
     * Get the fill color of the polygon
     */
    public void getFillColor(View v) {
        if (null != mPolygon) {
            polygonShown.setText("Polygon color is " + Integer.toHexString(mPolygon.getFillColor()));
        }
    }

    /**
     * Set the tag of the polygon
     */
    public void setTag(View v) {
        if (null != mPolygon) {
            String tag = polygonTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show();
            } else {
                mPolygon.setTag(tag);
            }
        }
    }

    /**
     * Get the tag of the polygon
     */
    public void getTag(View v) {
        if (null != mPolygon) {
            polygonShown.setText(String.valueOf(mPolygon.getTag() == null ? "Tag is null" : mPolygon.getTag()));
        }
    }

    /**
     * Add polygon click event
     */
    public void addClickEvent(View v) {
        if (null != mPolygon) {
            hMap.setOnPolygonClickListener(new HuaweiMap.OnPolygonClickListener() {
                @Override
                public void onPolygonClick(Polygon circle) {
                    Toast.makeText(getApplicationContext(), "Polygon is clicked.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Set polygons clickable
     */
    public void setClickableTrue(View v) {
        if (null != mPolygon) {
            mPolygon.setClickable(true);
        }
    }

    /**
     * Set polygons are not clickable
     */
    public void setClickableFalse(View v) {
        if (null != mPolygon) {
            mPolygon.setClickable(false);
        }
    }
}
