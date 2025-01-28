package br.com.digital.store.features.fee.di

import br.com.digital.store.features.fee.data.repository.FeeRemoteDataSource
import br.com.digital.store.features.fee.data.repository.FeeRepository
import br.com.digital.store.features.fee.domain.converter.ConverterFee
import br.com.digital.store.features.fee.ui.viewmodel.FeeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val feeModule = module {
    single { ConverterFee() }
    single<FeeRepository> {
        FeeRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::FeeViewModel)
}
