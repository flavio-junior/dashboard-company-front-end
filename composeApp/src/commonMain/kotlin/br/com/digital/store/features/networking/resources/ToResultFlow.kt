package br.com.digital.store.features.networking.resources

import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_200
import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_299
import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_400
import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_499
import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_500
import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_599
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <reified T> toResultFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline call: suspend () -> HttpResponse
): Flow<ObserveNetworkStateHandler<T>> = flow {
    emit(ObserveNetworkStateHandler.Loading(l = true))
    try {
        val response = call()
        when (response.status.value) {
            in NUMBER_200..NUMBER_299 -> {
                val data = response.body<T>()
                emit(ObserveNetworkStateHandler.Success(s = data))
            }
            in NUMBER_400..NUMBER_499 -> {
                val errorResponse = response.body<ResponseError>()
                emit(
                    ObserveNetworkStateHandler.Error(
                        code = errorResponse.status,
                        e = errorResponse.message
                    )
                )
            }
            in NUMBER_500..NUMBER_599 -> {
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
}.catch {
    emit(ObserveNetworkStateHandler.Error(type = ErrorType.INTERNAL, e = it.message.orEmpty()))
}.flowOn(dispatcher)
