package br.com.digital.store.ui.view.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.common.category.dto.EditCategoryRequestDTO
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.mail
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.ACTUAL_NAME
import br.com.digital.store.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.category.CategoryUtils.CATEGORY_NAME
import br.com.digital.store.ui.view.category.CategoryUtils.EDIT_CATEGORY
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun EditCategory(
    modifier: Modifier = Modifier,
    categoryVO: CategoryResponseVO,
    onSuccessful: (CategoryResponseVO) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        val viewModel: CategoryViewModel = getKoin().get()
        var observer: Triple<Boolean, Boolean, String> by remember {
            mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
        }
        var categoryName by remember { mutableStateOf(value = EMPTY_TEXT) }
        val editCategory = { category: String ->
            if (checkNameIsNull(category)) {
                observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.editCategory(
                    category = EditCategoryRequestDTO(id = categoryVO.id, name = category)
                )
            }
        }
        TextField(
            enabled = false,
            label = ACTUAL_NAME,
            value = categoryVO.name,
            icon = Res.drawable.mail,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            onValueChange = {},
        )
        TextField(
            label = CATEGORY_NAME,
            value = categoryName,
            icon = Res.drawable.mail,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                categoryName = it
            }
        )
        LoadingButton(
            label = EDIT_CATEGORY,
            onClick = {
                editCategory(categoryName)
            },
            isEnabled = observer.first
        )
        ObserveNetworkStateHandlerEditCategory(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            onSuccessful = {
                onSuccessful(CategoryResponseVO(id = 0, name = EMPTY_TEXT))
                categoryName = EMPTY_TEXT
            }
        )
        DeleteCategory(
            viewModel = viewModel,
            id = categoryVO.id,
            modifier = Modifier,
            onError = {
                observer = it
            },
            onSuccessful = {
                onSuccessful(CategoryResponseVO(id = 0, name = EMPTY_TEXT))
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerEditCategory(
    viewModel: CategoryViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.editCategory }
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
