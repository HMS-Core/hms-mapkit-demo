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
 *  2020.1.3-Changed modify the import classes type and add some polyline demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsEdit;
import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight;

import java.util.ArrayList;
import java.util.List;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Polyline;
import com.huawei.hms.maps.model.PolylineOptions;
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

/**
 * about polyline
 */
public class PolylineDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "PolylineDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private Polyline mPolyline;

    private TextView polylineShown;

    private EditText oneLatitude;

    private EditText oneLongtitude;

    private EditText polylineStokeWidth;

    private EditText polylineTag;

    private List<LatLng> points = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polyline_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInPolyline);
        mSupportMapFragment.getMapAsync(this);

        polylineShown = findViewById(R.id.polylineShown);
        oneLatitude = findViewById(R.id.oneLatitude);
        oneLongtitude = findViewById(R.id.oneLongtitude);
        polylineStokeWidth = findViewById(R.id.polylineStokeWidth);
        polylineTag = findViewById(R.id.polylineTag);

        points.add(MapUtils.HUAWEI_CENTER);
        points.add(MapUtils.APARTMENT_CENTER);
        points.add(MapUtils.EPARK_CENTER);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));
    }

    /**
     * Add polyline to the map
     */
    public void addPolyline(View view) {
        if (null == hMap) {
            return;
        }
        if (null != mPolyline) {
            mPolyline.remove();
        }
        mPolyline = hMap.addPolyline(
            new PolylineOptions().add(MapUtils.FRANCE, MapUtils.FRANCE1, MapUtils.FRANCE2, MapUtils.FRANCE3)
                .color(Color.BLUE)
                .width(3));
        hMap.setOnPolylineClickListener(new HuaweiMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Log.i(TAG, "onMapReady:onPolylineClick ");
            }
        });
    }

    /**
     * Remove the polyline
     */
    public void removePolyline(View view) {
        if (null != mPolyline) {
            mPolyline.remove();
        }
        points.clear();
        points.add(MapUtils.HUAWEI_CENTER);
        points.add(MapUtils.APARTMENT_CENTER);
        points.add(MapUtils.EPARK_CENTER);
    }

    /**
     * Set the point position information of the polyline
     */
    public void setOnePoint(View v) {
        if (null != mPolyline) {
            String latitude = oneLatitude.getText().toString().trim();
            String longtitude = oneLongtitude.getText().toString().trim();
            if (checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the latitude & longtitude is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(latitude) || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the latitude & longtitude is right", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    points.add(new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude)));
                    mPolyline.setPoints(points);
                }
            }
        }
    }

    /**
     * Get the point position information of the polyline
     */
    public void getPoints(View v) {
        if (null != mPolyline) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mPolyline.getPoints().toArray().length; i++) {
                stringBuilder.append(mPolyline.getPoints().get(i).toString());
            }
            Toast.makeText(this, "Polyline points is " + stringBuilder, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Set the outline color of the polyline
     */
    public void setStokeColor(View v) {
        if (null != mPolyline) {
            mPolyline.setColor(Color.YELLOW);
        }
    }

    /**
     * Get the outline color of the polyline
     */
    public void getStokeColor(View v) {
        if (null != mPolyline) {
            polylineShown.setText("Polyline color is " + Integer.toHexString(mPolyline.getColor()));
        }
    }

    /**
     * Set the width of the polyline
     */
    public void setWidth(View v) {
        if (null != mPolyline) {
            String width = polylineStokeWidth.getText().toString().trim();
            if (checkIsEdit(width)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(width)) {
                    Toast.makeText(this, "Please make sure the width is right", Toast.LENGTH_SHORT).show();
                } else {
                    if (Float.parseFloat(width) < 0.0F) {
                        Toast
                                .makeText(this,
                                        "Please make sure the width is right, this value must be non-negative",
                                        Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    mPolyline.setWidth(Float.parseFloat(width));
                }
            }
        }
    }

    /**
     * Get the width of the polyline
     */
    public void getWidth(View v) {
        if (null != mPolyline) {
            polylineShown.setText("Polyline width is " + mPolyline.getWidth());
        }
    }

    /**
     * Set the tag of the polyline
     */
    public void setTag(View v) {
        if (null != mPolyline) {
            String tag = polylineTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show();
            } else {
                mPolyline.setTag(tag);
            }
        }
    }

    /**
     * Get the tag of the polyline
     */
    public void getTag(View v) {
        if (null != mPolyline) {
            polylineShown.setText(String.valueOf(mPolyline.getTag() == null ? "Tag is null" : mPolyline.getTag()));
        }
    }

    /**
     * Add polyline click event
     */
    public void addClickEvent(View v) {
        if (null != mPolyline) {
            hMap.setOnPolylineClickListener(new HuaweiMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline circle) {
                    Toast.makeText(getApplicationContext(), "Polyline is clicked.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Set polyline clickable
     */
    public void setClickableTrue(View v) {
        if (null != mPolyline) {
            mPolyline.setClickable(true);
        }
    }

    /**
     * Set polyline are not clickable
     */
    public void setClickableFalse(View v) {
        if (null != mPolyline) {
            mPolyline.setClickable(false);
        }
    }
}
