package br.com.digital.store.features.resume.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ORDER
import br.com.digital.store.components.strings.StringsUtils.ORDERS
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.IconDefault
import br.com.digital.store.components.ui.SubTitle
import br.com.digital.store.components.ui.Title
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.EMPTY_TEXT
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.NumbersUtils
import br.com.digital.store.utils.onBorder
import br.com.digital.store.utils.onClickable
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun DetailsAnalise(
    modifier: Modifier = Modifier,
    graphic: Graphic? = null,
    resume: ResumePieChart? = null,
    onItemSelect: (Pair<Boolean, String>) -> Unit = {}
) {
    Column(modifier = modifier) {
        Title(title = graphic?.details ?: EMPTY_TEXT)
        BodyAnalise(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            information = graphic?.information,
            onItemSelect = onItemSelect
        )
        Title(title = resume?.title ?: EMPTY_TEXT)
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize8))
        DetailsCompletedAnalise(
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            details = resume?.details
        )
    }
}

@Composable
fun BodyAnalise(
    modifier: Modifier = Modifier,
    information: List<InformationPieChat>? = null,
    onItemSelect: (Pair<Boolean, String>) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.Center
    ) {
        information?.forEach {
            DetailsPieChartItem(information = it, onItemSelect = onItemSelect)
            Spacer(modifier = Modifier.height(height = Themes.size.spaceSize8))
        }
    }
}

@Composable
fun DetailsPieChartItem(
    information: InformationPieChat,
    onItemSelect: (Pair<Boolean, String>) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
            .onClickable {
                onItemSelect(Pair(true, information.label))
            }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .onBorder(
                    onClick = {
                        onItemSelect(Pair(true, information.label))
                    },
                    spaceSize = Themes.size.spaceSize12,
                    width = Themes.size.spaceSize2,
                    color = information.color
                )
                .background(color = information.color)
                .size(size = Themes.size.spaceSize48)
        )
        Column(
            modifier = Modifier
                .onClickable {
                    onItemSelect(Pair(true, information.label))
                }
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
        ) {
            SubTitle(
                subTitle = information.label,
                modifier = Modifier.onClickable {
                    onItemSelect(Pair(true, information.label))
                }
            )
            Description(
                description = if (information.value > NumbersUtils.NUMBER_ONE) {
                    "${information.value} $ORDERS"
                } else {
                    "${information.value} $ORDER"
                },
                modifier = Modifier.onClickable {
                    onItemSelect(Pair(true, information.label))
                }
            )
        }
    }
}

@Composable
fun DetailsCompletedAnalise(
    modifier: Modifier = Modifier,
    details: List<DetailsPieChart>? = null
) {
    Column(
        modifier = modifier.padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        details?.forEach {
            LabelWithInfoAnalise(
                icon = it.icon,
                label = it.label,
                value = it.value
            )
        }
    }
}

@Composable
fun LabelWithInfoAnalise(
    label: String,
    value: String,
    icon: DrawableResource
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            icon = icon,
            size = Themes.size.spaceSize48
        )
        Spacer(modifier = Modifier.width(width = Themes.size.spaceSize8))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        ) {
            SubTitle(subTitle = label)
            Description(description = value)
        }
    }
}