package com.infinity.reminder.retrofit2;

public class APIUtils {
    public static final String baseUrl = "http://52.149.215.72:8088/api/";
    public static final String PBAP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public static DataClient getData(){
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }
}
