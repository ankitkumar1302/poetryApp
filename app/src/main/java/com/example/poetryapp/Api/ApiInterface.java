package com.example.poetryapp.Api;

import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.Response.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getpoetry.php")
    Call<GetPoetryResponse> getpoetry();

    @FormUrlEncoded
    @POST("deletepoetry.php")
    Call<DeleteResponse> deletepoetry(@Field("poetry_id")String poetry_id);

    @FormUrlEncoded
    @POST("start.php")
    Call<DeleteResponse> addpoetry(@Field("poetry")String poetryData,@Field("poet_name")String poet_name);

    @FormUrlEncoded
    @POST("updatepoetry.php")
    Call<DeleteResponse> updatepoetry(@Field("poetry_data")String poetryData,@Field("id")String id);

}
