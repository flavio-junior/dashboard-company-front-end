package br.com.digital.store.networking.utils

sealed class ObserveNetworkStateHandler<T>(
    val status: NetworkStatus,
    val result: T? = null,
    val exception: DescriptionError = DescriptionError(type = ErrorType.CLIENT, message = "")
) {
    data class Loading<T>(val l: Boolean) :
        ObserveNetworkStateHandler<T>(status = NetworkStatus.LOADING)

    data class Error<T>(val type: ErrorType = ErrorType.CLIENT, val e: String) :
        ObserveNetworkStateHandler<T>(
            status = NetworkStatus.ERROR,
            exception = DescriptionError(type = type, message = e)
        )

    data class Success<T>(val s: T?) :
        ObserveNetworkStateHandler<T>(status = NetworkStatus.SUCCESS, result = s)
}
