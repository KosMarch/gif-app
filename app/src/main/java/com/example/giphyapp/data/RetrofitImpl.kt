package com.example.giphyapp.data

import com.example.giphyapp.GiphyConstant.API_KEY
import com.example.giphyapp.GiphyConstant.BUNDLE
import com.example.giphyapp.GiphyConstant.LIMIT
import com.example.giphyapp.GiphyConstant.OFFSET
import com.example.giphyapp.GiphyConstant.RATING
import com.example.giphyapp.data.ApiServiceInstance.getGiphyApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitImpl {
    private val service = getGiphyApiService()

    suspend fun getGifsPic(): GiphyResponse {
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