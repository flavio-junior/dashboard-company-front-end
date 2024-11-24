package br.com.digital.store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Description
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.NumbersUtils

@Composable
fun ProductScreen(
    goToBackScreen: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.error)
            .fillMaxSize()
    ) {
        var count = 0
        Button(
            onClick = {
                count++
                if (count == NumbersUtils.NUMBER_ONE) {
                    goToBackScreen()
                }
            },
            content = {
                Description(description = "Logout")
            }
        )

    }
}

