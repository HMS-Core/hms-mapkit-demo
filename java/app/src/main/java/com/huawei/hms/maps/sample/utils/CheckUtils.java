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
 *  2020.1.3-Changed modify the import classes type and add some camera events.
 *                  Huawei Technologies Co., Ltd.
 *
 */

package com.huawei.hms.maps.sample.utils;

import android.text.TextUtils;

public class CheckUtils {
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * Check whether the character string is an integer.
     *
     * @param value String value
     * @return isInteger
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check whether the character string is a floating point number.
     *
     * @param value String value
     * @return isDouble
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkIsEdit(String string) {
        return TextUtils.isEmpty(string);
    }

    public static boolean checkIsRight(String string) {
        return isNumber(string);
    }
}
