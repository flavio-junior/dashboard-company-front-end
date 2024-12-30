package br.com.digital.store.features.networking.resources

import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

sealed class ObserveNetworkStateHandler<T>(
    val status: NetworkStatus,
    val result: T? = null,
    val exception: DescriptionError = DescriptionError(
        code = 0,
        type = ErrorType.CLIENT,
        message = EMPTY_TEXT
    )
) {
    data class Loading<T>(val l: Boolean) :
        ObserveNetworkStateHandler<T>(status = NetworkStatus.LOADING)

    data class Error<T>(
        val code: Int? = null,
        val type: ErrorType = ErrorType.CLIENT,
        val e: String? = null
    ) :
        ObserveNetworkStateHandler<T>(
            status = NetworkStatus.ERROR,
            exception = DescriptionError(code = code, type = type, message = e)
        )

    data class Success<T>(val s: T?) :
        ObserveNetworkStateHandler<T>(status = NetworkStatus.SUCCESS, result = s)
}
