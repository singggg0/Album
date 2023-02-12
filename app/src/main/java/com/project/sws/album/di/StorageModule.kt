package com.project.sws.album.di

import com.project.sws.album.repo.StorageRepo
import com.project.sws.album.repo.StorageRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindStorageRepo(repo: StorageRepoImpl): StorageRepo
}