package com.infinity.reminder.views;

public interface ViewConnectThread {
    void onGetData(String value);
    void onConnected();
    void onError(String error);
    void onRuned();
}
