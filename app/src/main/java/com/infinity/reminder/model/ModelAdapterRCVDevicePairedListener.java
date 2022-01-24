package com.infinity.reminder.model;


import com.infinity.reminder.model_objects.SensorInfor;

import java.util.ArrayList;

public interface ModelAdapterRCVDevicePairedListener {
    void onGetData(ArrayList<SensorInfor> arrayList);
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
