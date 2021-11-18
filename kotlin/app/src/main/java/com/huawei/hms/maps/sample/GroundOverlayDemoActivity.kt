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

package com.huawei.hms.maps.sample

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.*
import com.huawei.hms.maps.sample.utils.CheckUtils
import com.huawei.hms.maps.sample.utils.MapUtils
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * about groundOverlay
 */
@SuppressLint("LongLogTag")
class GroundOverlayDemoActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "GroundOverlayDemoActivity"
    }

    private var mSupportMapFragment: SupportMapFragment? = null
    private var hMap: HuaweiMap? = null
    private var overlay: GroundOverlay? = null
    private lateinit var toprightLatitude: EditText
    private lateinit var toprightLongtitude: EditText
    private lateinit var bottomleftLatitude: EditText
    private lateinit var bottomleftLongtitude: EditText
    private lateinit var positionLatitude: EditText
    private lateinit var positionLongtitude: EditText
    private lateinit var imageWidth: EditText
    private lateinit var imageHeight: EditText
    private lateinit var groundOverlayTag: EditText
    private lateinit var groundOverlayShown: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groudoverlay_demo)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapInGroundOverlay) as SupportMapFragment?
        mSupportMapFragment?.getMapAsync(this)
        toprightLatitude = findViewById(R.id.toprightLatitude)
        toprightLongtitude = findViewById(R.id.toprightLongtitude)
        bottomleftLatitude = findViewById(R.id.bottomleftLatitude)
        bottomleftLongtitude = findViewById(R.id.bottomleftLongtitude)
        positionLatitude = findViewById(R.id.positionLatitude)
        positionLongtitude = findViewById(R.id.positionLongtitude)
        imageWidth = findViewById(R.id.imageWidth)
        imageHeight = findViewById(R.id.imageHeight)
        groundOverlayTag = findViewById(R.id.groundOverlayTag)
        groundOverlayShown = findViewById(R.id.groundOverlayShown)
    }

    override fun onMapReady(paramHuaweiMap: HuaweiMap) {
        Log.i(TAG, "onMapReady: ")
        hMap = paramHuaweiMap
        hMap?.isMyLocationEnabled = true
        hMap?.uiSettings?.isZoomControlsEnabled = false
        hMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.893478, 2.334595), 10f))
    }

    /**
     * Create a GroundOverlay using the images in the assets directory
     */
    fun addFromAsset(view: View?) {
        if (null != overlay) {
            overlay?.remove()
        }
        Log.d(TAG, "addFromAsset: ")
        val options = GroundOverlayOptions().position(MapUtils.FRANCE2, 50f, 50f)
                .image(BitmapDescriptorFactory.fromAsset("images/niuyouguo.jpg"))
        overlay = hMap?.addGroundOverlay(options)
        val cameraPosition = CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18f).bearing(0f).tilt(0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        hMap?.moveCamera(cameraUpdate)
    }

    /**
     * Create a GroundOverlay using the resources of the bitmap image
     */
    fun addFromResource(view: View?) {
        if (null != overlay) {
            overlay?.remove()
        }
        Log.d(TAG, "addFromResource: ")
        val options = GroundOverlayOptions().position(MapUtils.FRANCE2, 50f, 50f)
                .image(BitmapDescriptorFactory.fromResource(R.drawable.niuyouguo))
        overlay = hMap?.addGroundOverlay(options)
        val cameraPosition = CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18f).bearing(0f).tilt(0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        hMap?.moveCamera(cameraUpdate)
    }

    /**
     * Create GroundOverlay
     */
    fun addFromBitmap(view: View?) {
        if (null != overlay) {
            overlay?.remove()
        }
        Log.d(TAG, "addFromBitmap: ")
        val vectorDrawable = ResourcesCompat.getDrawable(resources, R.drawable.niuyouguo, null)
        vectorDrawable ?: return
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        val options = GroundOverlayOptions().position(MapUtils.FRANCE2, 50f, 50f)
                .image(BitmapDescriptorFactory.fromBitmap(bitmap))
        overlay = hMap?.addGroundOverlay(options)
        val cameraPosition = CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18f).bearing(0f).tilt(0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        hMap?.moveCamera(cameraUpdate)
    }

    /**
     * Create GroundOverlay
     */
    fun addFromFile(view: View?) {
        if (null != overlay) {
            overlay?.remove()
        }
        Log.d(TAG, "addFromFile: ")
        var out: FileOutputStream? = null
        val fileName = "maomao.jpg"
        val localFile = filesDir.toString() + File.separator + fileName
        try {
            val bitmap = BitmapFactory.decodeStream(assets.open("images/niuyouguo.jpg"))
            out = FileOutputStream(File(localFile))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: FileNotFoundException) {
            Log.d(TAG, "addFromFile FileNotFoundException: $e")
        } catch (e: IOException) {
            Log.d(TAG, "addFromFile IOException: $e")
        } finally {
            try {
                if (null != out) {
                    out.flush()
                    out.close()
                }
            } catch (e: IOException) {
                Log.d(TAG, "addFromFile close fileOutputStream IOException: $e")
            }
        }
        val options = GroundOverlayOptions().position(MapUtils.FRANCE2, 30f, 60f)
                .image(BitmapDescriptorFactory.fromFile(fileName))
        overlay = hMap?.addGroundOverlay(options)
        val cameraPosition = CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18f).bearing(0f).tilt(0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        hMap?.moveCamera(cameraUpdate)
    }

    /**
     * Create GroundOverlay
     */
    fun addFromPath(view: View?) {
        Log.d(TAG, "addFromPath")
        if (null != overlay) {
            overlay?.remove()
        }
        val path = getString(R.string.file_path)
        var out: FileOutputStream? = null
        try {
            val bitmap = BitmapFactory.decodeStream(assets.open("images/niuyouguo.jpg"))
            out = FileOutputStream(File(path))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: FileNotFoundException) {
            Log.d(TAG, "addFromFile FileNotFoundException: $e")
        } catch (e: IOException) {
            Log.d(TAG, "addFromFile IOException: $e")
        } finally {
            try {
                if (null != out) {
                    out.flush()
                    out.close()
                }
            } catch (e: IOException) {
                Log.d(TAG, "addFromFile close fileOutputStream IOException: $e")
            }
        }
        val options = GroundOverlayOptions().position(MapUtils.FRANCE2, 30f, 60f).image(BitmapDescriptorFactory.fromPath(path))
        val hmapOverlay = hMap?.addGroundOverlay(options) ?: return
        overlay = hmapOverlay
        val cameraPosition = CameraPosition.builder().target(MapUtils.FRANCE2).zoom(18f).bearing(0f).tilt(0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        hMap?.moveCamera(cameraUpdate)
    }

    /**
     * Remove the Groudoverlay
     */
    fun removeGroundOverlay(view: View?) {
        Log.d(TAG, "removeGroudoverlay: ")
        if (null != overlay) {
            overlay?.remove()
        }
    }

    /**
     * Get the properties of the GroundOverlay
     */
    fun getAttributes(view: View?) {
        if (null != overlay) {
            var bounds: String? = null
            var position: String? = null
            bounds = if (overlay?.bounds == null) {
                "null"
            } else {
                overlay?.bounds.toString()
            }
            position = if (overlay?.position == null) {
                "null"
            } else {
                overlay?.position.toString()
            }
            Toast
                    .makeText(this,
                            "position:" + position + "width:" + overlay?.width + "height:" + overlay?.height + "bounds:"
                                    + bounds,
                            Toast.LENGTH_LONG)
                    .show()
        }
    }

    /**
     * Set the scope of GroundOverlay
     */
    fun setPointsBy2Points(view: View?) {
        if (null != overlay) {
            val northeastLatitude = toprightLatitude.text.toString().trim { it <= ' ' }
            val northeastLongtitude = toprightLongtitude.text.toString().trim { it <= ' ' }
            val southwestLatitude = bottomleftLatitude.text.toString().trim { it <= ' ' }
            val southwestLontitude = bottomleftLongtitude.text.toString().trim { it <= ' ' }
            if (CheckUtils.checkIsEdit(northeastLatitude) || CheckUtils.checkIsEdit(northeastLongtitude) || CheckUtils.checkIsEdit(southwestLatitude)
                    || CheckUtils.checkIsEdit(southwestLontitude)) {
                Toast.makeText(this, "Please make sure these latlng are Edited", Toast.LENGTH_SHORT).show()
            } else {
                if (!CheckUtils.checkIsRight(northeastLatitude) || !CheckUtils.checkIsRight(northeastLongtitude)
                        || !CheckUtils.checkIsRight(southwestLatitude) || !CheckUtils.checkIsRight(southwestLontitude)) {
                    Toast.makeText(this, "Please make sure these latlng are right", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        overlay?.setPositionFromBounds(LatLngBounds(
                                LatLng(southwestLatitude.toDouble(), southwestLontitude.toDouble()),
                                LatLng(northeastLatitude.toDouble(), northeastLongtitude.toDouble())))
                        val cameraPosition = CameraPosition.builder()
                                .target(overlay?.position)
                                .zoom(18f)
                                .bearing(0f)
                                .tilt(0f)
                                .build()
                        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                        hMap?.moveCamera(cameraUpdate)
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(this, "IllegalArgumentException ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Get the scope of GroundOverlay
     */
    fun getPointsBy2Points(view: View?) {
        if (null != overlay) {
            if (overlay?.bounds == null) {
                Toast.makeText(this, "the groundoverlay is added by the other function", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "LatlngBounds :" + overlay?.bounds.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Set the height and width of the GroundOverlay
     */
    fun setPointsBy1PointsWidthHeight(view: View?) {
        if (null != overlay) {
            val width = imageWidth.text.toString().trim { it <= ' ' }
            val height = imageHeight.text.toString().trim { it <= ' ' }
            val latitude = positionLatitude.text.toString().trim { it <= ' ' }
            val longtitude = positionLongtitude.text.toString().trim { it <= ' ' }
            if (CheckUtils.checkIsEdit(width) || CheckUtils.checkIsEdit(height) || CheckUtils.checkIsEdit(latitude) || CheckUtils.checkIsEdit(longtitude)) {
                Toast.makeText(this, "Please make sure the width & height & position is Edited", Toast.LENGTH_SHORT)
                        .show()
            } else {
                if (!CheckUtils.checkIsRight(width) || !CheckUtils.checkIsRight(height) || !CheckUtils.checkIsRight(latitude)
                        || !CheckUtils.checkIsRight(longtitude)) {
                    Toast.makeText(this, "Please make sure the width & height & position is right", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    try {
                        if (width.toFloat() < 0.0f || height.toFloat() < 0.0f) {
                            Toast
                                    .makeText(this,
                                            "Please make sure the width & height is right, this value must be non-negative",
                                            Toast.LENGTH_SHORT)
                                    .show()
                            return
                        }
                        val position = LatLng(latitude.toDouble(), longtitude.toDouble())
                        overlay?.position = position
                        overlay?.setDimensions(width.toFloat(), height.toFloat())
                        val cameraPosition = CameraPosition.builder().target(position).zoom(18f).bearing(0f).tilt(0f).build()
                        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                        hMap?.moveCamera(cameraUpdate)
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(this, "IllegalArgumentException:" + e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    /**
     * Set the height, width, and position of the GroundOverlay
     */
    fun getPointsBy1PointsWidthHeight(view: View?) {
        if (null != overlay) {
            if (overlay?.position == null || overlay?.height == 0f || overlay?.width == 0f) {
                Toast.makeText(this, "the groundoverlay is added by the other function", Toast.LENGTH_SHORT).show()
            } else {
                Toast
                        .makeText(this,
                                "Position :" + overlay?.position.toString() + "With :" + overlay?.width + "Height :"
                                        + overlay?.height,
                                Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    /**
     * Change the image of GroundOverlay
     */
    fun setImage(view: View?) {
        if (null != overlay) {
            overlay?.setImage(BitmapDescriptorFactory.fromResource(R.drawable.makalong))
        }
    }

    /**
     * Get the tag of GroundOverlay
     */
    fun getTag(v: View?) {
        if (null != overlay) {
            groundOverlayShown.text = "Overlay tag is " + overlay?.tag
        }
    }

    /**
     * Set the tag of GroundOverlay
     */
    fun setTag(v: View?) {
        if (null != overlay) {
            val tag = groundOverlayTag.text.toString().trim { it <= ' ' }
            if (CheckUtils.checkIsEdit(tag)) {
                Toast.makeText(this, "Please make sure the tag is Edited", Toast.LENGTH_SHORT).show()
            } else {
                overlay?.tag = tag
            }
        }
    }

    /**
     * Set GroundOverlay visible
     */
    fun setVisibleTrue(view: View?) {
        if (null != overlay) {
            overlay?.isVisible = true
        }
    }

    /**
     * Setting GroundOverlay is not visible
     */
    fun setVisibleFalse(view: View?) {
        if (null != overlay) {
            overlay?.isVisible = false
        }
    }
}