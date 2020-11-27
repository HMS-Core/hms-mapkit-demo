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
package com.huawei.hms.maps.sample

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.HuaweiMap.InfoWindowAdapter
import com.huawei.hms.maps.HuaweiMap.OnMarkerDragListener
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.*
import java.util.*

/**
 * Marker related
 */
class MarkerDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "MarkerDemoActivity"
        private val PARIS = LatLng(48.893478, 2.334595)
        private val SERRIS = LatLng(48.7, 2.31)
        private val ORSAY = LatLng(48.85, 2.78)
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var mOrsay: Marker? = null
    private var mParis: Marker? = null
    private var mSerris: Marker? = null
    private var mWindowType = 0
    private lateinit var edtTitle: EditText
    private lateinit var edtSnippet: EditText
    private lateinit var edtTag: EditText
    private lateinit var txtvResultShown: TextView
    private lateinit var edtCameraLat: EditText
    private lateinit var edtCameraLng: EditText
    private lateinit var edtCameraZoom: EditText
    private lateinit var edtCameraTilt: EditText
    private lateinit var edtCameraBearing: EditText
    private var markerList: MutableList<Marker> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_demo)
        val fragment = supportFragmentManager.findFragmentById(R.id.mapfragment_markerdemo)
        if (fragment is SupportMapFragment) {
            mSupportMapFragment = fragment
            mSupportMapFragment?.getMapAsync(this)
        }
        edtTitle = findViewById(R.id.edt_title)
        edtSnippet = findViewById(R.id.edt_snippet)
        edtTag = findViewById(R.id.edt_tag)
        txtvResultShown = findViewById(R.id.markerdemo_result_shown)
        edtCameraLat = findViewById(R.id.edt_camera_lat)
        edtCameraLng = findViewById(R.id.edt_camera_lng)
        edtCameraZoom = findViewById(R.id.edt_camera_zoom)
        edtCameraTilt = findViewById(R.id.edt_camera_tilt)
        edtCameraBearing = findViewById(R.id.edt_camera_bearing)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.setInfoWindowAdapter(CustomInfoWindowAdapter())
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 14f))
    }

    internal inner class CustomInfoWindowAdapter : InfoWindowAdapter {
        private val mWindowView: View
        private val mContentsView: View
        override fun getInfoWindow(marker: Marker): View? {
            val view: View? = null
            Log.d(TAG, "getInfoWindow")
            if (mWindowType != 3) {
                return view
            }
            render(marker, mWindowView)
            return mWindowView
        }

        override fun getInfoContents(marker: Marker): View? {
            val view: View? = null
            Log.d(TAG, "getInfoContents")
            if (mWindowType != 2) {
                return view
            }
            render(marker, mContentsView)
            return mContentsView
        }

        private fun render(marker: Marker, view: View) {
            setMarkerBadge(marker, view)
            setMarkerTextView(marker, view)
            setMarkerSnippet(marker, view)
        }

        private fun setMarkerBadge(marker: Marker, view: View) {
            val markerBadge: Int
            // Use the equals method to determine if the marker is the same ,do not use"=="
            markerBadge = if (marker == mParis) {
                R.drawable.badge_bj
            } else if (marker == mOrsay) {
                R.drawable.badge_sh
            } else if (marker == mSerris) {
                R.drawable.badge_nj
            } else {
                0
            }
            (view.findViewById<View>(R.id.imgv_badge) as ImageView).setImageResource(markerBadge)
        }

        private fun setMarkerTextView(marker: Marker, view: View) {
            val markerTitle = marker.title
            var titleView: TextView? = null
            val viewObj: Any = view.findViewById(R.id.txtv_titlee)
            if (viewObj is TextView) {
                titleView = viewObj
            }
            if (markerTitle == null) {
                titleView?.text = ""
            } else {
                val titleText = SpannableString(markerTitle)
                titleText.setSpan(ForegroundColorSpan(Color.BLUE), 0, titleText.length, 0)
                titleView?.text = titleText
            }
        }

        private fun setMarkerSnippet(marker: Marker, view: View) {
            var markerSnippet = marker.snippet
            if (marker.tag != null) {
                markerSnippet = marker.tag as String
            }
            val snippetView = view.findViewById<View>(R.id.txtv_snippett) as TextView
            if (markerSnippet != null && !markerSnippet.isEmpty()) {
                val snippetText = SpannableString(markerSnippet)
                snippetText.setSpan(ForegroundColorSpan(Color.RED), 0, markerSnippet.length, 0)
                snippetView.text = snippetText
            } else {
                snippetView.text = ""
            }
        }

        init {
            mWindowView = layoutInflater.inflate(R.layout.custom_info_window, null)
            mContentsView = layoutInflater.inflate(R.layout.custom_info_contents, null)
        }
    }

    /**
     * Add a marker to the map
     *
     * @param view
     */
    fun addMarker(view: View?) {
        if (mParis == null && mOrsay == null && mSerris == null) {
            // Uses a colored icon.
            mParis = hMap?.addMarker(MarkerOptions().position(PARIS).title("paris").snippet("hello").clusterable(true))
            mOrsay = hMap?.addMarker(MarkerOptions().position(ORSAY)
                    .alpha(0.5f)
                    .title("Orsay")
                    .snippet("hello")
                    .clusterable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.badge_ph)))
            mSerris = hMap?.addMarker(MarkerOptions().position(SERRIS)
                    .title("Serris")
                    .snippet("Can be dragged after DragMarker.")
                    .clusterable(true))
            hMap?.setOnMarkerClickListener { marker ->
                val clusterable = marker.isClusterable
                Toast.makeText(applicationContext, "marker clusterable: $clusterable", Toast.LENGTH_SHORT).show()
                false
            }
        }

        // Add a marker when the point is long clicked.
        hMap?.setOnMapLongClickListener { latLng ->
            Log.d(TAG, "Map is long clicked.")
            val mMarker = hMap?.addMarker(MarkerOptions().position(latLng).title("I am Marker!"))
            if (mMarker != null) {
                markerList.add(mMarker)
            }
            Log.d(TAG, "markerList size is." + markerList.size)
        }
        addMarkerListener()
    }

    /**
     * Set the listener associated with the marker
     */
    private fun addMarkerListener() {
        hMap?.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {
                Log.i(TAG, "onMarkerDragStart: ")
            }

            override fun onMarkerDrag(marker: Marker) {
                Log.i(TAG, "onMarkerDrag: ")
            }

            override fun onMarkerDragEnd(marker: Marker) {
                Log.i(TAG, "onMarkerDragEnd: ")
            }
        })
        hMap?.setOnInfoWindowClickListener { marker ->
            if (marker == mSerris) {
                Toast.makeText(applicationContext, "mMelbourne infowindow is clicked", Toast.LENGTH_SHORT)
                        .show()
            }
            if (marker == mOrsay) {
                Toast.makeText(applicationContext, "mSydney infowindow is clicked", Toast.LENGTH_SHORT).show()
            }
            if (marker == mParis) {
                Toast.makeText(applicationContext, "mBrisbane infowindow is clicked", Toast.LENGTH_SHORT)
                        .show()
            }
        }
        hMap?.setOnInfoWindowCloseListener { Toast.makeText(applicationContext, "infowindowclose", Toast.LENGTH_SHORT).show() }
        hMap?.setOnInfoWindowLongClickListener { Toast.makeText(applicationContext, "onInfoWindowLongClick", Toast.LENGTH_SHORT).show() }
    }

    /**
     * Remove the marker from the map
     *
     * @param view
     */
    fun deleteMarker(view: View?) {
        if (null != mSerris) {
            mSerris?.remove()
            mSerris = null
        }
        if (null != mOrsay) {
            mOrsay?.remove()
            mOrsay = null
        }
        if (null != mParis) {
            mParis?.remove()
            mParis = null
        }

        // remove the markers added by long click.
        if (null != markerList && markerList.size > 0) {
            for (iMarker in markerList) {
                iMarker.remove()
            }
            markerList.clear()
        }
    }

    /**
     * Set the tag attribute of the marker
     *
     * @param view
     */
    fun setTag(view: View?) {
        val tagStr = edtTag.text.toString().trim()
        if (mParis != null) {
            mParis?.tag = tagStr
            Toast.makeText(this, "Paris tag is " + mParis?.tag, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set the snippet attribute of the marker
     *
     * @param view
     */
    fun setSnippet(view: View?) {
        val snippetStr = edtSnippet.text.toString()
        if (mParis != null) {
            mParis?.snippet = snippetStr
            Toast.makeText(this, "Paris snippet is " + mParis?.snippet, Toast.LENGTH_SHORT).show()
        }
    }

    fun defaultWindow(view: View?) {
        mWindowType = 1
    }

    fun contentWindow(view: View?) {
        mWindowType = 2
    }

    fun customWindow(view: View?) {
        mWindowType = 3
    }

    /**
     * Set the marker to drag
     *
     * @param view
     */
    fun dragMarker(view: View?) {
        if (null == mSerris) {
            return
        }
        mSerris?.isDraggable = true
    }

    /**
     * Set the icon attribute of the marker
     *
     * @param view
     */
    fun setMarkerIcon(view: View?) {
        if (null != mOrsay) {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.badge_tr)
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
            mOrsay?.setIcon(bitmapDescriptor)
        }
    }

    /**
     * Set the anchor attribute of the marker
     *
     * @param view
     */
    fun setMarkerAnchor(view: View?) {
        if (mParis != null) {
            mParis?.setMarkerAnchor(0.9f, 0.9f)
        }
    }

    /**
     * Get the latitude and longitude of the marker
     *
     * @param view
     */
    fun getPosition(view: View?) {
        if (mParis != null) {
            val latLng = mParis?.position
            val latitude = latLng?.latitude
            val longitude = latLng?.longitude
            txtvResultShown.text = "mBrisbane $latitude $longitude"
        }
    }

    /**
     * Hide the information window of the marker
     *
     * @param view
     */
    fun hideInfoWindow(view: View?) {
        if (null != mParis) {
            mParis?.hideInfoWindow()
        }
    }

    /**
     * Show the information window of the marker
     *
     * @param view
     */
    fun showInfoWindow(view: View?) {
        if (null != mParis) {
            mParis?.showInfoWindow()
        }
    }

    /**
     * Repositions the camera according to the instructions defined in the update
     *
     * @param view
     */
    fun setCamera(view: View?) {
        try {
            var latLng: LatLng? = null
            var zoom = 0f
            var bearing = 0f
            var tilt = 0f
            if (!edtCameraLng.text.isNullOrEmpty() && !edtCameraLat.text.isNullOrEmpty()) {
                latLng = LatLng(edtCameraLat.text.toString().trim().toDouble(), edtCameraLng.text.toString().trim().toDouble())
            }
            if (!edtCameraZoom.text.isNullOrEmpty()) {
                zoom = edtCameraZoom.text.toString().trim().toFloat()
            }
            if (!edtCameraBearing.text.isNullOrEmpty()) {
                bearing = edtCameraBearing.text.toString().trim().toFloat()
            }
            if (!edtCameraTilt.text.isNullOrEmpty()) {
                tilt = edtCameraTilt.text.toString().trim().toFloat()
            }
            val cameraPosition = CameraPosition.builder().target(latLng).zoom(zoom).bearing(bearing).tilt(tilt).build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            hMap?.moveCamera(cameraUpdate)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "IllegalArgumentException $e")
            Toast.makeText(this, "IllegalArgumentException", Toast.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            Log.e(TAG, "NullPointerException $e")
            Toast.makeText(this, "NullPointerException", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set the title attribute of the marker
     *
     * @param view
     */
    fun setTitle(view: View?) {
        val titleStr = edtTitle.text.toString()
        if (mParis != null) {
            mParis?.title = titleStr
            Toast.makeText(this, "Paris title is " + mParis?.title, Toast.LENGTH_SHORT).show()
        }
    }
}