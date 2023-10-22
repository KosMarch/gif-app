package com.example.giphyapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphyapp.data.GiphyResponse
import com.example.giphyapp.data.RetrofitImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val retrofitImpl = RetrofitImpl()

    val liveDataList = MutableLiveData<List<GiphyResponse.GiphyModel>>()
    val liveDataCurrent = MutableLiveData<GiphyResponse.GiphyModel>()

    fun setLiveDataCurrent(item: GiphyResponse.GiphyModel){
        liveDataCurrent.value = item
    }

    fun updateTrendingGifs() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = retrofitImpl.getGifsPic()
            val list = response.data

            if (list.isNotEmpty()) {
                liveDataList.value = list
                if (liveDataCurrent.value == null) {
                    liveDataCurrent.value = list[0]
                }
            }
        }
    }

    fun updateQueryGifs(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = retrofitImpl.getQueryGifs(query)
            val list = response.data

            if (list.isNotEmpty()) {
                liveDataList.value = list
                if (liveDataCurrent.value == null) {
                    liveDataCurrent.value = list[0]
                }
            }
        }
    }
}