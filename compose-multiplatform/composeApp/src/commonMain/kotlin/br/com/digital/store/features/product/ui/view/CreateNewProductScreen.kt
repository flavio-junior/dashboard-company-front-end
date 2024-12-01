package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Description
import br.com.digital.store.theme.Themes

@Composable
fun CreateNewProductScreen() {
    Column(
        modifier = Modifier.background(color = Themes.colors.secondary)
            .fillMaxSize()
    ) {
        Description(description = "Update Product")
    }
}
