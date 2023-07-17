/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.huawei.hms.maps.sample.utils

import com.huawei.hms.maps.model.LatLng

object MapUtils {
    val HUAWEI_CENTER = LatLng(31.98559, 118.76613)
    val APARTMENT_CENTER = LatLng(31.97480, 118.75682)
    val EPARK_CENTER = LatLng(31.97846, 118.76454)
    val FRANCE = LatLng(47.893478, 2.334595)
    val FRANCE1 = LatLng(48.993478, 3.434595)
    val FRANCE2 = LatLng(48.693478, 2.134595)
    val FRANCE3 = LatLng(48.793478, 2.334595)
    fun createRectangle(center: LatLng, halfWidth: Double, halfHeight: Double): List<LatLng> {
        return listOf(LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
                LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
                LatLng(center.latitude - halfHeight, center.longitude - halfWidth))
    }

    const val MAP_TYPE_NONE = 0
    const val MAP_TYPE_NORMAL = 1
    const val MAX_ZOOM_LEVEL = 20f
    const val MIN_ZOOM_LEVEL = 3f

    const val API_KEY = "YOUR API KEY"
}