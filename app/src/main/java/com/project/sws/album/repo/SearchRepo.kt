package com.project.sws.album.repo

import com.project.sws.album.network.models.SearchAlbumResult

interface SearchRepo {
    suspend fun searchAlbum(term: String): Result<List<SearchAlbumResult>>
}