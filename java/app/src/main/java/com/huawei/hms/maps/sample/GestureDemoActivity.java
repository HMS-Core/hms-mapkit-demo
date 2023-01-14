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

    @SuppressWarnings("FieldCanBeLocal")
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
        assert mSupportMapFragment != null;
        mSupportMapFragment.getMapAsync(this);

        mMyLocationButtonCheckbox = findViewById(R.id.isShowMylocationButton);
        mMyLocationLayerCheckbox = findViewById(R.id.isMyLocationLayerEnabled);
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
     *
     * @param view view
     */
    public void setZoomButtonsEnabled(View view) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomControlsEnabled(((CheckBox) view).isChecked());
    }

    /**
     * Set compass available
     *
     * @param view view
     */
    public void setCompassEnabled(View view) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setCompassEnabled(((CheckBox) view).isChecked());
    }

    /**
     * Set my location button is available
     *
     * @param view view
     */
    public void setMyLocationButtonEnabled(View view) {
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
     *
     * @param view view
     */
    public void setMyLocationLayerEnabled(View view) {
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
     *
     * @param view view
     */
    public void setScrollGesturesEnabled(View view) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * Set zoom gestures available
     *
     * @param view view
     */
    public void setZoomGesturesEnabled(View view) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * Set tilt gestures available
     *
     * @param view view
     */
    public void setTiltGesturesEnabled(View view) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setTiltGesturesEnabled(((CheckBox) view).isChecked());
    }

    /**
     * Set the rotation gesture available
     *
     * @param view view
     */
    public void setRotateGesturesEnabled(View view) {
        if (!checkReady()) {
            return;
        }
        mUiSettings.setRotateGesturesEnabled(((CheckBox) view).isChecked());
    }
}
