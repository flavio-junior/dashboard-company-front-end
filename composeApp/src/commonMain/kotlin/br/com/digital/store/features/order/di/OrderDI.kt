package br.com.digital.store.features.order.di

import br.com.digital.store.features.order.data.repository.OrderRemoteDataSource
import br.com.digital.store.features.order.data.repository.OrderRepository
import br.com.digital.store.features.order.domain.converter.ConverterOrder
import br.com.digital.store.features.order.ui.viewmodel.OpenOrdersViewModel
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val orderModule = module {
    single { ConverterOrder() }
    single<OrderRepository> {
        OrderRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::OpenOrdersViewModel)
    singleOf(::OrderViewModel)
}
