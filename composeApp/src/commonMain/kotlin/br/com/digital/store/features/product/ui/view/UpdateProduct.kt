package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CONFIRM_UPDATE
import br.com.digital.store.components.strings.StringsUtils.MESSAGE_ZERO_DOUBLE
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.strings.StringsUtils.QUANTITY
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Price
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.category.data.dto.CategoryResponseDTO
import br.com.digital.store.features.category.ui.view.SelectCategories
import br.com.digital.store.features.category.utils.CategoryUtils.ADD_CATEGORIES
import br.com.digital.store.features.category.utils.CategoryUtils.NO_CATEGORIES_SELECTED
import br.com.digital.store.features.item.utils.checkPriceIsEqualsZero
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.product.data.dto.UpdateProductRequestDTO
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import br.com.digital.store.features.product.utils.ProductUtils.NEW_NAME_PRODUCT
import br.com.digital.store.features.product.utils.checkBodyProductIsNull
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdateProduct(
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit
) {
    val viewModel: ProductViewModel = getKoin().get()
    var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    val selectedCategories = remember { mutableStateListOf<CategoryResponseDTO>() }
    var price: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
    var quantity: Int by remember { mutableIntStateOf(value = NUMBER_ZERO) }
    var cleanText by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var openDialog by remember { mutableStateOf(value = false) }
    var confirmUpdateProduct by remember { mutableStateOf(value = false) }
    val checkUpdateProduct = {
        if (checkBodyProductIsNull(
                name = name,
                price = price.toDouble(),
                quantity = quantity
            )
        ) {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        } else if (checkPriceIsEqualsZero(price = price.toDouble())) {
            observer = Triple(first = false, second = true, third = MESSAGE_ZERO_DOUBLE)
        } else if (selectedCategories.isEmpty()) {
            observer = Triple(first = false, second = true, third = NO_CATEGORIES_SELECTED)
        } else {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updateProduct(
                product = UpdateProductRequestDTO(
                    id = id,
                    name = name,
                    categories = selectedCategories,
                    price = price.toDouble(),
                    quantity = quantity
                )
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        TextField(
            label = NEW_NAME_PRODUCT,
            value = name,
            isError = observer.second,
            message = observer.third,
            onValueChange = {
                name = it
            },
            modifier = Modifier.weight(weight = CommonUtils.WEIGHT_SIZE_2)
        )
        Price(
            label = PRICE,
            value = price,
            isError = observer.second,
            cleanText = cleanText,
            onCleanText = {
                cleanText = it
            },
            onValueChange = {
                price = it
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            label = QUANTITY,
            value = quantity.toString(),
            isError = observer.second,
            onValueChange = {
                quantity = it.toIntOrNull() ?: NUMBER_ZERO
            },
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        LoadingButton(
            onClick = {
                openDialog = true
            },
            label = ADD_CATEGORIES,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        LoadingButton(
            onClick = {
                confirmUpdateProduct = true
            },
            label = CONFIRM_UPDATE,
            isEnabled = observer.first,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        DeleteProduct(
            id = id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            onRefresh = onRefresh
        )
    }
    ObserveNetworkStateHandlerUpdateProduct(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            name = EMPTY_TEXT
            price = ZERO_DOUBLE
            quantity = NUMBER_ZERO
            cleanText = true
            onRefresh()
        }
    )
    if (openDialog) {
        SelectCategories(
            onDismissRequest = {
                openDialog = false
            },
            onConfirmation = {
                selectedCategories.addAll(it)
                openDialog = false
            }
        )
    }
    if (confirmUpdateProduct) {
        Alert(
            label = "$CONFIRM_UPDATE?",
            onDismissRequest = {
                confirmUpdateProduct = false
            },
            onConfirmation = {
                checkUpdateProduct()
                confirmUpdateProduct = false
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerUpdateProduct(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateProduct }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            onError(Triple(first = false, second = true, third = it.orEmpty()))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
            reloadViewModels()
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccessful()
        }
    )
}
