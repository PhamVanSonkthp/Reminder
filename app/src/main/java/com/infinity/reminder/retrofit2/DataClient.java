package com.infinity.reminder.retrofit2;

import com.infinity.reminder.model.Remind;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataClient {

    @GET("remind")
    Call<List<Remind>> getRemind();
}
