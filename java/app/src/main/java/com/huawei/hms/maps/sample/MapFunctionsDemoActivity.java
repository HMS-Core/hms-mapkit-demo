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
 *  2020.1.3-Changed modify the import classes type and add some map function demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.sample.utils.MapUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Basical functions
 */
public class MapFunctionsDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapFunctionsDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private EditText left;

    private EditText right;

    private EditText top;

    private EditText bottom;

    private EditText minZoomlevel;

    private EditText maxZoomlevel;

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_map_founctions_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInFunctions);
        mSupportMapFragment.getMapAsync(this);

        left = findViewById(R.id.paddingleft);
        right = findViewById(R.id.paddingright);
        top = findViewById(R.id.paddingtop);
        bottom = findViewById(R.id.paddingbottom);
        text = findViewById(R.id.founctionsshow);
        minZoomlevel = findViewById(R.id.minZoomlevel);
        maxZoomlevel = findViewById(R.id.maxZoomlevel);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.resetMinMaxZoomPreference();
    }

    /**
     * Get the maximum zoom level parameter
     */
    public void getMaxZoomLevel(View view) {
        if (null != hMap) {
            text.setText(String.valueOf(hMap.getMaxZoomLevel()));
        }
    }

    /**
     * Get the minimum zoom level parameter
     */
    public void getMinZoomLevel(View view) {
        if (null != hMap) {
            text.setText(String.valueOf(hMap.getMinZoomLevel()));
        }
    }

    /**
     * Get map type
     */
    public void getMapType(View view) {
        if (null != hMap) {
            text.setText((hMap.getMapType() == MapUtils.MAP_TYPE_NONE) ? "MAP_TYPE_NONE" : "MAP_TYPE_NORMAL");
        }
    }

    /**
     * Set map type
     */
    public void setMapType(View view) {
        if (null != hMap) {
            synchronized (hMap) {
                if (hMap.getMapType() == MapUtils.MAP_TYPE_NORMAL) {
                    hMap.setMapType(HuaweiMap.MAP_TYPE_NONE);
                } else {
                    hMap.setMapType(HuaweiMap.MAP_TYPE_NORMAL);
                }
            }
        }
    }

    /**
     * Get 3D mode settings
     */
    public void is3DMode(View view) {
        if (null != hMap) {
            text.setText(String.valueOf(hMap.isBuildingsEnabled()));
        }
    }

    /**
     * Turn on the 3D switch
     */
    public void Set3DMode(View view) {
        if (null != hMap) {
            if (hMap.isBuildingsEnabled()) {
                hMap.setBuildingsEnabled(false);
            } else {
                hMap.setBuildingsEnabled(true);
            }
        }
    }

    /**
     * Set the maximum value of the desired camera zoom level
     */
    public void setMaxZoomPreference(View view) {
        String text = maxZoomlevel.getText().toString();
        if ((text.trim().length() == 0) || (text.trim().isEmpty()) || (text == null) || ("".equals(text))) {
            Toast.makeText(this, "Please make sure the maxZoom is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (!isNumber(text.trim())) {
                Toast.makeText(this, "Please make sure the maxZoom is right", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Float.valueOf(text.trim()) > MapUtils.MAX_ZOOM_LEVEL
                || Float.valueOf(text.trim()) < MapUtils.MIN_ZOOM_LEVEL) {
                Toast
                    .makeText(this, String.format(Locale.ENGLISH, "The zoom level ranges from %s to %s.",
                        MapUtils.MIN_ZOOM_LEVEL, MapUtils.MAX_ZOOM_LEVEL), Toast.LENGTH_SHORT)
                    .show();
            } else {
                Float maxZoom = Float.valueOf(maxZoomlevel.getText().toString());
                Log.i(TAG, "setMaxZoomPreference: " + maxZoom);
                if (null != hMap) {
                    hMap.setMaxZoomPreference(maxZoom);
                }
            }
        }
    }

    /**
     * Test the maximum zoom parameter
     */
    public void testMaxZoom(View view) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(1.0f);
        if (null != hMap) {
            hMap.moveCamera(cameraUpdate);
        }
    }

    /**
     * Set the minimum value of the desired camera zoom level
     */
    public void setMinZoomPreference(View view) {
        String text = minZoomlevel.getText().toString();
        if ((text.trim().length() == 0) || (text.trim().isEmpty()) || (text == null) || ("".equals(text))) {
            Toast.makeText(this, "Please make sure the minZoom is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (!isNumber(text.trim())) {
                Toast.makeText(this, "Please make sure the minZoom is right", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Float.valueOf(text.trim()) > MapUtils.MAX_ZOOM_LEVEL
                || Float.valueOf(text.trim()) < MapUtils.MIN_ZOOM_LEVEL) {
                Toast
                        .makeText(this, String.format(Locale.ENGLISH, "The zoom level ranges from %s to %s.",
                                MapUtils.MIN_ZOOM_LEVEL, MapUtils.MAX_ZOOM_LEVEL), Toast.LENGTH_SHORT)
                        .show();
            } else {
                if (null != hMap) {
                    hMap.setMinZoomPreference(Float.valueOf(minZoomlevel.getText().toString()));
                }
            }
        }
    }

    /**
     * Remove the previously set zoom level upper and lower boundary values
     */
    public void resetMinMaxZoomPreference(View view) {
        if (null != hMap) {
            hMap.resetMinMaxZoomPreference();
        }
    }

    /**
     * Set the map border fill width for the map
     */
    public void setPadding(View view) {
        String leftString = left.getText().toString();
        String topString = top.getText().toString();
        String rightString = right.getText().toString();
        String bottomString = bottom.getText().toString();

        if ((leftString.trim().length() == 0) || (leftString.trim().isEmpty()) || (leftString == null)
            || ("".equals(leftString)) || (topString.trim().length() == 0) || (topString.trim().isEmpty())
            || (topString == null) || ("".equals(topString)) || (rightString.trim().length() == 0)
            || (rightString.trim().isEmpty()) || (rightString == null) || ("".equals(rightString))
            || (bottomString.trim().length() == 0) || (bottomString.trim().isEmpty()) || (bottomString == null)
            || ("".equals(bottomString))) {
        } else {
            if (!isNumber(leftString.trim()) || !isNumber(topString.trim()) || !isNumber(rightString.trim())
                || !isNumber(bottomString.trim())) {
                Toast.makeText(this, "Please make sure the padding value is right", Toast.LENGTH_SHORT).show();
            } else {
                if (null != hMap) {
                    hMap.setPadding(Integer.valueOf(left.getText().toString()),
                        Integer.valueOf(top.getText().toString()), Integer.valueOf(right.getText().toString()),
                        Integer.valueOf(bottom.getText().toString()));
                }
            }
        }
    }

    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * Determine if the string is an integer
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Determine if the string is a float
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains(".")) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
