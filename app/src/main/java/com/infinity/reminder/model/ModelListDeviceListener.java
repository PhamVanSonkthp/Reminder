package com.infinity.reminder.model;

import com.infinity.reminder.model_objects.SensorInfor;

public interface ModelListDeviceListener {
    void onSuccessStoreSensor(SensorInfor sensorInfor);
    void onFailStoreSensor(String error);
}
