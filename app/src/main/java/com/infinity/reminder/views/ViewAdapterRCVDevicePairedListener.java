package com.infinity.reminder.views;

import com.infinity.reminder.model_objects.SensorInfor;

import java.util.ArrayList;

public interface ViewAdapterRCVDevicePairedListener {
    void onGetData(ArrayList<SensorInfor> arrayList);
    void onLoaded();
    void onLoadMore();
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
