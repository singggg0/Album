package com.project.sws.album.di

import com.project.sws.album.repo.SearchRepo
import com.project.sws.album.repo.SearchRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {
    @Binds
    abstract fun bindSearchAlbumRepo(repo: SearchRepoImpl): SearchRepo
}