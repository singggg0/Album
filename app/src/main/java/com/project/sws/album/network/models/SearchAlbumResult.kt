package com.project.sws.album.network.models


import com.google.gson.annotations.SerializedName

data class SearchAlbumResult(
    @SerializedName("amgArtistId")
    val amgArtistId: Int,
    @SerializedName("artistId")
    val artistId: Int,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("artistViewUrl")
    val artistViewUrl: String,
    @SerializedName("artworkUrl100")
    val artworkUrl100: String,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String,
    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String,
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String,
    @SerializedName("collectionId")
    val collectionId: Int,
    @SerializedName("collectionName")
    val collectionName: String,
    @SerializedName("collectionPrice")
    val collectionPrice: Double,
    @SerializedName("collectionType")
    val collectionType: String,
    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String,
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("trackCount")
    val trackCount: Int,
    @SerializedName("wrapperType")
    val wrapperType: String
)