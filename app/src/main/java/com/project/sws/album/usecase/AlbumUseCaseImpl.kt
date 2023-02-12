package com.project.sws.album.usecase

import com.project.sws.album.Album
import com.project.sws.album.di.IoDispatcher
import com.project.sws.album.network.models.SearchAlbumResult
import com.project.sws.album.repo.SearchRepo
import com.project.sws.album.repo.StorageRepo
import com.project.sws.album.toDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumUseCaseImpl @Inject constructor(
    private val searchRepo: SearchRepo,
    private val storageRepo: StorageRepo,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AlbumUseCase {
    override val bookmarkedAlbums: Flow<Set<Int>>
        get() = storageRepo.bookMarkedAlbums

    override suspend fun getAlbums(term: String): List<Album> = withContext(dispatcher) {
        val searchResult = searchRepo.searchAlbum(term).getOrNull() ?: emptyList()
        val albums = searchResult.map { it.toAlbum() }
        albums
    }

    override suspend fun updateBookmarkedAlbums(ids: Set<Int>) {
        storageRepo.storeBookmarkedAlbums(ids)
    }

    private fun SearchAlbumResult.toAlbum(): Album {
        return Album(
            collectionId = collectionId,
            collectionName = collectionName,
            artistName = artistName,
            artWorkUrl = artworkUrl100,
            currency = currency,
            price = collectionPrice,
            releaseDate = releaseDate.toDate(),
            isBookmarked = false
        )
    }
}