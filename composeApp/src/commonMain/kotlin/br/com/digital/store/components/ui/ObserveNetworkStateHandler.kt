package br.com.digital.store.components.ui

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ErrorType
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.StatusCode.NUMBER_403
import br.com.digital.store.features.networking.resources.selectAlternativeRoute
import br.com.digital.store.utils.CommonUtils.UNAUTHORIZED_MESSAGE
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
            when (state.exception.type) {
                ErrorType.CLIENT -> {
                    if (state.exception.code == NUMBER_403 && state.exception.message == UNAUTHORIZED_MESSAGE) {
                        goToAlternativeRoutes(selectAlternativeRoute(code = state.exception.code))
                    } else {
                        onError(state.exception.message)
                    }
                }

                ErrorType.INTERNAL, ErrorType.EXTERNAL, ErrorType.SERVER -> {
                    goToAlternativeRoutes(
                        selectAlternativeRoute(
                            code = state.exception.code ?: NUMBER_ZERO
                        )
                    )
                }
            }
        }

        is ObserveNetworkStateHandler.Success<*> -> onSuccess(state)
    }
}
