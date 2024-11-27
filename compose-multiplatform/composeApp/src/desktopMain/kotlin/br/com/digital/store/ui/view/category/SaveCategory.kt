package br.com.digital.store.ui.view.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.category.CategoryUtils.CATEGORY_NAME
import br.com.digital.store.ui.view.category.CategoryUtils.SAVE_CATEGORY
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SaveCategory(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val viewModel: CategoryViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var categoryName by remember { mutableStateOf(value = EMPTY_TEXT) }
        val saveCategory = { category: String ->
            if (checkNameIsNull(category)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.createCategory(category = category)
            }
        }
        TextField(
            label = CATEGORY_NAME,
            value = categoryName,
            icon = Res.drawable.edit,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = { categoryName = it },
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            onGo = {
                saveCategory(categoryName)
            }
        )
        LoadingButton(
            label = SAVE_CATEGORY,
            onClick = {
                saveCategory(categoryName)
            },
            isEnabled = observer.first,
            modifier = modifier.weight(weight = WEIGHT_SIZE)
        )
        ObserveNetworkStateHandlerCreateNewCategory(
            viewModel = viewModel,
            onError = {
                observer = it
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewCategory }
    ObserveNetworkStateHandler(
        resultState = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllCategories()
        }
    )
}
