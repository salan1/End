package com.example.end.di

import com.example.end.data.repositories.EndRepository
import com.example.end.di.scope.FragmentScope
import com.example.end.domain.interactors.GetProductsUseCase
import dagger.Module
import dagger.Provides


@Module
class MainModule {

    // Use cases
    @FragmentScope
    @Provides
    internal fun provideGetProductsUseCase(
        endRepository: EndRepository
    ): GetProductsUseCase = GetProductsUseCase(endRepository)

}