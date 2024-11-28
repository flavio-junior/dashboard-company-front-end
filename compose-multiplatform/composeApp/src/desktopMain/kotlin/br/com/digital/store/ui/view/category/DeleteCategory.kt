package br.com.digital.store.ui.view.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.category.CategoryUtils.DELETE_CATEGORY
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT

@Composable
fun DeleteCategory(
    viewModel: CategoryViewModel,
    id: Long,
    modifier: Modifier = Modifier,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var openDialog by remember { mutableStateOf(false) }
        LoadingButton(
            label = DELETE_CATEGORY,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        ObserveNetworkStateHandlerEditCategory(
            viewModel = viewModel,
            onError = {
                onError(it)
                observer = it
            },
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                onSuccessful()
            }
        )
        if (openDialog) {
            Alert(
                label = DELETE_CATEGORY,
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    viewModel.deleteCategory(id = id)
                    openDialog = false
                }
            )
        }
    }
}

@Composable
private fun ObserveNetworkStateHandlerEditCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.deleteCategory }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        onSuccess = {
            onSuccessful()
            viewModel.findAllCategories()
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
}
