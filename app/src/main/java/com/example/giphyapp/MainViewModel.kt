package com.example.giphyapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphyapp.data.GiphyResponse
import com.example.giphyapp.data.GiphyApiService
import com.example.giphyapp.data.GiphyModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "Your api_key"
const val BASE_URL = "https://api.giphy.com/v1/"
const val TAG = "MyLog"

class MainViewModel : ViewModel() {
    val liveDataList = MutableLiveData<List<GiphyModel>>()
    val liveDataCurrent = MutableLiveData<GiphyModel>()

    init {
        getGifsPic()

    }

    fun getGifsPic() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GiphyApiService::class.java)
        val call = service.getGifs(
            API_KEY,
            25,
            0,
            "g",
            "messaging_non_clips"
        )

        call.enqueue(object : Callback<GiphyResponse> {
            override fun onResponse(call: Call<GiphyResponse>, response: Response<GiphyResponse>) {
                if (response.isSuccessful) {
                    val giphyResponse = response.body()
                    if (giphyResponse != null) {
                        parseToModel(giphyResponse)
                    } else {
                        Log.d(TAG, "Response body is null.")
                    }
                } else {
                    Log.d(TAG, "Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GiphyResponse>, t: Throwable) {
            }
        })
    }

    private fun parseToModel(response: GiphyResponse) {
        val list = ArrayList<GiphyModel>()

        for (i in 0 until response.data.size) {
            val item = GiphyModel(
                response.data[i].images.original.url,
                response.data[i].title
            )
            list.add(item)
        }
        if (liveDataCurrent.value == null) {
            liveDataCurrent.value = list[0]
        }
        liveDataList.value = list
    }
}
