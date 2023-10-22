package com.example.giphyapp.data

data class GiphyResponse(val data: List<GiphyModel>) {
    data class GiphyModel(val images: GiphyImage, var title: String) {
        data class GiphyImage(val original: GiphyOriginal) {
            data class GiphyOriginal(val url: String)
        }
    }
}