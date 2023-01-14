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
 *  2020.1.3-Changed modify the import classes type and add some map  style display demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.HuaweiMapOptions;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MapStyleOptions;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * StyleMap related
 */
public class StyleMapDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "StyleMapDemoActivity";

    @SuppressWarnings("FieldCanBeLocal")
    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private EditText edtStyleId;

    private EditText edtPreviewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_map_demo);
        edtStyleId = findViewById(R.id.edt_style_id);
        edtPreviewId = findViewById(R.id.edt_preview_id);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mSupportMapFragment = (SupportMapFragment) fragmentManager.findFragmentByTag("support_map_fragment");

        if (mSupportMapFragment == null) {
            HuaweiMapOptions huaweiMapOptions = new HuaweiMapOptions();
            // please replace "styleId" with style ID field value in
            huaweiMapOptions.styleId("styleId");
            // please replace "previewId" with preview ID field value in
            huaweiMapOptions.previewId("previewId");
            mSupportMapFragment = SupportMapFragment.newInstance(huaweiMapOptions);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.map_container_layout, mSupportMapFragment, "support_map_fragment");
            fragmentTransaction.commit();
        }

        mSupportMapFragment.getMapAsync(this);
        mSupportMapFragment.onAttach(this);
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        Log.d(TAG, "onMapReady: ");
        hMap = map;
        hMap.getUiSettings().setMyLocationButtonEnabled(false);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));
    }

    /**
     * set map style:night
     *
     * @param view view
     */
    public void setNightStyle(View view) {
        MapStyleOptions styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night_hms);
        hMap.setMapStyle(styleOptions);
    }

    /**
     * set map style:grayscale
     *
     * @param view view
     */
    public void setGrayscaleStyle(View view) {
        MapStyleOptions styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_grayscale_hms);
        hMap.setMapStyle(styleOptions);
    }

    /**
     * set map style:retro
     *
     * @param view view
     */
    public void setRetroStyle(View view) {
        MapStyleOptions styleOptions = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_retro_hms);
        hMap.setMapStyle(styleOptions);
    }

    /**
     * set style ID
     *
     * @param view view
     */
    public void setStyleId(View view) {
        String styleIdStr = edtStyleId.getText().toString();
        if (TextUtils.isEmpty(styleIdStr)) {
            Toast.makeText(this, "Please make sure the style ID is edited", Toast.LENGTH_SHORT).show();
            return;
        }
        if (null != hMap) {
            hMap.setStyleId(styleIdStr);
        }
    }

    /**
     * set preview ID
     *
     * @param view view
     */
    public void setPreviewId(View view) {
        String previewIdStr = edtPreviewId.getText().toString();
        if (TextUtils.isEmpty(previewIdStr)) {
            Toast.makeText(this, "Please make sure the preview ID is edited", Toast.LENGTH_SHORT).show();
            return;
        }
        if (null != hMap) {
            hMap.previewId(previewIdStr);
        }
    }
}
