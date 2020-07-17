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
 *  2020.1.3-Changed modify the import classes type and add some support languages demo.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Map support multi-language
 */
public class MoreLanguageDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MoreLanguageDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_language_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapInLanguage);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
        }
        mSupportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
    }
}
