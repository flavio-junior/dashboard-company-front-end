package br.com.digital.store

import androidx.compose.runtime.Composable
import br.com.digital.store.components.ui.Description
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Description(description = "Hello World!")
}