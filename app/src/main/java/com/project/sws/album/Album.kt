package com.project.sws.album

import java.util.*

data class Album(
    val collectionId: Int,
    val collectionName: String,
    val artistName: String,
    val artWorkUrl: String,
    val currency: String,
    val price: Double,
    val releaseDate: Date?,
    val isBookmarked: Boolean
)
