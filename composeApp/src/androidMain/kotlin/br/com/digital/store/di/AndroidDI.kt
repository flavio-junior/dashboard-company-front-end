package br.com.digital.store.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import br.com.digital.store.features.account.data.repository.local.LocalStorage
import br.com.digital.store.features.account.viewmodel.AccountViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    singleOf(::LocalStorage)
    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(name = "LOCAL_STORAGE")
        }
    }
    single { AccountViewModel(get(), get(), get()) }
}
