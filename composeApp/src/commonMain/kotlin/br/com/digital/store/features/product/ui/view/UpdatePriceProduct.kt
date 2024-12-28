package br.com.digital.store.features.product.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.CONFIRM_UPDATE
import br.com.digital.store.components.strings.StringsUtils.MESSAGE_ZERO_DOUBLE
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.ui.Alert
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Price
import br.com.digital.store.features.item.utils.checkPriceIsEqualsZero
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.features.networking.utils.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.utils.reloadViewModels
import br.com.digital.store.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.digital.store.features.product.ui.viewmodel.ProductViewModel
import br.com.digital.store.features.product.utils.ProductUtils.UPDATE_PRICE_PRODUCT
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.ZERO_DOUBLE
import br.com.digital.store.utils.checkPriceIsNull
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun UpdatePriceProduct(
    modifier: Modifier = Modifier,
    id: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: ProductViewModel = getKoin().get()
    var price: String by remember { mutableStateOf(value = ZERO_DOUBLE) }
    var cleanText by remember { mutableStateOf(value = false) }
    var openDialog by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val checkUpdatePriceProduct = {
        if (checkPriceIsNull(price = price.toDouble())
        ) {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        } else if (checkPriceIsEqualsZero(price = price.toDouble())) {
            observer = Triple(first = false, second = true, third = MESSAGE_ZERO_DOUBLE)
        } else {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.updatePriceProduct(
                id = id,
                price = UpdatePriceProductRequestDTO(
                    price = price.toDouble()
                )
            )
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = "$UPDATE_PRICE_PRODUCT:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = modifier,
            verticalAlignment = Alignment.Top
        ) {
            Price(
                label = PRICE,
                value = price,
                isError = observer.second,
                message = observer.third,
                cleanText = cleanText,
                onCleanText = {
                    cleanText = it
                },
                onValueChange = {
                    price = it
                },
                modifier = modifier.weight(weight = CommonUtils.WEIGHT_SIZE),
                onGo = {
                    openDialog = true
                }
            )
            LoadingButton(
                onClick = {
                    openDialog = true
                },
                label = CONFIRM_UPDATE,
                isEnabled = observer.first,
                modifier = modifier.weight(weight = CommonUtils.WEIGHT_SIZE)
            )
        }
        if (openDialog) {
            Alert(
                label = "$UPDATE_PRICE_PRODUCT?",
                onDismissRequest = {
                    openDialog = false
                },
                onConfirmation = {
                    observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                    checkUpdatePriceProduct()
                    openDialog = false
                }
            )
        }
        ObserveNetworkStateHandlerUpdatePriceProduct(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                price = ZERO_DOUBLE
                onRefresh()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerUpdatePriceProduct(
    viewModel: ProductViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updatePriceProduct }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {
            Triple(first = true, second = false, third = it)
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
