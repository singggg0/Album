package com.project.sws.album.di

import com.project.sws.album.usecase.AlbumUseCase
import com.project.sws.album.usecase.AlbumUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AlbumModule {
    @Binds
    abstract fun bindAlbumUseCase(useCase: AlbumUseCaseImpl): AlbumUseCase
}