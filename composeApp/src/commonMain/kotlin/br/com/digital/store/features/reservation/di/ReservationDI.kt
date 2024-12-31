package br.com.digital.store.features.reservation.di

import br.com.digital.store.features.reservation.data.repository.ReservationRemoteDataSource
import br.com.digital.store.features.reservation.data.repository.ReservationRepository
import br.com.digital.store.features.reservation.domain.converter.ConverterReservation
import br.com.digital.store.features.reservation.ui.viewmodel.ReservationViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val reservationModule = module {
    single { ConverterReservation() }
    single<ReservationRepository> {
        ReservationRemoteDataSource(get(), get())
    }
    singleOf(::ReservationViewModel)
}
