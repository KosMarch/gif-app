package com.example.giphyapp.data

import com.example.giphyapp.GiphyConstant.API_KEY
import com.example.giphyapp.GiphyConstant.BASE_URL
import com.example.giphyapp.GiphyConstant.BUNDLE
import com.example.giphyapp.GiphyConstant.LIMIT
import com.example.giphyapp.GiphyConstant.OFFSET
import com.example.giphyapp.GiphyConstant.RATING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {
    suspend fun getGifsPic(): GiphyResponse {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GiphyApiService::class.java)

        return withContext(Dispatchers.IO) {
            service.getGifs(
                API_KEY,
                LIMIT,
                OFFSET,
                RATING,
                BUNDLE
            )
        }
    }

    suspend fun getQueryGifs(query: String): GiphyResponse {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GiphyApiService::class.java)

        return withContext(Dispatchers.IO) {
            service.getQueryGifs(
                API_KEY,
                query,
                LIMIT,
                OFFSET,
                RATING,
                BUNDLE
            )
        }
    }
}