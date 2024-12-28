package br.com.digital.store.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import br.com.digital.store.data.AndroidLocalStorage


val androidModule = module {
    singleOf(::AndroidLocalStorage)
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(name = "LOCAL_STORAGE")
        }
    }
}
