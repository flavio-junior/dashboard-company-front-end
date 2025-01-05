package br.com.digital.store.features.report.di

import br.com.digital.store.features.report.ui.viewmodel.ReportViewModel
import br.com.digital.store.features.report.data.repository.ReportDataSource
import br.com.digital.store.features.report.data.repository.ReportRepository
import br.com.digital.store.features.report.domain.ConverterReport
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val reportModule = module {
    single { ConverterReport() }
    single<ReportRepository> {
        ReportDataSource(httpClient = get(), get())
    }
    singleOf(::ReportViewModel)
}
