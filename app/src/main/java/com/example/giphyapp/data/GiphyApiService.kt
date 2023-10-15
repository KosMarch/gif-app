package com.example.giphyapp.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApiService {
    @GET("gifs/trending")
    fun getGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("bundle") bundle: String
    ): Call<GiphyResponse>
}