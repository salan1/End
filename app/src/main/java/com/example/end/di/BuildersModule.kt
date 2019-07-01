package com.example.end.di

import com.example.end.di.scope.ActivityScope
import com.example.end.di.scope.FragmentScope
import com.example.end.presentation.ui.MainActivity
import com.example.end.presentation.ui.products.ProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Binds all sub-components within the app.
 */
@Module
internal abstract class BuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @FragmentScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeProductsFragment(): ProductsFragment
}