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
 *  2020.1.3-Changed modify the import classes type and add some groundOverlay demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsEdit;
import static com.huawei.hms.maps.sample.utils.CheckUtils.checkIsRight;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.GroundOverlay;
import com.huawei.hms.maps.model.GroundOverlayOptions;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.LatLngBounds;
import com.huawei.hms.maps.sample.utils.MapUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

/**
 * about groundOverlay
 */
public class GroundOverlayDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "GroundOverlayDemoActivity";

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private GroundOverlay overlay;

    private EditText toprightLatitude;

    private EditText toprightLongtitude;

    private EditText bottomleftLatitude;

    private EditText bottomleftLongtitude;

    private EditText positionLatitude;

    private EditText positionLongtitude;

    private EditText imageWidth;

    private EditText imageHeight;

    private EditText groundOverlayTag;

    private TextView groundOverlayShown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groudoverlay_demo);
        mSupportMapFragment =
            (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInGroundOverlay);
        mSupportMapFragment.getMapAsync(this);

        toprightLatitude = findViewById(R.id.toprightLatitude);
        toprightLongtitude = findViewById(R.id.toprightLongtitude);
        bottomleftLatitude = findViewById(R.id.bottomleftLatitude);
        bottomleftLongtitude = findViewById(R.id.bottomleftLongtitude);
        positionLatitude = findViewById(R.id.positionLatitude);
        positionLongtitude = findViewById(R.id.positionLongtitude);
        imageWidth = findViewById(R.id.imageWidth);
        imageHeight = findViewById(R.id.imageHeight);
        groundOverlayTag = findViewById(R.id.groundOverlayTag);
        groundOverlayShown = findViewById(R.id.groundOverlayShown);
    }

    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.getUiSettings().setZoomControlsEnabled(false);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 10));
    }

    /**
     * Create a GroundOverlay using the images in the assets directory
     */
    public void addFromAsset(View view) {
        if (hMap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        Log.d(TAG, "addFromAsset: ");
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.FRANCE2, 50, 50)
            .image(BitmapDescriptorFactory.fromAsset("images/niuyouguo.jpg"));
        overlay = hMap.addGroundOverlay(options);
        CameraPosition cameraPosition =
            CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18).bearing(0f).tilt(0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        hMap.moveCamera(cameraUpdate);
    }

    /**
     * Create a GroundOverlay using the resources of the bitmap image
     */
    public void addFromResource(View view) {
        if (hMap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        Log.d(TAG, "addFromResource: ");
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.FRANCE2, 50, 50)
            .image(BitmapDescriptorFactory.fromResource(R.drawable.niuyouguo));
        overlay = hMap.addGroundOverlay(options);
        CameraPosition cameraPosition =
            CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18).bearing(0f).tilt(0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        hMap.moveCamera(cameraUpdate);
    }

    /**
     * Create GroundOverlay
     */
    public void addFromBitmap(View view) {
        if (hMap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        Log.d(TAG, "addFromBitmap: ");
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.niuyouguo, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.FRANCE2, 50, 50)
            .image(BitmapDescriptorFactory.fromBitmap(bitmap));
        overlay = hMap.addGroundOverlay(options);
        CameraPosition cameraPosition =
            CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18).bearing(0f).tilt(0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        hMap.moveCamera(cameraUpdate);
    }

    /**
     * Create GroundOverlay
     */
    public void addFromFile(View view) {
        if (hMap == null) {
            return;
        }
        if (null != overlay) {
            overlay.remove();
        }
        Log.d(TAG, "addFromFile: ");
        FileOutputStream out = null;
        String fileName = "maomao.jpg";
        String localFile = getFilesDir() + File.separator + fileName;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("images/niuyouguo.jpg"));
            out = new FileOutputStream(new File(localFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "addFromFile FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            Log.d(TAG, "addFromFile IOException: " + e.toString());
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "addFromFile close fileOutputStream IOException: " + e.toString());
            }
        }
        GroundOverlayOptions options = new GroundOverlayOptions().position(MapUtils.FRANCE2, 30, 60)
            .image(BitmapDescriptorFactory.fromFile(fileName));
        overlay = hMap.addGroundOverlay(options);
        CameraPosition cameraPosition =
            CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18).bearing(0f).tilt(0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        hMap.moveCamera(cameraUpdate);
    }

    /**
     * Create GroundOverlay
     */
    public void addFromPath(View view) {
        if (hMap == null) {
            return;
        }
        Log.d(TAG, "addFromPath");
        if (null != overlay) {
            overlay.remove();
        }
        String path = "/data/data/com.huawei.hms.maps.demo/niuyouguo.jpg";
        FileOutputStream out = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open("images/niuyouguo.jpg"));
            out = new FileOutputStream(new File(path));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "addFromFile FileNotFoundException: " + e.toString());
        } catch (IOException e) {
            Log.d(TAG, "addFromFile IOException: " + e.toString());
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "addFromFile close fileOutputStream IOException: " + e.toString());
            }
        }

        GroundOverlayOptions options =
            new GroundOverlayOptions().position(MapUtils.FRANCE2, 30, 60).image(BitmapDescriptorFactory.fromPath(path));
        GroundOverlay hmapOverlay = hMap.addGroundOverlay(options);
        if (hmapOverlay == null) {
            return;
        }
        overlay = hmapOverlay;
        CameraPosition cameraPosition =
            CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18).bearing(0f).tilt(0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        hMap.moveCamera(cameraUpdate);
    }

    /**
     * Remove the Groudoverlay
     */
    public void removeGroundOverlay(View view) {
        Log.d(TAG, "removeGroudoverlay: ");
        if (null != overlay) {
            overlay.remove();
        }
    }

    /**
     * Get the properties of the GroundOverlay
     */
    public void getAttributes(View view) {
        if (null != overlay) {
            String bounds = null;
            String position = null;
            if (overlay.getBounds() == null) {
                bounds = "null";
            } else {
                bounds = overlay.getBounds().toString();
            }
            if (overlay.getPosition() == null) {
                position = "null";
            } else {
                position = overlay.getPosition().toString();
            }

            Toast
                .makeText(this,
                    "position:" + position + "width:" + overlay.getWidth() + "height:" + overlay.getHeight() + "bounds:"
                        + bounds,
                    Toast.LENGTH_LONG)
                .show();
        }
    }

    /**
     * Set the scope of GroundOverlay
     */
    public void setPointsBy2Points(View view) {
        if (null != overlay) {
            String northeastLatitude = toprightLatitude.getText().toString().trim();
            String northeastLongtitude = toprightLongtitude.getText().toString().trim();
            String southwestLatitude = bottomleftLatitude.getText().toString().trim();
            String southwestLontitude = bottomleftLongtitude.getText().toString().trim();
            if (checkIsEdit(northeastLatitude) || checkIsEdit(northeastLongtitude) || checkIsEdit(southwestLatitude)
                || checkIsEdit(southwestLontitude)) {
                Toast.makeText(this, "Please make sure these latlng are Edited", Toast.LENGTH_SHORT).show();
            } else {
                if (!checkIsRight(northeastLatitude) || !checkIsRight(northeastLongtitude)
                    || !checkIsRight(southwestLatitude) || !checkIsRight(southwestLontitude)) {
                    Toast.makeText(this, "Please make sure these latlng are right", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        overlay.setPositionFromBounds(new LatLngBounds(
                            new LatLng(Double.valueOf(southwestLatitude), Double.valueOf(southwestLontitude)),
                            new LatLng(Double.valueOf(northeastLatitude), Double.valueOf(northeastLongtitude))));
                        CameraPosition cameraPosition = CameraPosition.builder()
                            .target(overlay.getPosition())
                            .zoom(18)
                            .bearing(0f)
                            .tilt(0f)
                            .build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        hMap.moveCamera(cameraUpdate);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, "IllegalArgumentException ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Get the scope of GroundOverlay
     */
    public void getPointsBy2Points(View view) {
        if (null != overlay) {
            if (overlay.getBounds() == null) {
                Toast.makeText(this, "the groundoverlay is added by the other function", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "LatlngBounds :" + overlay.getBounds().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Set the height and width of the GroundOverlay
     */
    public void setPointsBy1PointsWidthHeight(View view) {
        if (null != overlay) {
            String width = imageWidth.getText().toString().trim();
            String height = imageHeight.getText().toString().trim();
            String latitude = positionLatitude.getText().toString().trim();
            String longtitude = positionLongtitude.getText().toString().trim();
            if (checkIsEdit(width) || checkIsEdit(height) || checkIsEdit(latitude) || checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the width & height & position is Edited", Toast.LENGTH_SHORT)
                    .show();
            } else {
                if (!checkIsRight(width) || !checkIsRight(height) || !checkIsRight(latitude)
                    || !checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the width & height & position is right", Toast.LENGTH_SHORT)
                        .show();
                } else {
                    try {
                        if (Float.valueOf(width) < 0.0F || Float.valueOf(height) < 0.0F) {
                            Toast
                                .makeText(this,
                                    "Please make sure the width & height is right, this value must be non-negative",
                                    Toast.LENGTH_SHORT)
                                .show();
                            return;
                        }
                        LatLng position = new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude));
                        overlay.setPosition(position);
                        overlay.setDimensions(Float.valueOf(width), Float.valueOf(height));
                        CameraPosition cameraPosition =
                            CameraPosition.builder().target(position).zoom(18).bearing(0f).tilt(0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        hMap.moveCamera(cameraUpdate);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, "IllegalArgumentException:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Set the height, width, and position of the GroundOverlay
     */
    public void getPointsBy1PointsWidthHeight(View view) {
        if (null != overlay) {
            if (overlay.getPosition() == null || overlay.getHeight() == 0 || overlay.getWidth() == 0) {
                Toast.makeText(this, "the groundoverlay is added by the other function", Toast.LENGTH_SHORT).show();
            } else {
                Toast
                    .makeText(this,
                        "Position :" + overlay.getPosition().toString() + "With :" + overlay.getWidth() + "Height :"
                            + overlay.getHeight(),
                        Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }

    /**
     * Change the image of GroundOverlay
     */
    public void setImage(View view) {
        if (null != overlay) {
            overlay.setImage(BitmapDescriptorFactory.fromResource(R.drawable.makalong));
        }
    }

    /**
     * Get the tag of GroundOverlay
     */
    public void getTag(View v) {
        if (null != overlay) {
            groundOverlayShown.setText("Overlay tag is " + overlay.getTag());
        }
    }

    /**
     * Set the tag of GroundOverlay
     */
    public void setTag(View v) {
        if (null != overlay) {
            String tag = groundOverlayTag.getText().toString().trim();
            if (checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show();
            } else {
                overlay.setTag(tag);
            }
        }
    }

    /**
     * Set GroundOverlay visible
     */
    public void setVisibleTrue(View view) {
        if (null != overlay) {
            overlay.setVisible(true);
        }
    }

    /**
     * Setting GroundOverlay is not visible
     */
    public void setVisibleFalse(View view) {
        if (null != overlay) {
            overlay.setVisible(false);
        }
    }
}
