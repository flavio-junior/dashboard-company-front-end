package br.com.digital.store.features.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.digital.store.components.strings.isNotBlankAndEmpty
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.account.data.vo.TokenResponseVO
import br.com.digital.store.features.account.viewmodel.AccountViewModel
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.utils.CommonUtils.DELAY
import br.com.digital.store.utils.initializeWithDelay
import br.com.digital.store.utils.isTokenExpired
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    goToSignInScreen: () -> Unit = {},
    goToDashboardScreen: () -> Unit = {}
) {
    val viewModel: AccountViewModel = koinViewModel()
    LaunchedEffect(key1 = Unit) {
        viewModel.getToken()
        initializeWithDelay(time = DELAY, action = { viewModel.getToken() })
    }
    ObserveNetworkStateHandlerToken(
        viewModel = viewModel,
        goToSignInScreen = goToSignInScreen,
        goToDashboardScreen = goToDashboardScreen
    )
}

@Composable
private fun ObserveNetworkStateHandlerToken(
    viewModel: AccountViewModel,
    goToSignInScreen: () -> Unit = {},
    goToDashboardScreen: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.getToken()
    }
    val state: ObserveNetworkStateHandler<TokenResponseVO> by remember { viewModel.getTokenSaved }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {},
        onSuccess = {
            if (state.result != null && state.result?.type?.isNotBlankAndEmpty() == true) {
                val checkContent = state.result
                if (checkContent?.type?.isNotBlankAndEmpty() == true) {
                    if (isTokenExpired(expirationDate = checkContent.expiration)) {
                        viewModel.cleanToken()
                        goToSignInScreen()
                    } else {
                        goToDashboardScreen()
                    }
                }
            }
        }
    )
}
