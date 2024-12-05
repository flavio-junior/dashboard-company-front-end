package br.com.digital.store.features.food.di

import br.com.digital.store.features.food.data.repository.FoodRemoteDataSource
import br.com.digital.store.features.food.data.repository.FoodRepository
import br.com.digital.store.features.food.domain.ConverterFood
import br.com.digital.store.features.food.ui.viewmodel.FoodViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val foodModule = module {
    single { ConverterFood(get()) }
    single<FoodRepository> {
        FoodRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::FoodViewModel)
}
