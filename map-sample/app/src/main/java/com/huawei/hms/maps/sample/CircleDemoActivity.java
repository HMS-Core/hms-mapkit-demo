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
 *  2020.1.3-Changed modify the import classes type and add some circle demos.
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
import com.huawei.hms.maps.model.Circle;
import com.huawei.hms.maps.model.CircleOptions;
import com.huawei.hms.maps.model.LatLng;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * circle related
 */

public class CircleDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "CircleDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private Circle mCircle;

    private TextView circleShown;

    private EditText centerLatitude;

    private EditText centerLongtitude;

    private EditText circleRadius;

    private EditText circleStokeWidth;

    private EditText circleTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInCircle);
        mSupportMapFragment.getMapAsync(this);

        circleShown = findViewById(R.id.circleShown);
        centerLatitude = findViewById(R.id.centerLatitude);
        centerLongtitude = findViewById(R.id.centerLongtitude);
        circleRadius = findViewById(R.id.circleRadius);
        circleStokeWidth = findViewById(R.id.circleStokeWidth);
        circleTag = findViewById(R.id.circleTag);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 14));
    }

    /**
     * remove the circle
     */
    public void removeCircle(View view) {
        if (null != mCircle) {
            mCircle.remove();
        }
    }

    /**
     * add a circle on the map
     */
    public void addCircle(View view) {
        if (null == hMap) {
            return;
        }
        if (null != mCircle) {
            mCircle.remove();
        }
        mCircle = hMap.addCircle(new CircleOptions().center(new LatLng(48.893478, 2.334595))
            .radius(500)
            .fillColor(0XFF00FFFF)
            .strokeWidth(10)
            .strokeColor(Color.RED));
    }

    /**
     * Set the center of the circle
     */
    public void setCenter(View v) {
        if (null != mCircle) {
            LatLng center = null;
            if (!TextUtils.isEmpty(centerLatitude.getText()) && !TextUtils.isEmpty(centerLongtitude.getText())) {
                String latitude = centerLatitude.getText().toString().trim();
                String longtitude = centerLongtitude.getText().toString().trim();
                center = new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude));
            }
            try {
                mCircle.setCenter(center);
            } catch (NullPointerException e) {
                Log.e(TAG, "NullPointerException " + e.toString());
                Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Get the center coordinates
     */
    public void getCenter(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle center is " + mCircle.getCenter().toString());
        }
    }

    /**
     * Set the radius of the circle
     */
    public void setRadius(View v) {
        if (null != mCircle) {
            String radius = circleRadius.getText().toString().trim();
            if (checkIsEdit(radius)) {
                Toast.makeText(this, "Please make sure the radius is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(radius)) {
                    Toast.makeText(this, "Please make sure the radius is right", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        mCircle.setRadius(Double.valueOf(radius));
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, "IllegalArgumentException ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Get the radius of the circle
     */
    public void getRadius(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle radius is " + mCircle.getRadius());
        }
    }

    /**
     * Get the fill color of the circle
     */
    public void setFillColor(View v) {
        if (null != mCircle) {
            mCircle.setFillColor(Color.RED);
        }
    }

    /**
     * Set the fill color of the circle
     */
    public void getFillColor(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle fill color is " + Integer.toHexString(mCircle.getFillColor()));
        }
    }

    boolean flag = true;

    public void setStokeColor(View v) {
        if (null != mCircle) {
            if (flag) {
                mCircle.setStrokeColor(Color.RED);
                flag = false;
            } else {
                mCircle.setStrokeColor(Color.GRAY);
                flag = true;
            }
        }
    }

    /**
     * Get the outline color of the circle
     */
    public void getStokeColor(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle stroke color is " + Integer.toHexString(mCircle.getStrokeColor()));
        }
    }

    /**
     * Set the outline width of the circle
     */
    public void setWidth(View v) {
        if (null != mCircle) {
            String width = circleStokeWidth.getText().toString().trim();
            if (checkIsEdit(width)) {
                Toast.makeText(this, "Please make sure the width is Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(width)) {
                    Toast.makeText(this, "Please make sure the width is right", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        mCircle.setStrokeWidth(Float.valueOf(width));
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, "IllegalArgumentException " + e.toString());
                        Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Get the outline width of the circle
     */
    public void getWidth(View v) {
        if (null != mCircle) {
            circleShown.setText("Circle stroke width is " + mCircle.getStrokeWidth());
        }
    }

    /**
     * Set the tag of the circle
     */
    public void setTag(View v) {
        if (null != mCircle) {
            String tag = circleTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show();
            } else {
                mCircle.setTag(tag);
            }
        }
    }

    /**
     * Get the tag of the circle
     */
    public void getTag(View v) {
        if (null != mCircle) {
            circleShown.setText(String.valueOf(mCircle.getTag()));
        }
    }

    /**
     * Set click event for circle
     */
    public void addClickEvent(View v) {
        if (null != mCircle) {
            hMap.setOnCircleClickListener(new HuaweiMap.OnCircleClickListener() {
                @Override
                public void onCircleClick(Circle circle) {
                    if (mCircle.equals(circle)) {
                        Toast.makeText(getApplicationContext(), "Circle is clicked.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /**
     * Set the circle clickable to true
     */
    public void setClickableTrue(View v) {
        if (null != mCircle) {
            mCircle.setClickable(true);
        }
    }

    /**
     * Set the circle clickable to false
     */
    public void setClickableFalse(View v) {
        if (null != mCircle) {
            mCircle.setClickable(false);
        }
    }
}
