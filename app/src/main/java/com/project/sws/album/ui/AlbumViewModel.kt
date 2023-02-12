package com.project.sws.album.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.sws.album.Album
import com.project.sws.album.usecase.AlbumUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(private val useCase: AlbumUseCase) : ViewModel() {
    private val bookmarkedAlbumIds = useCase.bookmarkedAlbums.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptySet())

    private val fetchedAlbums = MutableStateFlow<List<Album>>(emptyList())
    private val _albums: Flow<List<Album>> = combine(fetchedAlbums, bookmarkedAlbumIds) { albums, bookmarkedIds ->
        val mutableAlbums = albums.toMutableList()
        mutableAlbums.forEachIndexed { index, album ->
            if (bookmarkedIds.contains(album.collectionId)) {
                mutableAlbums[index] = album.copy(isBookmarked = true)
            }
        }
        mutableAlbums
    }

    val albums: Flow<List<Album>> = _albums
    val bookmarkedAlbums: Flow<List<Album>> = _albums.map { it.filter { it.isBookmarked } }

    fun searchAlbums(term: String) {
        viewModelScope.launch {
            val albums = useCase.getAlbums(term)
            fetchedAlbums.emit(albums)
        }
    }

    fun bookmarkAlbum(id: Int) {
        viewModelScope.launch {
            val bookmarkSet = bookmarkedAlbumIds.value.toMutableSet()
            if (!bookmarkSet.contains(id)) {
                bookmarkSet.add(id)
                useCase.updateBookmarkedAlbums(bookmarkSet)
            }
        }
    }

    fun removeBookmarkAlbum(id: Int) {
        viewModelScope.launch {
            val bookmarkSet = bookmarkedAlbumIds.value.toMutableSet()
            if (bookmarkSet.contains(id)) {
                bookmarkSet.remove(id)
                useCase.updateBookmarkedAlbums(bookmarkSet)
            }
        }
    }
}