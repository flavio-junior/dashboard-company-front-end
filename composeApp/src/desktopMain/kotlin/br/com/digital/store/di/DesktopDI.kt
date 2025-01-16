package br.com.digital.store.di

import br.com.digital.store.data.repository.local.DesktopLocalStorage
import br.com.digital.store.features.account.data.repository.LocalStorageImp
import br.com.digital.store.features.account.domain.converter.ConverterToken
import br.com.digital.store.ui.viewmodel.ApiViewModel
import org.koin.dsl.module

val desktopModule = module {
    single { DesktopLocalStorage() }
    single<LocalStorageImp> { DesktopLocalStorage() }
    single { ConverterToken() }
    single { ApiViewModel(get(), get(), get()) }
}
