package com.example.end.di.app

import com.example.end.data.api.EndApi
import com.example.end.data.api.EndClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideEndClient(): EndApi =
        EndClient().endApi

}