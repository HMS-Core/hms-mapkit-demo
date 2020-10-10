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
 *  2020.1.3-Changed modify the import classes type and add some marker demos.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample;

import java.util.ArrayList;
import java.util.List;

import com.huawei.hms.maps.CameraUpdate;
import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.BitmapDescriptor;
import com.huawei.hms.maps.model.BitmapDescriptorFactory;
import com.huawei.hms.maps.model.CameraPosition;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.Marker;
import com.huawei.hms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * Marker related
 */
public class MarkerDemoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MarkerDemoActivity";

    private static final LatLng PARIS = new LatLng(48.893478, 2.334595);

    private static final LatLng SERRIS = new LatLng(48.7, 2.31);

    private static final LatLng ORSAY = new LatLng(48.85, 2.78);

    private SupportMapFragment mSupportMapFragment;

    private HuaweiMap hMap;

    private Marker mOrsay;

    private Marker mParis;

    private Marker mSerris;

    private int mWindowType;

    private EditText edtTitle;

    private EditText edtSnippet;

    private EditText edtTag;

    private TextView txtvResultShown;

    private EditText edtCameraLat;

    private EditText edtCameraLng;

    private EditText edtCameraZoom;

    private EditText edtCameraTilt;

    private EditText edtCameraBearing;

    List<Marker> markerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_demo);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.mapfragment_markerdemo);
        if (fragment instanceof SupportMapFragment) {
            mSupportMapFragment = (SupportMapFragment) fragment;
            mSupportMapFragment.getMapAsync(this);
        }
        edtTitle = findViewById(R.id.edt_title);
        edtSnippet = findViewById(R.id.edt_snippet);
        edtTag = findViewById(R.id.edt_tag);
        txtvResultShown = findViewById(R.id.markerdemo_result_shown);

        edtCameraLat = findViewById(R.id.edt_camera_lat);
        edtCameraLng = findViewById(R.id.edt_camera_lng);
        edtCameraZoom = findViewById(R.id.edt_camera_zoom);
        edtCameraTilt = findViewById(R.id.edt_camera_tilt);
        edtCameraBearing = findViewById(R.id.edt_camera_bearing);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(HuaweiMap paramHuaweiMap) {
        Log.i(TAG, "onMapReady: ");
        hMap = paramHuaweiMap;
        hMap.setMyLocationEnabled(true);
        hMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.893478, 2.334595), 14));
    }

    class CustomInfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {
        private final View mWindowView;

        private final View mContentsView;

        CustomInfoWindowAdapter() {
            mWindowView = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContentsView = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            View view = null;
            Log.d(TAG, "getInfoWindow");
            if (mWindowType != 3) {
                return view;
            }
            render(marker, mWindowView);
            return mWindowView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = null;
            Log.d(TAG, "getInfoContents");
            if (mWindowType != 2) {
                return view;
            }
            render(marker, mContentsView);
            return mContentsView;
        }

        private void render(Marker marker, View view) {
            setMarkerBadge(marker, view);

            setMarkerTextView(marker, view);

            setMarkerSnippet(marker, view);
        }

        private void setMarkerBadge(Marker marker, View view) {
            int markerBadge;
            // Use the equals method to determine if the marker is the same ,do not use"=="
            if (marker.equals(mParis)) {
                markerBadge = R.drawable.badge_bj;
            } else if (marker.equals(mOrsay)) {
                markerBadge = R.drawable.badge_sh;
            } else if (marker.equals(mSerris)) {
                markerBadge = R.drawable.badge_nj;
            } else {
                markerBadge = 0;
            }
            ((ImageView) view.findViewById(R.id.imgv_badge)).setImageResource(markerBadge);
        }

        private void setMarkerTextView(Marker marker, View view) {
            String markerTitle = marker.getTitle();

            TextView titleView = null;

            Object object = view.findViewById(R.id.txtv_titlee);
            if (object instanceof TextView) {
                titleView = (TextView) object;
            }
            if (markerTitle == null) {
                titleView.setText("");
            } else {
                SpannableString titleText = new SpannableString(markerTitle);
                titleText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, titleText.length(), 0);
                titleView.setText(titleText);
            }
        }

        private void setMarkerSnippet(Marker marker, View view) {
            String markerSnippet = marker.getSnippet();
            if (marker.getTag() != null) {
                markerSnippet = (String) marker.getTag();
            }
            TextView snippetView = ((TextView) view.findViewById(R.id.txtv_snippett));
            if (markerSnippet != null && !markerSnippet.isEmpty()) {
                SpannableString snippetText = new SpannableString(markerSnippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.RED), 0, markerSnippet.length(), 0);
                snippetView.setText(snippetText);
            } else {
                snippetView.setText("");
            }
        }
    }

    /**
     * Add a marker to the map
     *
     * @param view
     */
    public void addMarker(View view) {
        if (null == hMap) {
            return;
        }

        if (mParis == null && mOrsay == null && mSerris == null) {
            // Uses a colored icon.
            mParis =
                hMap.addMarker(new MarkerOptions().position(PARIS).title("paris").snippet("hello").clusterable(true));
            mOrsay = hMap.addMarker(new MarkerOptions().position(ORSAY)
                .alpha(0.5f)
                .title("Orsay")
                .snippet("hello")
                .clusterable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)));
            mSerris = hMap.addMarker(new MarkerOptions().position(SERRIS)
                .title("Serris")
                .snippet("Can be dragged after DragMarker.")
                .clusterable(true));
            hMap.setOnMarkerClickListener(new HuaweiMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    boolean clusterable = marker.isClusterable();
                    Toast.makeText(getApplicationContext(), String.valueOf(clusterable), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        // Add a marker when the point is long clicked.
        hMap.setOnMapLongClickListener(new HuaweiMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(TAG, "Map is long clicked.");
                Marker mMarker = hMap.addMarker(new MarkerOptions().position(latLng).title("I am Marker!"));
                markerList.add(mMarker);
                Log.d(TAG, "markerList size is." + markerList.size());
            }
        });

        addMarkerListener();
    }

    /**
     * Set the listener associated with the marker
     */
    private void addMarkerListener() {
        hMap.setOnMarkerDragListener(new HuaweiMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.i(TAG, "onMarkerDragStart: ");
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i(TAG, "onMarkerDrag: ");
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.i(TAG, "onMarkerDragEnd: ");
            }
        });

        hMap.setOnInfoWindowClickListener(new HuaweiMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(mSerris)) {
                    Toast.makeText(getApplicationContext(), "mMelbourne infowindow is clicked", Toast.LENGTH_SHORT)
                        .show();
                }

                if (marker.equals(mOrsay)) {
                    Toast.makeText(getApplicationContext(), "mSydney infowindow is clicked", Toast.LENGTH_SHORT).show();
                }

                if (marker.equals(mParis)) {
                    Toast.makeText(getApplicationContext(), "mBrisbane infowindow is clicked", Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });
        hMap.setOnInfoWindowCloseListener(new HuaweiMap.OnInfoWindowCloseListener() {
            @Override
            public void onInfoWindowClose(Marker marker) {
                Toast.makeText(getApplicationContext(), "infowindowclose", Toast.LENGTH_SHORT).show();
            }
        });
        hMap.setOnInfoWindowLongClickListener(new HuaweiMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "onInfoWindowLongClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Remove the marker from the map
     *
     * @param view
     */
    public void deleteMarker(View view) {
        if (null != mSerris) {
            mSerris.remove();
            mSerris = null;
        }

        if (null != mOrsay) {
            mOrsay.remove();
            mOrsay = null;
        }

        if (null != mParis) {
            mParis.remove();
            mParis = null;
        }

        // remove the markers added by long click.
        if (null != markerList && markerList.size() > 0) {
            for (Marker iMarker : markerList) {
                iMarker.remove();
                iMarker = null;
            }
            markerList.clear();
        }
    }

    /**
     * Set the tag attribute of the marker
     *
     * @param view
     */
    public void setTag(View view) {
        String tagStr = edtTag.getText().toString();
        if (mParis != null && tagStr != null && !"".equals(tagStr)) {
            mParis.setTag(tagStr);
        }
    }

    /**
     * Set the snippet attribute of the marker
     *
     * @param view
     */
    public void setSnippet(View view) {
        String snippetStr = edtSnippet.getText().toString();
        if (mOrsay != null && snippetStr != null && !"".equals(snippetStr)) {
            mOrsay.setSnippet(snippetStr);
        }

    }

    public void defaultWindow(View view) {
        mWindowType = 1;
    }

    public void contentWindow(View view) {
        mWindowType = 2;
    }

    public void customWindow(View view) {
        mWindowType = 3;
    }

    /**
     * Set the marker to drag
     *
     * @param view
     */
    public void dragMarker(View view) {
        if (null == mSerris) {
            return;
        }
        mSerris.setDraggable(true);
    }

    /**
     * Set the icon attribute of the marker
     *
     * @param view
     */
    public void setMarkerIcon(View view) {
        if (null != mOrsay) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.badge_tr);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

            mOrsay.setIcon(bitmapDescriptor);
        }
    }

    /**
     * Set the anchor attribute of the marker
     *
     * @param view
     */
    public void setMarkerAnchor(View view) {
        if (mParis != null) {
            mParis.setMarkerAnchor(0.9F, 0.9F);
        }
    }

    /**
     * Get the latitude and longitude of the marker
     *
     * @param view
     */
    public void getPosition(View view) {
        if (mParis != null) {
            LatLng latLng = mParis.getPosition();
            Double latitude = latLng.latitude;
            Double longitude = latLng.longitude;
            txtvResultShown.setText("mBrisbane " + latitude.toString() + " " + longitude.toString());
        }
    }

    /**
     * Hide the information window of the marker
     *
     * @param view
     */
    public void hideInfoWindow(View view) {
        if (null != mParis) {
            mParis.hideInfoWindow();
        }
    }

    /**
     * Show the information window of the marker
     *
     * @param view
     */
    public void showInfoWindow(View view) {
        if (null != mParis) {
            mParis.showInfoWindow();
        }
    }

    /**
     * Repositions the camera according to the instructions defined in the update
     *
     * @param view
     */
    public void setCamera(View view) {
        try {
            LatLng latLng = null;
            float zoom = 0f;
            float bearing = 0f;
            float tilt = 0f;
            if (!TextUtils.isEmpty(edtCameraLng.getText()) && !TextUtils.isEmpty(edtCameraLat.getText())) {
                latLng = new LatLng(Float.valueOf(edtCameraLat.getText().toString().trim()),
                    Float.valueOf(edtCameraLng.getText().toString().trim()));
            }
            if (!TextUtils.isEmpty(edtCameraZoom.getText())) {
                zoom = Float.valueOf(edtCameraZoom.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(edtCameraBearing.getText())) {
                bearing = Float.valueOf(edtCameraBearing.getText().toString().trim());
            }
            if (!TextUtils.isEmpty(edtCameraTilt.getText())) {
                tilt = Float.valueOf(edtCameraTilt.getText().toString().trim());
            }
            CameraPosition cameraPosition =
                CameraPosition.builder().target(latLng).zoom(zoom).bearing(bearing).tilt(tilt).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            hMap.moveCamera(cameraUpdate);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException " + e.toString());
            Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException " + e.toString());
            Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Set the title attribute of the marker
     *
     * @param view
     */
    public void setTitle(View view) {
        String titleStr = edtTitle.getText().toString();
        if (mParis != null && titleStr != null && !"".equals(titleStr)) {
            mParis.setTitle(titleStr);
            Toast.makeText(this, "BJ title is " + mParis.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
