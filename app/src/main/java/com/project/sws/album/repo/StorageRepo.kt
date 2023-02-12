package com.project.sws.album.repo

import kotlinx.coroutines.flow.Flow

interface StorageRepo {
    suspend fun storeBookmarkedAlbums(ids: Set<Int>)
    val bookMarkedAlbums: Flow<Set<Int>>
}