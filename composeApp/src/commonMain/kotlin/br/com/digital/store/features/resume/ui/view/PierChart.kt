package br.com.digital.store.features.resume.ui.view

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

data class PierChart(
    val graphic: Graphic,
    val resume: ResumePieChart
)

data class Graphic(
    val title: String,
    val graphic: String,
    val details: String,
    val information: List<InformationPieChat>? = null,
    val total: Long
)

data class InformationPieChat(
    val label: String,
    val value: Long,
    val color: Color
)

data class ResumePieChart(
    val title: String,
    val details: List<DetailsPieChart>
)

data class DetailsPieChart(
    val label: String,
    val icon: DrawableResource,
    val value: String
)
