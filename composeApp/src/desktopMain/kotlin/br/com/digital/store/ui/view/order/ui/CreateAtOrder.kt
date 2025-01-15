package br.com.digital.store.ui.view.order.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.digital.store.components.mask.formatDate
import br.com.digital.store.components.strings.StringsUtils.CREATE_AT
import br.com.digital.store.components.ui.SimpleText

@Composable
fun CreateAtOrder(
    createAt: String
) {
    SimpleText(
        text = "$CREATE_AT ${formatDate(originalDate = createAt)}",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
