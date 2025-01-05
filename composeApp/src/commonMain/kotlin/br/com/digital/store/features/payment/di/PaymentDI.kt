package br.com.digital.store.features.payment.di

import br.com.digital.store.features.payment.data.repository.PaymentRemoteDataSource
import br.com.digital.store.features.payment.data.repository.PaymentRepository
import br.com.digital.store.features.payment.domain.converter.ConverterPayment
import br.com.digital.store.features.payment.ui.viewmodel.PaymentViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val paymentModule = module {
    single { ConverterPayment() }
    single<PaymentRepository> {
        PaymentRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::PaymentViewModel)
}
