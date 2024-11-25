package br.com.digital.store.components.ui

import androidx.compose.runtime.Composable
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler

@Composable
fun <R : ObserveNetworkStateHandler<*>> ObserveNetworkStateHandler(
    resultState: R,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String) -> Unit = {},
    onSuccess: @Composable (R) -> Unit = {}
) {
    when (resultState) {
        is ObserveNetworkStateHandler.Loading<*> -> onLoading()
        is ObserveNetworkStateHandler.Error<*> -> onError(resultState.e)
        is ObserveNetworkStateHandler.Success<*> -> onSuccess(resultState)
    }
}
