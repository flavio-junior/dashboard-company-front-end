package br.com.digital.store.ui

import android.app.Application
import br.com.digital.store.di.androidModule
import br.com.digital.store.features.networking.di.networkModule
import br.com.digital.store.features.product.di.productModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationApp)
            androidLogger(Level.INFO)
            modules(modules = listOf(androidModule, networkModule, productModule))
        }
    }
}
