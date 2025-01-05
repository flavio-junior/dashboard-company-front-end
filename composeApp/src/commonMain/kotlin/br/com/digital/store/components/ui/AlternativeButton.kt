package br.com.digital.store.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.model.BodyButton
import br.com.digital.store.components.model.TypeNavigation
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.arrow_outward
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.onBorder

@Composable
fun AlternativeButton(
    type: BodyButton,
    goToNextTab: (Int) -> Unit = {},
    goToNextScreen: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .onBorder(
                onClick = {
                    if (type.navigation == TypeNavigation.NAVIGATION) {
                        goToNextScreen()
                    } else {
                        goToNextTab(type.count)
                    }
                },
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .padding(all = Themes.size.spaceSize16)
            .width(width = Themes.size.spaceSize650)
    ) {
        IconDefault(icon = type.icon, size = Themes.size.spaceSize48)
        Title(title = type.label.uppercase(), modifier = Modifier.weight(weight = WEIGHT_SIZE))
        IconDefault(icon = Res.drawable.arrow_outward, size = Themes.size.spaceSize48)
    }
}
