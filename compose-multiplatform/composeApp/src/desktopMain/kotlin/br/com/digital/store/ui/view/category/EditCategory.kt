package br.com.digital.store.ui.view.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import br.com.digital.store.common.category.dto.EditCategoryRequestDTO
import br.com.digital.store.common.category.vo.CategoryResponseVO
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.close
import br.com.digital.store.features.category.viewmodel.CategoryViewModel
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.strings.StringsUtils.ACTUAL_NAME
import br.com.digital.store.strings.StringsUtils.ID
import br.com.digital.store.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.category.CategoryUtils.EDIT_CATEGORY
import br.com.digital.store.ui.view.category.CategoryUtils.NEW_NAME_CATEGORY
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_3
import br.com.digital.store.utils.onBorder
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun EditCategory(
    modifier: Modifier = Modifier,
    categoryVO: CategoryResponseVO,
    onCleanCategory: () -> Unit = {},
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
        var openDialog by remember { mutableStateOf(value = false) }
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = categoryVO.id.toString(),
                modifier = Modifier.weight(weight = WEIGHT_SIZE),
            )
            TextField(
                enabled = false,
                label = ACTUAL_NAME,
                value = categoryVO.name,
                modifier = Modifier.weight(weight = WEIGHT_SIZE_3),
            )
            IconDefault(
                icon = Res.drawable.close, modifier = Modifier
                    .onBorder(
                        onClick = {},
                        color = Themes.colors.primary,
                        spaceSize = Themes.size.spaceSize16,
                        width = Themes.size.spaceSize1
                    )
                    .size(size = Themes.size.spaceSize64)
                    .padding(all = Themes.size.spaceSize8),
                onClick = onCleanCategory
            )
        }
        TextField(
            label = NEW_NAME_CATEGORY,
            value = categoryName,
            keyboardType = KeyboardType.Text,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                categoryName = it
            },
            onGo = {
                openDialog = true
            }
        )
        LoadingButton(
            label = EDIT_CATEGORY,
            onClick = {
                openDialog = true
            },
            isEnabled = observer.first
        )
        if (openDialog) {
            Alert(
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    editCategory(categoryName)
                    openDialog = false
                }
            )
        }
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
