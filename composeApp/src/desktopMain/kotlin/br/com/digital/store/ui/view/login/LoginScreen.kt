package br.com.digital.store.ui.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.theme.Themes

@Composable
fun SignInScreen(
    goToDashboardScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CardSignIn(
            goToDashboardScreen = goToDashboardScreen,
            goToAlternativeRoutes = goToAlternativeRoutes
        )
    }
}