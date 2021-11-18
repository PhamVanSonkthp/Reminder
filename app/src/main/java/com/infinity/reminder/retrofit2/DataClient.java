package com.infinity.reminder.retrofit2;

import com.infinity.reminder.model.DataAir;
import com.infinity.reminder.model.DataListUserByManager;
import com.infinity.reminder.model.DataMax30100;
import com.infinity.reminder.model.DataSchedule;
import com.infinity.reminder.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DataClient {

    @GET("admin/schedule/get-list-schedule-by-patient/{id}")
    Call<DataSchedule> getSchedule(@Header("Authorization") String token , @Path("id") int id);

    @FormUrlEncoded
    @POST("admin/schedule/create")
    Call<String> addRemind(@Header("Authorization") String token ,@Field("content") String title, @Field("time") String time, @Field("process") int process, @Field("user_id") int user_id);

    @GET("user/list-user-by-manager")
    Call<DataListUserByManager> getListUserByManager(@Header("Authorization") String token);

    @GET("airdata/get-list-by-user/{id}")
    Call<DataAir> getDataAir(@Header("Authorization") String token , @Path("id") int id);

    @GET("max30110/get-list-by-user/{id}")
    Call<DataMax30100> getMax30100(@Header("Authorization") String token , @Path("id") int id);

    @FormUrlEncoded
    @POST("auth/login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("add-record")
    Call<ResponseBody> addRecord(@Part MultipartBody.Part file);
}