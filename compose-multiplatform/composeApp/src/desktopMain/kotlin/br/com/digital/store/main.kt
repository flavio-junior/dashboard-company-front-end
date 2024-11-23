package br.com.digital.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.digital.store.networking.di.networkModule
import br.com.digital.store.strings.StringsUtils.MAIN_NAME
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.CardSignIn
import lojavirtual.composeapp.generated.resources.Res
import lojavirtual.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(modules = networkModule)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = MAIN_NAME,
            icon = painterResource(resource = Res.drawable.logo),
            state = rememberWindowState(placement = WindowPlacement.Maximized)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .fillMaxSize()
            ) {
                CardSignIn()
            }
        }
    }
}
