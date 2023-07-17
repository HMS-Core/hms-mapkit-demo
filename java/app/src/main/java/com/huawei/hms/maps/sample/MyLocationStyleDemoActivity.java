/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2023. All rights reserved.
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

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MyLocationStyle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * about MyLocationStyle
 */
public class MyLocationStyleDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MyLocationStyleDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private TextView myLocationStyleShown;

    private ImageView imageViewIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylocationstyle_demo);
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInPolyline);
        mSupportMapFragment.getMapAsync(this);
        myLocationStyleShown = findViewById(R.id.myLocationStyleShown);
        imageViewIcon = findViewById(R.id.imageViewIcon);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.getUiSettings().setMyLocationButtonEnabled(true);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));

        hMap.setOnMyLocationButtonClickListener(new HuaweiMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(MyLocationStyleDemoActivity.this,
                        "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        hMap.setOnMyLocationClickListener(new HuaweiMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(Location location) {
                Toast.makeText(MyLocationStyleDemoActivity.this, "onMyLocationClick: ("
                        + location.getLatitude() +
                        "," + location.getLongitude() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set my location style.
     *
     * @param view view
     */
    public void setMyLocationStyle(View view) {
        // resourceId为图标资源
        int resourceId = R.drawable.avocado;
        MyLocationStyle style = new MyLocationStyle()
                .anchor(0.5f, 0.5f)
                .radiusFillColor(Color.RED)
                .myLocationIcon(BitmapDescriptorFactory.fromResource(resourceId));
        hMap.setMyLocationStyle(style);
    }

    /**
     * Get my location style.
     *
     * @param view view
     */
    public void getMyLocationStyle(View view) {
        MyLocationStyle myLocationStyle = hMap.getMyLocationStyle();
        if (myLocationStyle == null) {
            Toast.makeText(this, "Please call the 'setMyLocationStyle' interface first.", Toast.LENGTH_SHORT).show();
            return;
        }

        BitmapDescriptor locationIcon = myLocationStyle.getMyLocationIcon();

        Bitmap bitmap = ObjectWrapper.unwrap(locationIcon.getObject());
        imageViewIcon.setImageBitmap(bitmap);

        float anchorU = myLocationStyle.getAnchorU();
        float anchorV = myLocationStyle.getAnchorV();
        int color = myLocationStyle.getRadiusFillColor();
        String text = "getMyLocationStyle:\nanchorU:" + anchorU
                + ",anchorV:" + anchorV
                + ",color:" + Integer.toHexString(color)
                + ",icon:" + bitmap;
        myLocationStyleShown.setText(text);
    }
}
