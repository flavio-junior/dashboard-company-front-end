package br.com.digital.store.ui.view.pdv

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
import br.com.digital.store.components.strings.StringsUtils.CREATE_NEW_ORDER
import br.com.digital.store.components.strings.StringsUtils.NOT_BLANK_OR_EMPTY
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.data.dto.OrderRequestDTO
import br.com.digital.store.features.order.data.vo.OrderResponseVO
import br.com.digital.store.features.order.domain.type.TypeOrder
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.pdv.utils.PdvUtils.SELECT_ORDER
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.order.ui.BodyCard
import br.com.digital.store.ui.view.order.ui.SelectObjects
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.NUMBER_EQUALS_ZERO
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun CreateOrderScreen(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var onItemSelected: Pair<Boolean, ObjectRequestDTO> by remember {
        mutableStateOf(value = Pair(first = false, second = ObjectRequestDTO()))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = SELECT_ORDER)
        SelectObjects(
            onItemSelected = onItemSelected,
            objectsSelected = {
                it.forEach { objectSelected ->
                    if (!objectsToSave.contains(objectSelected)) {
                        objectsToSave.add(objectSelected)
                    }
                }
            }
        )
        BodyCard(
            objectsToSave = objectsToSave,
            title = false,
            verifyObjects = verifyObjects,
            isError = observer.second,
            onItemSelected = {
                if (objectsToSave.contains(element = it)) {
                    onItemSelected = Pair(first = true, second = it)
                    objectsToSave.remove(element = it)
                }
            }
        )
        LoadingButton(
            label = CREATE_NEW_ORDER,
            onClick = {
                if (checkBodyOrderIsNull(objectSelected = objectsToSave)
                ) {
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
                                type = TypeOrder.ORDER,
                                objects = objectsToSave.toList()
                            )
                        )
                    }
                }
            },
            isEnabled = observer.first
        )
        IsErrorMessage(isError = observer.second, observer.third)
        ObserveNetworkStateHandlerCreateNewOrder(
            viewModel = viewModel,
            onError = {
                observer = it
            },
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccessful = {
                objectsToSave.clear()
                onRefresh()
            }
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerCreateNewOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
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
            onSuccessful()
        }
    )
}

fun checkBodyOrderIsNull(
    objectSelected: List<ObjectRequestDTO>? = null
): Boolean {
    return (objectSelected.isNullOrEmpty())
}
