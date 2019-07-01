package com.example.end

import androidx.test.platform.app.InstrumentationRegistry
import com.example.end.di.app.AppComponent
import com.example.end.di.app.AppModule
import it.cosenonjaviste.daggermock.DaggerMock

fun espressoDaggerMockRule() = DaggerMock.rule<AppComponent>() {
    set { component -> component.inject(app) }
    customizeBuilder<AppComponent.Builder> { it.application(app) }
}

val app: App get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App