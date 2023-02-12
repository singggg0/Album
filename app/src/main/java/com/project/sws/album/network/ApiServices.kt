package com.project.sws.album.network

import com.project.sws.album.network.models.SearchAlbumResult
import com.project.sws.album.network.models.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("search?entity=album")
    fun searchAlbum(
        @Query("term") term: String
    ): Call<SearchResult<SearchAlbumResult>>
}