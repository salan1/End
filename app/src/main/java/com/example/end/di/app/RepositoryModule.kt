package com.example.end.di.app

import com.example.end.data.repositories.EndRepository
import com.example.end.data.repositories.datasources.EndDatasource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    // Repos
    @Provides
    @Singleton
    internal fun provideEndRepository(
        datasource: EndDatasource
    ) = EndRepository(datasource)

}