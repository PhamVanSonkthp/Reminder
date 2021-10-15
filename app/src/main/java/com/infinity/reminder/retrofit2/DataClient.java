package com.infinity.reminder.retrofit2;

import com.infinity.reminder.model.Remind;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataClient {

    @GET("remind")
    Call<List<Remind>> getRemind();

    @FormUrlEncoded
    @POST("remind")
    Call<String> addRemind(@Field("title") String title, @Field("time") String time);

    @FormUrlEncoded
    @POST("login")
    Call<String> login(@Field("username") String username, @Field("password") String password);
}