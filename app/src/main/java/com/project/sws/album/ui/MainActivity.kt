package com.project.sws.album.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.sws.album.Album
import com.project.sws.album.databinding.ActivityMainBinding
import com.project.sws.album.usecase.AlbumUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: AlbumViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, JackJohnsonAlbumsFragment())
            .commit()

        vm.searchAlbums("jack+johnson")
    }
}

@HiltViewModel
class AlbumViewModel @Inject constructor(private val useCase: AlbumUseCase) : ViewModel() {
    private val bookmarkedAlbums = useCase.bookmarkedAlbums.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptySet())

    private val fetchedAlbums = MutableStateFlow<List<Album>>(emptyList())
    val albums: Flow<List<Album>> = combine(fetchedAlbums, bookmarkedAlbums) { albums, bookmarkedIds ->
        val mutableAlbums = albums.toMutableList()
        mutableAlbums.forEachIndexed { index, album ->
            if (bookmarkedIds.contains(album.collectionId)) {
                mutableAlbums[index] = album.copy(isBookmarked = true)
            }
        }
        mutableAlbums
    }

    fun searchAlbums(term: String) {
        viewModelScope.launch {
            val albums = useCase.getAlbums(term)
            fetchedAlbums.emit(albums)
        }
    }

    fun bookmarkAlbum(id: Int) {
        viewModelScope.launch {
            val bookmarkSet = bookmarkedAlbums.value.toMutableSet()
            if (!bookmarkSet.contains(id)) {
                bookmarkSet.add(id)
                useCase.updateBookmarkedAlbums(bookmarkSet)
            }
        }
    }

    fun removeBookmarkAlbum(id: Int) {
        viewModelScope.launch {
            val bookmarkSet = bookmarkedAlbums.value.toMutableSet()
            if (bookmarkSet.contains(id)) {
                bookmarkSet.remove(id)
                useCase.updateBookmarkedAlbums(bookmarkSet)
            }
        }
    }
}