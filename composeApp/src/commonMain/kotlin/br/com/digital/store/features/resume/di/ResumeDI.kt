package br.com.digital.store.features.resume.di

import br.com.digital.store.features.resume.data.repository.ResumeRemoteDataSource
import br.com.digital.store.features.resume.data.repository.ResumeRepository
import br.com.digital.store.features.resume.domain.converter.ConverterResume
import br.com.digital.store.features.resume.ui.viewmodel.ResumeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val resumeModule = module {
    single { ConverterResume() }
    single<ResumeRepository> {
        ResumeRemoteDataSource(get(), get())
    }
    singleOf(::ResumeViewModel)
}
