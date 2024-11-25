package br.com.digital.store.di

import br.com.digital.store.data.repository.local.DesktopLocalStorage
import br.com.digital.store.features.account.data.LocalStorageImp
import br.com.digital.store.features.account.domain.converter.ConverterAccount
import br.com.digital.store.ui.viewmodel.ApiViewModel
import org.koin.dsl.module

val desktopModule = module {
    single { DesktopLocalStorage() }
    single<LocalStorageImp> { DesktopLocalStorage() }
    single { ConverterAccount() }
    single { ApiViewModel(get(), get(), get()) }
}
