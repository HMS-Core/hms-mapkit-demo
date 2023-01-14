/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.utils;

import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;

public class ScreenUtil {

    /**
     * Obtains the screen width.
     *
     * @param context Context
     * @return device width
     * @noinspection unused
     */
    public static int getScreenWidth(Context context) {
        return DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes().width;
    }

    /**
     * Obtains the screen height.
     *
     * @param context Context
     * @return device height
     */
    public static int getScreenHeight(Context context) {
        return DisplayManager.getInstance().getDefaultDisplay(context).get().getAttributes().height;
    }
}
