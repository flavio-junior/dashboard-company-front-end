package br.com.digital.store.features.order.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.components.strings.StringsUtils.ORDER
import br.com.digital.store.components.ui.Title
import br.com.digital.store.theme.SpaceSize.spaceSize2
import br.com.digital.store.utils.NumbersUtils.NUMBER_TWO

@Composable
fun OrderList(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .drawBehind {
                val strokeWidth = spaceSize2.toPx()
                drawLine(
                    color = Color.Black,
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = size.height),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = Color.Black,
                    start = Offset(x = size.width - strokeWidth / NUMBER_TWO, y = 0f),
                    end = Offset(x = size.width - strokeWidth / NUMBER_TWO, y = size.height),
                    strokeWidth = strokeWidth
                )
            }
            .fillMaxHeight()
    ) {
        Title(title = ORDER, textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth())
    }
}
