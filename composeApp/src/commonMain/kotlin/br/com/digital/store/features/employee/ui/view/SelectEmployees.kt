package br.com.digital.store.features.employee.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import br.com.digital.store.components.strings.StringsUtils.ADD_EMPLOYEE
import br.com.digital.store.components.strings.StringsUtils.CANCEL
import br.com.digital.store.components.strings.StringsUtils.CONFIRM
import br.com.digital.store.components.strings.StringsUtils.NO_EMPLOYEE_SELECTED
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ObserveNetworkStateHandler
import br.com.digital.store.components.ui.Search
import br.com.digital.store.components.ui.SimpleButton
import br.com.digital.store.components.ui.Tag
import br.com.digital.store.components.ui.Title
import br.com.digital.store.features.employee.data.dto.EmployeeResponseDTO
import br.com.digital.store.features.employee.ui.viewModel.EmployeeViewModel
import br.com.digital.store.features.networking.resources.AlternativesRoutes
import br.com.digital.store.features.networking.resources.ObserveNetworkStateHandler
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun FindEmployeeByName(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (EmployeeResponseDTO) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var observer: Triple<Boolean, Boolean, String> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val viewModel: EmployeeViewModel = getKoin().get()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .onBorder(
                    onClick = {},
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2,
                    color = Themes.colors.primary
                )
                .background(color = Themes.colors.background)
                .size(size = Themes.size.spaceSize500)
                .padding(all = Themes.size.spaceSize16),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var name: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            val employees = remember { mutableStateListOf<EmployeeResponseDTO>() }
            var employeeSelected by remember { mutableStateOf(value = EmployeeResponseDTO()) }
            Title(title = ADD_EMPLOYEE)
            Search(
                value = name,
                onValueChange = { name = it },
                isError = observer.second,
                message = observer.third,
                onGo = {
                    viewModel.findEmployeeByName(name = name)
                }
            )
            FindEmployeeByName(
                employees = employees,
                employeeSelected = employeeSelected,
                onResult = {
                    employeeSelected = it
                }
            )
            Description(description = employeeSelected.name ?: EMPTY_TEXT)
            FooterSelectEmployees(
                onDismissRequest = {
                    viewModel.resetEmployee()
                    onDismissRequest()
                },
                onConfirmation = {
                    if (employeeSelected.id > 0) {
                        viewModel.resetEmployee()
                        onConfirmation(employeeSelected)
                    } else {
                        observer =
                            Triple(first = false, second = true, third = NO_EMPLOYEE_SELECTED)
                    }
                }
            )
            ObserveNetworkStateHandlerFindEmployeeByName(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToAlternativeRoutes = goToAlternativeRoutes,
                onSuccessful = {
                    observer = Triple(first = false, second = false, third = EMPTY_TEXT)
                    employees.clear()
                    employees.addAll(it)
                }
            )
        }
    }
}

@Composable
private fun FindEmployeeByName(
    employees: List<EmployeeResponseDTO>,
    employeeSelected: EmployeeResponseDTO,
    onResult: (EmployeeResponseDTO) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        state = scrollState,
        modifier = Modifier
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(-delta)
                    }
                }
            )
    ) {
        items(employees) { employee ->
            Tag(
                text = employee.name ?: EMPTY_TEXT,
                value = employee,
                enabled = employees.contains(employeeSelected),
                onCheck = { isChecked ->
                    if (isChecked) {
                        onResult(employee)
                    }
                }
            )
        }
    }
}

@Composable
private fun FooterSelectEmployees(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
    ) {
        SimpleButton(
            onClick = onDismissRequest,
            label = CANCEL,
            background = Themes.colors.error,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
        SimpleButton(
            onClick = onConfirmation,
            label = CONFIRM,
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerFindEmployeeByName(
    viewModel: EmployeeViewModel,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccessful: (List<EmployeeResponseDTO>) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<List<EmployeeResponseDTO>> by remember { viewModel.findEmployeeByName }
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
            it.result?.let { result -> onSuccessful(result) }
        }
    )
}
