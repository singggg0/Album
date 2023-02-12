package com.project.sws.album.network.models


import com.google.gson.annotations.SerializedName

data class SearchResult<T>(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<T>
)