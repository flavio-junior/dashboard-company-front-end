package br.com.digital.store.features.item.di

import br.com.digital.store.features.item.data.repository.ItemRemoteDataSource
import br.com.digital.store.features.item.data.repository.ItemRepository
import br.com.digital.store.features.item.domain.ConverterItem
import br.com.digital.store.features.item.viewmodel.ItemViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val itemModule = module {
    single { ConverterItem() }
    single<ItemRepository> {
        ItemRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::ItemViewModel)
}
