package br.com.digital.store.features.product.di

import br.com.digital.store.features.product.data.repository.ProductRemoteDataSource
import br.com.digital.store.features.product.data.repository.ProductRepository
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val  productModule = module {
    single { ConverterProduct(get()) }
    single<ProductRepository> {
        ProductRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::ProductViewModel)
}
