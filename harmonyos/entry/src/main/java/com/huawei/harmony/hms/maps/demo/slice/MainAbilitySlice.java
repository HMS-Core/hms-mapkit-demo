/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2008-2021. All rights reserved.
 */

package com.huawei.harmony.hms.maps.demo.slice;

import com.huawei.harmony.hms.maps.demo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;

public class MainAbilitySlice extends AbilitySlice {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main); // Load XML Layout

        // Click the button to go to the MapViewDemo.
        Button buttonToMapViewDemo = (Button) findComponentById(ResourceTable.Id_button_toMapViewDemo);
        buttonToMapViewDemo.setClickedListener(listener -> present(new MapViewDemo(), new Intent()));

        // Click the button to go to the MapViewCameraDemo.
        Button buttonToMapViewCameraDemo = (Button) findComponentById(ResourceTable.Id_button_toMapViewCameraDemo);
        buttonToMapViewCameraDemo.setClickedListener(listener -> present(new MapViewCameraDemo(), new Intent()));

        // Click the button to go to the LiteModeDemo.
        Button buttonToLiteModeDemo = (Button) findComponentById(ResourceTable.Id_button_toLiteModeDemo);
        buttonToLiteModeDemo.setClickedListener(listener -> present(new LiteModeDemo(), new Intent()));

        // Click the button to go to the MapTypeDemo
        Button buttonToMapTypeDemo = (Button) findComponentById(ResourceTable.Id_button_toMapTypeDemo);
        buttonToMapTypeDemo.setClickedListener(listener -> present(new MapTypeDemo(), new Intent()));

        // Click the button to go to the CircleDemo
        Button buttonToCircleDemo = (Button) findComponentById(ResourceTable.Id_button_toCircleDemo);
        buttonToCircleDemo.setClickedListener(listener -> present(new CircleDemo(), new Intent()));

        // Click the button to go to the PolylineDemo
        Button buttonToPolylineDemo = (Button) findComponentById(ResourceTable.Id_button_toPolylineDemo);
        buttonToPolylineDemo.setClickedListener(listener -> present(new PolylineDemo(), new Intent()));

        // Click the button to go to the PolygonDemo
        Button buttonToPolygonDemo = (Button) findComponentById(ResourceTable.Id_button_toPolygonDemo);
        buttonToPolygonDemo.setClickedListener(listener -> present(new PolygonDemo(), new Intent()));

        // Click the button to go to the MarkerDemo
        Button buttonToMarkerDemo = (Button) findComponentById(ResourceTable.Id_button_toMarkerDemo);
        buttonToMarkerDemo.setClickedListener(listener -> present(new MarkerDemo(), new Intent()));

        // Click the button to go to the GestureDemo
        Button buttonToGestureDemo = (Button) findComponentById(ResourceTable.Id_button_toGestureDemo);
        buttonToGestureDemo.setClickedListener(listener -> present(new GestureDemo(), new Intent()));

        // Click the button to go to the HuaweiMapOptionsDemo
        Button buttonToHuaweiMapOptionsDemo = (Button) findComponentById(ResourceTable.Id_button_toHuaweiMapOptionsDemo);
        buttonToHuaweiMapOptionsDemo.setClickedListener(listener -> present(new HuaweiMapOptionsDemo(), new Intent()));

        // Click the button to go to the MapEventsDemo
        Button buttonToMapEventsDemo = (Button) findComponentById(ResourceTable.Id_button_toMapEventsDemo);
        buttonToMapEventsDemo.setClickedListener(listener -> present(new MapEventsDemo(), new Intent()));
    }
}
