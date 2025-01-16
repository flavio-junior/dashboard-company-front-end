package br.com.digital.store.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import br.com.digital.store.features.account.data.repository.AccountRemoteDataSource
import br.com.digital.store.features.account.data.repository.local.LocalStorage
import br.com.digital.store.features.account.domain.converter.ConverterToken
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
    single<AccountRemoteDataSource> { AccountRemoteDataSource(get()) }
    single { ConverterToken() }
    single { AccountViewModel(get(), get(), get()) }
}
