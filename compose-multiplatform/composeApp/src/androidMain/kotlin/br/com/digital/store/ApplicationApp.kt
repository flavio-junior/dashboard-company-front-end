package br.com.digital.store

import android.app.Application
import br.com.digital.store.di.networkModule
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
            modules(
                modules = networkModule
            )
        }
    }
}
