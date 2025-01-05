package br.com.digital.store.features.report.di

import br.com.digital.store.features.report.data.repository.ReportRemoteDataSource
import br.com.digital.store.features.report.data.repository.ReportRepository
import br.com.digital.store.features.report.domain.converter.ConverterReport
import br.com.digital.store.features.report.ui.viewmodel.ReportViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val reportModule = module {
    single { ConverterReport() }
    single<ReportRepository> {
        ReportRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::ReportViewModel)
}
