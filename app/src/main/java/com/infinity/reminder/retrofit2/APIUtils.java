package com.infinity.reminder.retrofit2;

public class APIUtils {
    public static final String baseUrl = "https://s24h.okechua.com/api/";

    public static DataClient getData(){
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }
}
