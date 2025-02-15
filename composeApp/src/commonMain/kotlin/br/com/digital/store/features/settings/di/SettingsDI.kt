package br.com.digital.store.features.settings.di

import br.com.digital.store.features.settings.data.repository.SettingsRemoteDataSource
import br.com.digital.store.features.settings.data.repository.SettingsRepository
import br.com.digital.store.features.settings.ui.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsModule = module {
    single<SettingsRepository> {
        SettingsRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::SettingsViewModel)
}
