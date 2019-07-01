package com.example.end

import com.example.end.di.app.AppModule
import com.example.end.di.app.DaggerAppComponent
import dagger.android.DaggerApplication
import timber.log.Timber

class App: DaggerApplication() {

    var appComponent = DaggerAppComponent.builder().appModule(AppModule()).application(this).build()


    override fun applicationInjector() = appComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

}