package br.com.digital.store.features.networking.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <reified T> toResultFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline call: suspend () -> HttpResponse
): Flow<ObserveNetworkStateHandler<T>> = flow {
    emit(ObserveNetworkStateHandler.Loading(true))
    try {
        val response = call()
        when (response.status.value) {
            in 200..299 -> {
                val data = response.body<T>()
                emit(ObserveNetworkStateHandler.Success(data))
            }

            in 400..499 -> {
                response.body<ResponseError>()
                emit(
                    ObserveNetworkStateHandler.Error(
                        code = response.status.value,
                        e = response.status.description
                    )
                )
            }

            in 500..599 -> {
                emit(
                    ObserveNetworkStateHandler.Error(
                        code = response.status.value,
                        type = ErrorType.SERVER,
                        e = response.status.description
                    )
                )
            }

            else -> {
                emit(
                    ObserveNetworkStateHandler.Error(
                        type = ErrorType.INTERNAL,
                        e = "Unknown error"
                    )
                )
            }
        }
    } catch (e: Exception) {
        emit(ObserveNetworkStateHandler.Error(type = ErrorType.INTERNAL, e = e.message.orEmpty()))
    }
}.flowOn(dispatcher)
