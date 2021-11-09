package com.infinity.reminder.retrofit2;

public class APIUtils {
    public static final String baseUrl = "http://20.119.48.31:8088/api/";

    public static DataClient getData(){
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }
}
