package br.com.digital.store.features.food.ui.view

import br.com.digital.store.features.food.utils.FoodUtils.CREATE_FOOD
import br.com.digital.store.features.food.utils.FoodUtils.DETAILS_FOOD
import br.com.digital.store.features.food.utils.FoodUtils.LIST_FOODS

enum class ItemsFood(val text: String) {
    ListFoods(text = LIST_FOODS),
    DetailsFood(text = DETAILS_FOOD),
    CreateFood(text = CREATE_FOOD)
}
