package br.com.digital.store

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.digital.store.di.networkModule
import br.com.digital.store.strings.StringsUtils.MAIN_NAME
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
            App()
        }
    }
}
