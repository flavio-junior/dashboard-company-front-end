package br.com.digital.store.ui.view.pdv

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.domain.factory.availableServices
import br.com.digital.store.components.strings.StringsUtils.PDV
import br.com.digital.store.theme.Themes
import br.com.digital.store.ui.view.shared.BodyPage
import br.com.digital.store.ui.view.shared.Services
import br.com.digital.store.utils.CommonUtils
import br.com.digital.store.utils.TypeLayout

@Composable
fun PdvScreen(
    modifier: Modifier = Modifier,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Row {
                Services(
                    label = PDV,
                    services = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen,
                    modifier = modifier.weight(weight = CommonUtils.WEIGHT_SIZE)
                )
                CashRegisterScreen(
                    modifier = modifier
                        .padding(
                            start = Themes.size.spaceSize8,
                            top = Themes.size.spaceSize16,
                            bottom = Themes.size.spaceSize16,
                            end = Themes.size.spaceSize16
                        )
                        .weight(weight = CommonUtils.WEIGHT_SIZE_4)
                )
            }
        }
    )
}
