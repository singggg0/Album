package com.project.sws.album.usecase

import com.project.sws.album.Album
import kotlinx.coroutines.flow.Flow

interface AlbumUseCase {
    val bookmarkedAlbums: Flow<Set<Int>>
    suspend fun getAlbums(term: String): List<Album>
    suspend fun updateBookmarkedAlbums(ids: Set<Int>)
}