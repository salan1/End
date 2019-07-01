package com.example.end

import android.app.Application
import android.content.Context
import com.github.tmurakami.dexopener.DexOpener
import androidx.test.runner.AndroidJUnitRunner


class EndAndroidJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader,
        className: String,
        context: Context
    ): Application {
        DexOpener.install(this)
        return super.newApplication(cl, className, context)
    }
}