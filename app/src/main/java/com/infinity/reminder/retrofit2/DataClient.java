package com.infinity.reminder.retrofit2;

import com.infinity.reminder.model_objects.DataAir;
import com.infinity.reminder.model_objects.DataListUserByManager;
import com.infinity.reminder.model_objects.DataMax30100;
import com.infinity.reminder.model_objects.DataRegister;
import com.infinity.reminder.model_objects.DataSchedule;
import com.infinity.reminder.model_objects.Max30100Request;
import com.infinity.reminder.model_objects.User;
import com.infinity.reminder.model_objects.Wifi;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DataClient {

    @GET("admin/schedule/get-list-schedule-by-patient/{id}")
    Call<DataSchedule> getSchedule(@Header("Authorization") String token , @Path("id") int id);

    @DELETE("admin/schedule/delete/{id}")
    Call<String> deleteSchedule(@Header("Authorization") String token , @Path("id") int id);

    @GET("alert/create-alert-emergency/{id}")
    Call<String> addAlert(@Header("Authorization") String token , @Path("id") int id);

    @FormUrlEncoded
    @POST("admin/schedule/create")
    Call<String> addRemind(@Header("Authorization") String token ,@Field("content") String title, @Field("time") String time, @Field("process") int process, @Field("user_id") int user_id);

    @GET("user/list-user-by-manager")
    Call<DataListUserByManager> getListUserByManager(@Header("Authorization") String token);

    @GET("admin/wifi/get-wifi/{id_device}")
    Call<Wifi> getListWifi(@Header("Authorization") String token,@Header("pageIndex") int pageIndex,@Header("pageSize") int pageSize, @Path("id_device") String id_device);

    @FormUrlEncoded
    @POST("admin/wifi/create")
    Call<String> addListWifi(@Header("Authorization") String token, @Field("id_device") String id_device, @Field("name") String name, @Field("pass") String pass);

    @FormUrlEncoded
    @PUT("admin/wifi/update/{id_device}")
    Call<String> updateListWifi(@Header("Authorization") String token, @Path("id_device") String id_device, @Field("name") String name, @Field("pass") String pass, @Field("status") int status);

    @DELETE("admin/wifi/get-wifi/{id}")
    Call<String> deleteListWifi(@Header("Authorization") String token, @Path("id") String id);

    @GET("airdata/get-list-by-user/{id}")
    Call<DataAir> getDataAir(@Header("Authorization") String token , @Header("time") String time , @Path("id") int id);

    @GET("max30110/get-list-by-user/{id}")
    Call<DataMax30100> getMax30100(@Header("Authorization") String token , @Header("time") String time, @Path("id") int id);

    @FormUrlEncoded
    @POST("auth/login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/delete-relation-user")
    Call<String> deleteUserByDoctor(@Header("Authorization") String token ,@Field("userId") int id);

    @FormUrlEncoded
    @POST("user/add-relation-user")
    Call<String> addUserByDoctor(@Header("Authorization") String token ,@Field("userId") String id);

    @FormUrlEncoded
    @POST("auth/register")
    Call<DataRegister> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("age") int age,
            @Field("role") int role,
            @Field("address") String address,
            @Field("buildingId") int buildingId,
            @Field("lstDoctorId") String lstDoctorId,
            @Field("lstManagerId") String lstManagerId,
            @Field("lstUserId") String lstUserId
    );

    @Multipart
    @POST("add-record")
    Call<ResponseBody> addRecord(@Part MultipartBody.Part file);
}