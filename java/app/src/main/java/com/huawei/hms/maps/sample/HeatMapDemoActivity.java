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
 *  2020.1.3-Changed modify the import classes type and add some groundOverlay demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.HeatMap;
import com.huawei.hms.maps.model.HeatMapOptions;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * about HeatMap
 */
public class HeatMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "HeatMapDemoActivity";

    private static final int BAR_MAX = 100;

    @SuppressWarnings("FieldCanBeLocal")
    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private HeatMap heatMap = null;

    private SeekBar mOpacityBar;

    private SeekBar mRadiusBar;

    private SeekBar mIntensityBar;

    private int colorClickCount;

    private int dataClickCount;

    private boolean visible = true;

    private HeatMapOptions.RadiusUnit unit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapfragment_heatmapdemo);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }

        mOpacityBar = findViewById(R.id.OpacitySeekBar);
        mOpacityBar.setMax(BAR_MAX);
        mOpacityBar.setProgress(0);

        mRadiusBar = findViewById(R.id.RadiusSeekBar);
        mRadiusBar.setMax(BAR_MAX * BAR_MAX * 5);
        mRadiusBar.setProgress(0);

        mIntensityBar = findViewById(R.id.IntensitySeekBar);
        mIntensityBar.setMax(BAR_MAX);
        mIntensityBar.setProgress(0);

    }

    @Override
    public void onMapReady(HuaweiMap huaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = huaweiMap;
        hMap.setMyLocationEnabled(true);
        setListeners();
    }

    /**
     * Add Heat Map
     *
     * @param view view
     */
    public void addHeatMap(View view) {
        if (hMap == null) {
            return;
        }
        if (heatMap != null) {
            heatMap.remove();
            heatMap = null;
        }
        Map<Float, Float> heatMapAttribute = new HashMap<Float, Float>() {
            {
                put(9.0f, 0.0f);
                put(13.0f, 1.0f);
            }
        };
        Map<Float, Float> heatMapAttribute2 = new HashMap<Float, Float>() {
            {
                put(3.0f, 3.0f);
                put(13.0f, 20.0f);
            }
        };
        HeatMapOptions heatMapOptions1 = new HeatMapOptions();
        heatMapOptions1.radius(heatMapAttribute2);
        heatMapOptions1.opacity(heatMapAttribute);
        heatMapOptions1.intensity(-0.1f);
        heatMapOptions1.dataSet(R.raw.earthquakes);
        heatMap = hMap.addHeatMap("heatMap", heatMapOptions1);
        Log.i(TAG, "addHeatMap: " + heatMap.getId() + " ");

        mOpacityBar.setProgress(0);
        mRadiusBar.setProgress(0);
        mIntensityBar.setProgress(0);
    }

    /**
     * Set the listener associated with the heat map
     */
    public void setListeners() {
        mOpacityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (heatMap != null) {
                    heatMap.setOpacity((float) progress / (float) BAR_MAX);
                    Log.d(TAG, "heatMap.setOpacity " + (float) progress / (float) BAR_MAX);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mRadiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (heatMap != null) {
                    if (heatMap.getRadiusUnit() == HeatMapOptions.RadiusUnit.PIXEL) {
                        progress = progress / (BAR_MAX * 5);
                    }
                    heatMap.setRadius((float) progress);
                    Log.d(TAG, "heatMap.setRadius " + (float) progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mIntensityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (heatMap != null) {
                    heatMap.setIntensity((float) progress * 5 / (float) BAR_MAX);
                    Log.d(TAG, "heatMap.setIntensity " + (float) progress * 5 / (float) BAR_MAX);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Remove the heat map
     *
     * @param view view
     */
    public void removeHeatMap(View view) {
        if (heatMap != null) {
            heatMap.remove();
            heatMap = null;
        }
    }

    public void changeColor(View view) {
        if (heatMap == null) {
            return;
        }
        if (colorClickCount == 0) {
            Map<Float, Integer> color = new HashMap<Float, Integer>() {
                {
                    put(0f, Color.argb(0, 33, 102, 172));
                    put(0.2f, Color.argb(255, 103, 169, 207));
                    put(0.4f, Color.argb(255, 209, 229, 240));
                    put(0.6f, Color.argb(255, 253, 219, 199));
                    put(0.8f, Color.argb(255, 239, 138, 98));
                    put(1.0f, Color.argb(255, 178, 24, 43));
                }
            };
            heatMap.setColor(color);
        }
        if (colorClickCount == 1) {
            Map<Float, Integer> color2 = new HashMap<Float, Integer>() {
                {
                    put(0.0f, Color.argb(0, 2, 108, 57));
                    put(0.15f, Color.argb(255 / 3 * 2, 254, 233, 229));
                    put(0.3f, Color.rgb(252, 196, 192));
                    put(0.45f, Color.rgb(249, 134, 172));
                    put(0.6f, Color.rgb(226, 62, 153));
                    put(0.75f, Color.rgb(161, 1, 124));
                    put(0.9f, Color.rgb(93, 0, 111));
                    put(1.0f, Color.rgb(75, 0, 106));
                }
            };
            heatMap.setColor(color2);
        }

        if (colorClickCount == 2) {
            heatMap.setColor(null);
            heatMap.setIntensity((Map<Float, Float>) null);
            heatMap.setOpacity((Map<Float, Float>) null);
            heatMap.setRadius((Map<Float, Float>) null);
        }
        colorClickCount = (colorClickCount + 1) % 3;
    }

    /**
     * Modifying the Data Source of a Heat Map
     *
     * @param view view
     */
    public void changeData(View view) {
        if (heatMap == null) {
            return;
        }
        if (dataClickCount == 0) {
            heatMap.changeDataSet(this, R.raw.earthquakes_with_usa);
        }
        if (dataClickCount == 1) {
            heatMap.changeDataSet(this, R.raw.earthquakes);
        }

        if (dataClickCount == 2) {
            heatMap.changeDataSet(this, R.raw.citycenter);
        }
        dataClickCount = (dataClickCount + 1) % 3;
    }

    /**
     * Modifying the Visibility of a Heat Map
     *
     * @param view view
     */
    public void setHeatMapVisible(View view) {
        if (heatMap == null) {
            return;
        }
        visible = !visible;
        heatMap.setVisible(visible);
    }

    /**
     * Modifying the Radius Units of a Heat Map
     *
     * @param view view
     */
    public void setHeatMapRadiusUnit(View view) {
        if (heatMap == null) {
            return;
        }
        if (unit == HeatMapOptions.RadiusUnit.METER) {
            unit = HeatMapOptions.RadiusUnit.PIXEL;
        } else {
            unit = HeatMapOptions.RadiusUnit.METER;
        }
        heatMap.setRadiusUnit(unit);
    }
}
