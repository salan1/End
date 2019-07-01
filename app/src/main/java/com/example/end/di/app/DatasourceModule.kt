package com.example.end.di.app

import com.example.end.data.api.EndApi
import com.example.end.data.repositories.datasources.EndDatasource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatasourceModule {

    // Datasources
    @Provides
    @Singleton
    internal fun provideEndDatasource(
        client: EndApi
    ): EndDatasource = EndDatasource(client)

}