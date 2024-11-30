package br.com.digital.store.ui.view.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.category.CategoryUtils.CATEGORY_NAME
import br.com.digital.store.ui.view.category.CategoryUtils.SAVE_CATEGORY
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.checkNameIsNull
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SaveCategory(
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = modifier.padding(top = Themes.size.spaceSize8),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val viewModel: CategoryViewModel = getKoin().get()
            var categoryName by remember { mutableStateOf(value = EMPTY_TEXT) }
            val saveCategory = { category: String ->
                if (checkNameIsNull(name = category)) {
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
                onValueChange = { categoryName = it },
                modifier = modifier.weight(weight = WEIGHT_SIZE_2),
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
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    categoryName = EMPTY_TEXT
                }
            )
        }
        IsErrorMessage(isError = observer.second, message = observer.third)
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewCategory }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            it?.let {
                onError(Triple(first = false, second = true, third = it))
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.findAllCategories()
            onSuccessful()
        }
    )
}
