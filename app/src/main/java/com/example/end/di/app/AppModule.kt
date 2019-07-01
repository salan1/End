package com.example.end.di.app

import android.app.Application
import android.content.Context

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatasourceModule::class, RepositoryModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.baseContext

}
