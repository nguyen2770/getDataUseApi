package com.example.api_demo.api;

import com.example.api_demo.modal.Currency;
import com.example.api_demo.modal.ListSongs;
import com.example.api_demo.modal.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://thantrieu.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(ApiService.class);

    @GET("resources/braniumapis/songs.json")
//    Call<Currency> test(@Query("access_key") String access_key,
//                        @Query("currencies") String currencies,
//                        @Query("source") String source,
//                        @Query("format") int format);
    Call<ListSongs> getSongs();

}
