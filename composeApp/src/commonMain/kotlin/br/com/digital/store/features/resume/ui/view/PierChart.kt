package br.com.digital.store.features.resume.ui.view

import androidx.compose.ui.graphics.Color
import br.com.digital.store.components.strings.StringsUtils.GRAPHIC
import br.com.digital.store.composeapp.generated.resources.Res
import br.com.digital.store.composeapp.generated.resources.money
import br.com.digital.store.features.resume.utils.ResumeUtils.RESUME_COMPLETED
import org.jetbrains.compose.resources.DrawableResource

data class PierChart(
    val graphic: Graphic? = null,
    val resume: ResumePieChart? = null
)

data class Graphic(
    val title: String = GRAPHIC,
    val graphic: String = "",
    val details: String = "",
    val information: List<InformationPieChat>? = null,
    val total: Long = 9
)

data class InformationPieChat(
    val label: String = "",
    val value: Long = 0,
    val color: Color = Color.Unspecified
)

data class ResumePieChart(
    val title: String = RESUME_COMPLETED,
    val details: List<DetailsPieChart>? = null
)

data class DetailsPieChart(
    val label: String = "",
    val icon: DrawableResource = Res.drawable.money,
    val value: String = ""
)
