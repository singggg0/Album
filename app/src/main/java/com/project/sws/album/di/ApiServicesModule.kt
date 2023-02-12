package com.project.sws.album.di

import com.project.sws.album.network.ApiServices
import com.project.sws.album.network.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ApiServicesModule {
    @Provides
    fun provideApiServices(): ApiServices {
        return RetrofitManager.apiServices
    }
}