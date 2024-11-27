package br.com.digital.store.features.reservation.di

import br.com.digital.store.features.reservation.data.ReservationRemoteDataSource
import br.com.digital.store.features.reservation.data.ReservationRepository
import br.com.digital.store.features.reservation.domain.ConverterReservation
import br.com.digital.store.features.reservation.viewmodel.ReservationViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val reservationModule = module {
    single { ConverterReservation() }
    single<ReservationRepository> {
        ReservationRemoteDataSource(get(), get())
    }
    singleOf(::ReservationViewModel)
}
