package br.com.digital.store.ui.view.order.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.digital.store.components.strings.StringsUtils.SELECT_ITEMS
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IsErrorMessage
import br.com.digital.store.components.ui.LoadingButton
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.features.networking.resources.reloadViewModels
import br.com.digital.store.features.order.data.dto.ObjectRequestDTO
import br.com.digital.store.features.order.ui.viewmodel.OrderViewModel
import br.com.digital.store.features.order.ui.viewmodel.ResetOrder
import br.com.digital.store.features.order.utils.OrderUtils.ADD_MORE_ITEMS_ORDER
import br.com.digital.store.features.order.utils.OrderUtils.NO_SELECTED_ITEMS
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun IncrementMoreObjectsOrderScreen(
    orderId: Long,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    val viewModel: OrderViewModel = getKoin().get()
    val objectsToSave = remember { mutableStateListOf<ObjectRequestDTO>() }
    var onItemSelected: Pair<Boolean, ObjectRequestDTO> by remember {
        mutableStateOf(value = Pair(first = false, second = ObjectRequestDTO()))
    }
    var verifyObjects: Boolean by remember { mutableStateOf(value = false) }
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    Description(description = SELECT_ITEMS)
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
        isError = observer.second,
        verifyObjects = verifyObjects,
        onItemSelected = {
            if (objectsToSave.contains(element = it)) {
                onItemSelected = Pair(first = true, second = it)
                objectsToSave.remove(element = it)
            }
        }
    )
    LoadingButton(
        label = ADD_MORE_ITEMS_ORDER,
        onClick = {
            if (objectsToSave.isEmpty()) {
                observer = Triple(first = false, second = true, third = NO_SELECTED_ITEMS)
            } else if (objectsToSave.all { it.quantity == 0 }) {
                verifyObjects = true
            } else {
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.incrementMoreObjectsOrder(
                    orderId = orderId,
                    incrementObjects = objectsToSave
                )
            }
        },
        isEnabled = observer.first
    )
    IsErrorMessage(isError = observer.second, observer.third)
    ObserveNetworkStateHandlerIncrementMoreObjectsOrder(
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

@Composable
private fun ObserveNetworkStateHandlerIncrementMoreObjectsOrder(
    viewModel: OrderViewModel,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.incrementMoreObjectsOrder }
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
            viewModel.resetOrder(reset = ResetOrder.INCREMENT_MORE_OBJECTS_ORDER)
            onSuccessful()
        }
    )
}
