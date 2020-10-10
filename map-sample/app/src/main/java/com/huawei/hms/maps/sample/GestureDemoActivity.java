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
 *  2020.1.3-Changed modify the import classes type and add some gesture controls demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.UiSettings;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * about gesture
 */
public class GestureDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "GestureDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private UiSettings mUiSettings;

    private CheckBox mMyLocationButtonCheckbox;

    private CheckBox mMyLocationLayerCheckbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInGestures);
        mSupportMapFragment.getMapAsync(this);

        mMyLocationButtonCheckbox = (CheckBox) findViewById(R.id.isShowMylocationButton);
        mMyLocationLayerCheckbox = (CheckBox) findViewById(R.id.isMyLocationLayerEnabled);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(false);
        hMap.getUiSettings().setCompassEnabled(false);
        hMap.getUiSettings().setZoomControlsEnabled(false);
        hMap.getUiSettings().setMyLocationButtonEnabled(false);
        mUiSettings = hMap.getUiSettings();
    }

    private boolean checkReady() {
        if (hMap == null) {
            Toast.makeText(this, "Map is not ready yet", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Set map zoom button available
     */
    public void setZoomButtonsEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomControlsEnabled(((CheckBox) v).isChecked());
    }

    /**
     * Set compass available
     */
    public void setCompassEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setCompassEnabled(((CheckBox) v).isChecked());
    }

    /**
     * Set my location button is available
     */
    public void setMyLocationButtonEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mMyLocationLayerCheckbox.isChecked()) {
                mUiSettings.setMyLocationButtonEnabled(mMyLocationButtonCheckbox.isChecked());
            } else {
                Toast.makeText(this, "Please open My Location Layer first", Toast.LENGTH_LONG).show();
                mMyLocationButtonCheckbox.setChecked(false);
            }

        } else {
            Toast.makeText(this,
                "System positioning permission was not obtained, please turn on system positioning permission first",
                Toast.LENGTH_LONG).show();
            mMyLocationButtonCheckbox.setChecked(false);
        }
    }

    /**
     * Set my location layer available
     */
    public void setMyLocationLayerEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            hMap.setMyLocationEnabled(mMyLocationLayerCheckbox.isChecked());
        } else {
            mMyLocationLayerCheckbox.setChecked(false);
        }
    }

    /**
     * Set scroll gestures available
     */
    public void setScrollGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setScrollGesturesEnabled(((CheckBox) v).isChecked());
    }

    /**
     * Set zoom gestures available
     */
    public void setZoomGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomGesturesEnabled(((CheckBox) v).isChecked());
    }

    /**
     * Set tilt gestures available
     */
    public void setTiltGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setTiltGesturesEnabled(((CheckBox) v).isChecked());
    }

    /**
     * Set the rotation gesture available
     */
    public void setRotateGesturesEnabled(View v) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setRotateGesturesEnabled(((CheckBox) v).isChecked());
    }
}
