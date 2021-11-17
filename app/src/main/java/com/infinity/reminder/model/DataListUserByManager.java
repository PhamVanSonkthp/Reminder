package com.infinity.reminder.model;

import java.util.List;

public class DataListUserByManager {

    private List<UserData> data;

    public DataListUserByManager(List<UserData> data) {
        this.data = data;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }
}
