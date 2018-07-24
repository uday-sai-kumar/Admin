package com.example.udaysaikumar.clgattendance.RetrofitPack;
import com.example.udaysaikumar.clgattendance.Login.LoginData;
import com.example.udaysaikumar.clgattendance.MarksPack.MarksData;
import com.example.udaysaikumar.clgattendance.UserDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroGet {
    @GET("collections/{coll}")
    Call<List<LoginData>> getMlabData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
    @GET("collections/{coll}")
    Call<List<UserDetails>> getData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q, @Query("f") String f);
    @GET("collections/{coll}")
    Call<List<MarksData>> getMarksData(@Path("coll")String coll, @Query("apiKey") String key, @Query("q") String q);
}
