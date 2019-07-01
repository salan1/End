package com.example.end.di.app

import android.app.Application
import com.example.end.App
import com.example.end.di.BuildersModule
import com.example.end.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class,
        BuildersModule::class, ViewModelModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)
    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appModule(ss: AppModule): Builder

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
