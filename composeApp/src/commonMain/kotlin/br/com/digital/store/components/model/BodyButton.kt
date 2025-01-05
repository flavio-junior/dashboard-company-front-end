package br.com.digital.store.components.model

import org.jetbrains.compose.resources.DrawableResource

enum class TypeNavigation {
    NAVIGATION,
    TAB
}

data class BodyButton(
    val icon: DrawableResource,
    val label: String,
    val navigation: TypeNavigation = TypeNavigation.TAB,
    val count: Int
)
