package br.com.digital.store.di

import br.com.digital.store.data.repository.loal.DesktopLocalStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import br.com.digital.store.ui.viewmodel.ApiViewModel

val desktopModule = module {
    single {
        DesktopLocalStorage()
    }
    singleOf(::ApiViewModel)
}