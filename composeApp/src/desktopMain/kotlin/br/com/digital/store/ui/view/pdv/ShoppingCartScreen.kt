package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ADD_PRODUCT
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.ui.ButtonCreate
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.type.TypeItem
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.CLOSE_ORDER
import br.com.digital.store.features.product.ui.view.SelectProducts
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.components.ui.SelectPrint
import br.com.digital.store.ui.view.components.utils.ThermalPrinter
import br.com.digital.store.ui.view.order.ui.BodyCard
import br.com.digital.store.ui.view.order.ui.ClosedOrderDialog
import br.com.digital.store.ui.view.order.ui.ItemObject
import br.com.digital.store.ui.view.order.utils.formatOrderToPrint
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import org.koin.mp.KoinPlatform.getKoin
import java.io.ByteArrayInputStream

@Composable
fun ShoppingCartScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var addProduct: Boolean by remember { mutableStateOf(value = false) }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
    var selectTypePayment: Boolean by remember { mutableStateOf(value = false) }
    var openPrint: Boolean by remember { mutableStateOf(value = false) }
    var getOrder by remember { mutableStateOf(value = OrderResponseVO()) }
    val thermalPrinter = ThermalPrinter()
    val viewModel: OrderViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        ItemObject(
            body = {
                ButtonCreate(
                    label = ADD_PRODUCT,
                    onItemSelected = {
                        addProduct = true
                    }
                )
                BodyCard(
                    objectsToSave = objectsToSave,
                    title = false,
                    verifyObjects = verifyObjects,
                    isError = observer.second,
                    onItemSelected = {
                        if (objectsToSave.contains(element = it)) {
                            objectsToSave.remove(element = it)
                        }
                    }
                )
            }
        )
        LoadingButton(
            label = CLOSE_ORDER,
            onClick = {
                selectTypePayment = true
            },
            isEnabled = observer.first
        )
        IsErrorMessage(isError = observer.second, observer.third)
    }
    if (addProduct) {
        SelectProducts(
            onDismissRequest = {
                addProduct = false
            },
            onConfirmation = {
                it.forEach { product ->
                    val productSelected = ObjectRequestDTO(
                        name = product.name,
                        identifier = product.id,
                        actualQuantity = product.quantity,
                        quantity = 0,
                        type = TypeItem.PRODUCT
                    )
                    if (!objectsToSave.contains(element = productSelected)) {
                        objectsToSave.add(productSelected)
                    }
                    verifyObjects = false
                    addProduct = false
                }
            }
        )
    }
    if (selectTypePayment) {
        ClosedOrderDialog(
            onDismissRequest = {
                selectTypePayment = false
            },
            onConfirmation = {
                selectTypePayment = false
                if (objectsToSave.isEmpty()) {
                    observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
                } else {
                    verifyObjects = objectsToSave.any { it.quantity == 0 }
                    if (verifyObjects) {
                        observer =
                            Triple(first = false, second = true, third = NUMBER_EQUALS_ZERO)
                    } else {
                        observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                        viewModel.createOrder(
                            order = OrderRequestDTO(
                                type = TypeOrder.SHOPPING_CART,
                                objects = objectsToSave.toList(),
                                payment = it
                            )
                        )
                    }
                }
            }
        )
    }
    ObserveNetworkStateHandlerShoppingCart(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccessful = {
            observer = Triple(first = false, second = false, third = EMPTY_TEXT)
            openPrint = true
            if (it != null) {
                getOrder = it
            }
        }
    )
    val textToDisplay = formatOrderToPrint(order = getOrder)
    val inputStream = ByteArrayInputStream(textToDisplay.toByteArray(Charsets.UTF_8))
    if (openPrint) {
        SelectPrint(
            onDismissRequest = {
                openPrint = false
                onRefresh()
            },
            onConfirmation = { printerName ->
                openPrint = false
                thermalPrinter.printInputStream(
                    inputStream = inputStream,
                    printerName = printerName
                )
                objectsToSave.clear()
                onRefresh()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerShoppingCart(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: (OrderResponseVO?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<OrderResponseVO> by remember { viewModel.createOrder }
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
            viewModel.resetOrder(reset = ResetOrder.CREATE_ORDER)
            onSuccessful(it.result)
        }
    )
}
