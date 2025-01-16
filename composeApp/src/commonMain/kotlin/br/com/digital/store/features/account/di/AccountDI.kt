package br.com.digital.store.features.account.di

import br.com.digital.store.features.account.data.repository.AccountRemoteDataSource
import br.com.digital.store.features.account.data.repository.AccountRepository
import br.com.digital.store.features.account.domain.converter.ConverterToken
import org.koin.dsl.module

val accountModule = module {
    single<AccountRepository> { AccountRemoteDataSource(get()) }
    single { ConverterToken() }
}
