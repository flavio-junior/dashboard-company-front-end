package br.com.digital.store.components.ui

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ErrorType
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.selectAlternativeRoute
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO

@Composable
fun <R : ObserveNetworkStateHandler<*>> ObserveNetworkStateHandler(
    state: R,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String?) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (R) -> Unit = {}
) {
    when (state) {
        is ObserveNetworkStateHandler.Loading<*> -> onLoading()
        is ObserveNetworkStateHandler.Error<*> -> {
            when (state.type) {
                ErrorType.CLIENT -> onError(state.e)
                ErrorType.INTERNAL, ErrorType.EXTERNAL, ErrorType.SERVER -> {
                    goToAlternativeRoutes(selectAlternativeRoute(code = state.code ?: NUMBER_ZERO))
                }
            }
        }
        is ObserveNetworkStateHandler.Success<*> -> onSuccess(state)
    }
}
