package com.infinity.reminder.views;


import com.infinity.reminder.model_objects.SensorInfor;

public interface ViewListDeviceListener {
    void onSuccessStoreSensor(SensorInfor sensorInfor);
    void onFailStoreSensor(String error);
}
