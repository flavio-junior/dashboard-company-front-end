package br.com.digital.store.ui.view.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Description
import br.com.digital.store.theme.Themes

@Composable
fun EditCategory(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.error)
    ) {
        Description(description = "Edit Category")
    }
}
