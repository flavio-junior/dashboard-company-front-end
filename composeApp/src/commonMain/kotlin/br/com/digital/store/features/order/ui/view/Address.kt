package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADDRESS
import br.com.digital.store.components.strings.StringsUtils.COMPLEMENT
import br.com.digital.store.components.strings.StringsUtils.DISTRICT
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.strings.StringsUtils.NUMBER
import br.com.digital.store.components.strings.StringsUtils.STATUS
import br.com.digital.store.components.strings.StringsUtils.STREET
import br.com.digital.store.components.ui.Button
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.edit
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.AddressRequestDTO
import br.com.digital.store.features.order.data.vo.AddressResponseVO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.factory.addressFactory
import br.com.digital.store.features.order.ui.viewmodel.OpenOrdersViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOpenOrders
import br.com.digital.store.features.order.utils.OrderUtils.ACTUAL_ADDRESS
import br.com.digital.store.features.order.utils.OrderUtils.ALTER_ADDRESS
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun ShowAddress(
    label: String = ADDRESS,
    addressResponseVO: AddressResponseVO? = null,
    showAction: Boolean = false,
    onItemSelected: () -> Unit = {}
) {
    Description(description = label)
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        TextField(
            enabled = false,
            label = STATUS,
            value = addressFactory(status = addressResponseVO?.status),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = STREET,
            value = addressResponseVO?.street ?: EMPTY_TEXT,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = NUMBER,
            value = addressResponseVO?.number.toString(),
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = DISTRICT,
            value = addressResponseVO?.district ?: EMPTY_TEXT,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        TextField(
            enabled = false,
            label = COMPLEMENT,
            value = addressResponseVO?.complement ?: EMPTY_TEXT,
            onValueChange = {},
            modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
        )
        if (showAction) {
            Button(icon = Res.drawable.edit, onClick = onItemSelected)
        }
    }
}

@Composable
fun AlterAddress(
    orderResponseVO: OrderResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: OpenOrdersViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    if (orderResponseVO.id > NUMBER_ZERO) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
        ) {
            ShowAddress(label = ACTUAL_ADDRESS, addressResponseVO = orderResponseVO.address)
            GetAddressOrder(
                observer = observer.second,
                addressResult = { addressResult ->
                    LoadingButton(
                        label = ALTER_ADDRESS,
                        onClick = {
                            if (checkAddressUpdateIsNull(address = addressResult)) {
                                observer =
                                    Triple(
                                        first = false,
                                        second = true,
                                        third = NOT_BLANK_OR_EMPTY
                                    )
                            } else {
                                observer =
                                    Triple(first = true, second = false, third = EMPTY_TEXT)
                                viewModel.updateAddressOrder(
                                    orderId = orderResponseVO.id,
                                    addressId = orderResponseVO.address?.id ?: 0,
                                    updateAddress = addressResult
                                )
                            }
                        },
                        isEnabled = observer.first
                    )
                }
            )
            IsErrorMessage(isError = observer.second, observer.third)
            ObserveNetworkStateHandlerAlterAddress(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    onRefresh()
                }
            )
        }
    } else {
        ResourceUnavailable()
    }
}

@Composable
private fun ObserveNetworkStateHandlerAlterAddress(
    viewModel: OpenOrdersViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.updateAddressOrder }
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
            viewModel.resetOpenOrders(reset = ResetOpenOrders.UPDATE_ADDRESS_ORDER)
            onSuccessful()
        }
    )
}

fun checkAddressUpdateIsNull(
    address: AddressRequestDTO
): Boolean {
    return (address.street.isEmpty() || address.number == NUMBER_ZERO || address.district.isEmpty()
            || address.complement.isEmpty())
}
