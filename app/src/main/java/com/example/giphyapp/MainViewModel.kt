package com.example.giphyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.giphyapp.data.GiphyResponse
import com.example.giphyapp.data.RetrofitImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel : ViewModel() {
    private val retrofitImpl = RetrofitImpl()
    private val _listGifs = MutableLiveData<List<GiphyResponse.GiphyModel>>()

    private val _errorMessage = MutableLiveData<String>()

    val listGifs: LiveData<List<GiphyResponse.GiphyModel>>
        get() = _listGifs

    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun updateTrendingGifs() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = retrofitImpl.getGifsPic()
                val list = response.data

                if (list.isNotEmpty()) {
                    _listGifs.value = list
                }
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    _errorMessage.value = "Wrong API key"
                } else {
                    _errorMessage.value = "Error receiving data"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Unknown error: ${e.message}"
            }
        }
    }

    fun updateQueryGifs(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = retrofitImpl.getQueryGifs(query)
                val list = response.data

                if (list.isNotEmpty()) {
                    _listGifs.value = list
                }
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    _errorMessage.value = "Wrong API key"
                } else {
                    _errorMessage.value = "Error receiving data"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Unknown error: ${e.message}"
            }
        }
    }
}