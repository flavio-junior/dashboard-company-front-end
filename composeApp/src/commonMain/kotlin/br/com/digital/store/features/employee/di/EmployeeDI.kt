package br.com.digital.store.features.employee.di

import br.com.digital.store.features.employee.data.repository.EmployeeRemoteDataSource
import br.com.digital.store.features.employee.data.repository.EmployeeRepository
import br.com.digital.store.features.employee.ui.viewModel.EmployeeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val employeeModule = module {
    single<EmployeeRepository> {
        EmployeeRemoteDataSource(httpClient = get(), get())
    }
    singleOf(::EmployeeViewModel)
}
