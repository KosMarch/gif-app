package com.example.giphyapp.data

data class GiphyResponse(
    val data: List<GiphyData>
)

data class GiphyData(
    val images: GiphyImage,
    val title: String
)

data class GiphyImage(
    val original: GiphyOriginal
)

data class GiphyOriginal(
    val url: String
)