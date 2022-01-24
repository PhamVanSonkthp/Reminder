package com.infinity.reminder.presenter;


import com.infinity.reminder.model.ModelListDevice;
import com.infinity.reminder.model.ModelListDeviceListener;
import com.infinity.reminder.model_objects.SensorInfor;
import com.infinity.reminder.views.ViewListDeviceListener;

public class PresenterListDevice implements ModelListDeviceListener {

    private ModelListDevice modelListDevice;
    private ViewListDeviceListener callback;

    public PresenterListDevice(ViewListDeviceListener callback) {
        modelListDevice = new ModelListDevice(this);
        this.callback = callback;
    }

    public void receivedStoreSensor(String token , String name , String datetime , String macDevice){
        modelListDevice.handleStoreSensor(token , name , datetime , macDevice);
    }

    @Override
    public void onSuccessStoreSensor(SensorInfor sensorInfor) {
        callback.onSuccessStoreSensor(sensorInfor);
    }

    @Override
    public void onFailStoreSensor(String error) {
        callback.onFailStoreSensor(error);
    }
}
