package com.example.giphyapp.data

import com.example.giphyapp.GiphyConstant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceInstance {
    private var service: GiphyApiService? = null

    fun getGiphyApiService(): GiphyApiService {
        if (service == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(GiphyApiService::class.java)
        }
        return service!!
    }
}