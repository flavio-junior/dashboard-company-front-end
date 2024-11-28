package br.com.digital.store.features.category.di

import br.com.digital.store.features.category.data.repository.CategoryRemoteDataSource
import br.com.digital.store.features.category.data.repository.CategoryRepository
import br.com.digital.store.features.category.domain.ConverterCategory
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val categoryModule = module {
    single { ConverterCategory() }
    single<CategoryRepository> {
        CategoryRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::CategoryViewModel)
}
