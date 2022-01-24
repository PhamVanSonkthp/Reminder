package com.infinity.reminder.model;

import androidx.annotation.NonNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelListDevice {

    private ModelListDeviceListener modelListDeviceListener;

    public ModelListDevice(ModelListDeviceListener modelListDeviceListener) {
        this.modelListDeviceListener = modelListDeviceListener;
    }

    public void handleStoreSensor(String token , String name , String datetime , String macDevice){
//        DataClient dataClient = APIUtils.getData();
//        final Call<SensorInfor> callback = dataClient.storeSensor(token , name , datetime , macDevice);
//        callback.enqueue(new Callback<SensorInfor>() {
//            @Override
//            public void onResponse(@NonNull Call<SensorInfor> call, @NonNull Response<SensorInfor> response) {
//                if (response.body() != null){
//                    modelListDeviceListener.onSuccessStoreSensor(response.body());
//                }else {
//                    modelListDeviceListener.onFailStoreSensor("error" + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<SensorInfor> call, @NonNull Throwable t) {
//                modelListDeviceListener.onFailStoreSensor(t.getMessage());
//                //Protector.appendLog(true ,t.getMessage());
//            }
//        });
    }
}
