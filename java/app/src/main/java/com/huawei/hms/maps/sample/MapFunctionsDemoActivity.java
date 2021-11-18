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
 *  2020.1.3-Changed modify the import classes type and add some map function demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsEdit;
import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight;
import static com.huawei.hms.maps.sample.utils.CheckUtils.isInteger;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.sample.utils.MapUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
@SuppressLint("LongLogTag")
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

    private EditText logoPaddingStart;

    private EditText logoPaddingTop;

    private EditText logoPaddingEnd;

    private EditText logoPaddingBottom;

    private TextView textView;

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
        textView = findViewById(R.id.founctionsshow);
        minZoomlevel = findViewById(R.id.minZoomlevel);
        maxZoomlevel = findViewById(R.id.maxZoomlevel);
        logoPaddingStart = findViewById(R.id.logo_padding_start);
        logoPaddingTop = findViewById(R.id.logo_padding_top);
        logoPaddingEnd = findViewById(R.id.logo_padding_end);
        logoPaddingBottom = findViewById(R.id.logo_padding_bottom);
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
     *
     * @param view view
     */
    public void getMaxZoomLevel(View view) {
        if (null != hMap) {
            textView.setText(String.valueOf(hMap.getMaxZoomLevel()));
        }
    }

    /**
     * Get the minimum zoom level parameter
     *
     * @param view view
     */
    public void getMinZoomLevel(View view) {
        if (null != hMap) {
            textView.setText(String.valueOf(hMap.getMinZoomLevel()));
        }
    }

    /**
     * Get map type
     *
     * @param view view
     */
    public void getMapType(View view) {
        if (null != hMap) {
            textView.setText((hMap.getMapType() == MapUtils.MAP_TYPE_NONE) ? "MAP_TYPE_NONE" : "MAP_TYPE_NORMAL");
        }
    }

    /**
     * Set map type
     *
     * @param view view
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
     *
     * @param view view
     */
    public void is3DMode(View view) {
        if (null != hMap) {
            textView.setText(String.valueOf(hMap.isBuildingsEnabled()));
        }
    }

    /**
     * Turn on the 3D switch
     *
     * @param view view
     */
    public void set3DMode(View view) {
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
     *
     * @param view view
     */
    public void setMaxZoomPreference(View view) {
        String text = maxZoomlevel.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "Please make sure the maxZoom is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (!checkIsRight(text.trim())) {
                Toast.makeText(this, "Please make sure the maxZoom is right", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Float.parseFloat(text.trim()) > MapUtils.MAX_ZOOM_LEVEL
                || Float.parseFloat(text.trim()) < MapUtils.MIN_ZOOM_LEVEL) {
                Toast
                    .makeText(this, String.format(Locale.ENGLISH, "The zoom level ranges from %s to %s.",
                        MapUtils.MIN_ZOOM_LEVEL, MapUtils.MAX_ZOOM_LEVEL), Toast.LENGTH_SHORT)
                    .show();
            } else {
                float maxZoom = Float.parseFloat(maxZoomlevel.getText().toString());
                Log.i(TAG, "setMaxZoomPreference: " + maxZoom);
                if (null != hMap) {
                    hMap.setMaxZoomPreference(maxZoom);
                }
            }
        }
    }

    /**
     * Test the maximum zoom parameter
     *
     * @param view view
     */
    public void testMaxZoom(View view) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(1.0f);
        if (null != hMap) {
            hMap.moveCamera(cameraUpdate);
        }
    }

    /**
     * Set the minimum value of the desired camera zoom level
     *
     * @param view view
     */
    public void setMinZoomPreference(View view) {
        String text = minZoomlevel.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "Please make sure the minZoom is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (!checkIsRight(text.trim())) {
                Toast.makeText(this, "Please make sure the minZoom is right", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Float.parseFloat(text.trim()) > MapUtils.MAX_ZOOM_LEVEL
                || Float.parseFloat(text.trim()) < MapUtils.MIN_ZOOM_LEVEL) {
                Toast
                    .makeText(this, String.format(Locale.ENGLISH, "The zoom level ranges from %s to %s.",
                        MapUtils.MIN_ZOOM_LEVEL, MapUtils.MAX_ZOOM_LEVEL), Toast.LENGTH_SHORT)
                    .show();
            } else {
                if (null != hMap) {
                    hMap.setMinZoomPreference(Float.parseFloat(minZoomlevel.getText().toString()));
                }
            }
        }
    }

    /**
     * Remove the previously set zoom level upper and lower boundary values
     *
     * @param view view
     */
    public void resetMinMaxZoomPreference(View view) {
        if (null != hMap) {
            hMap.resetMinMaxZoomPreference();
        }
    }

    /**
     * Set the map border fill width for the map
     *
     * @param view view
     */
    public void setPadding(View view) {
        String leftString = left.getText().toString();
        String topString = top.getText().toString();
        String rightString = right.getText().toString();
        String bottomString = bottom.getText().toString();

        if (TextUtils.isEmpty(leftString) || TextUtils.isEmpty(topString) || TextUtils.isEmpty(rightString) || TextUtils.isEmpty(bottomString)) {
            Toast.makeText(this, "Please make sure the padding value is Edited", Toast.LENGTH_SHORT).show();
        } else {
            if (!isInteger(leftString.trim()) || !isInteger(topString.trim()) || !isInteger(rightString.trim())
                || !isInteger(bottomString.trim())) {
                Toast.makeText(this, "Please make sure the padding value is right", Toast.LENGTH_SHORT).show();
            } else {
                if (null != hMap) {
                    hMap.setPadding(Integer.parseInt(left.getText().toString()),
                        Integer.parseInt(top.getText().toString()), Integer.parseInt(right.getText().toString()),
                        Integer.parseInt(bottom.getText().toString()));
                }
            }
        }
    }

    /**
     * Setting the logo position: Gravity.BOTTOM | Gravity.START
     *
     * @param view view
     */
    public void setLogoBottomStart(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setLogoPosition(Gravity.BOTTOM | Gravity.START);
        }
    }

    /**
     * Setting the logo position: Gravity.BOTTOM | Gravity.END
     *
     * @param view view
     */
    public void setLogoBottomEnd(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setLogoPosition(Gravity.BOTTOM | Gravity.END);
        }
    }

    /**
     * Setting the logo position: Gravity.TOP | Gravity.START
     *
     * @param view view
     */
    public void setLogoTopStart(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setLogoPosition(Gravity.TOP | Gravity.START);
        }
    }

    /**
     * Setting the logo position: Gravity.TOP | Gravity.END
     *
     * @param view view
     */
    public void setLogoTopEnd(View view) {
        if (null != hMap) {
            hMap.getUiSettings().setLogoPosition(Gravity.TOP | Gravity.END);
        }
    }

    /**
     * Setting the logo padding
     *
     * @param view view
     */
    public void setLogoPadding(View view) {
        if (null != hMap) {
            String paddingStartString = logoPaddingStart.getText().toString().trim();
            String paddingTopString = logoPaddingTop.getText().toString().trim();
            String paddingEndString = logoPaddingEnd.getText().toString().trim();
            String paddingBottomString = logoPaddingBottom.getText().toString().trim();
            if (checkIsEdit(paddingStartString) || checkIsEdit(paddingTopString) || checkIsEdit(paddingEndString)
                || checkIsEdit(paddingBottomString)) {
                Toast.makeText(this, "Please make sure these padding are Edited", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isInteger(paddingStartString) || !isInteger(paddingTopString) || !isInteger(paddingEndString)
                || !isInteger(paddingBottomString)) {
                Toast.makeText(this, "Please make sure these padding are right", Toast.LENGTH_SHORT).show();
                return;
            }

            int paddingStart = Integer.parseInt(paddingStartString);
            int paddingTop = Integer.parseInt(paddingTopString);
            int paddingEnd = Integer.parseInt(paddingEndString);
            int paddingBottom = Integer.parseInt(paddingBottomString);

            hMap.getUiSettings().setLogoPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
        }
    }
}
