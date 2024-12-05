package br.com.digital.store.features.food.utils

object FoodUtils {
    const val LIST_FOODS = "Lista de Pratos"
    const val DETAILS_FOOD = "Detalhes do Prato"
    const val UPDATE_FOOD = "Atualizar Prato:"
    const val UPDATE_PRICE_FOOD = "Atualizar preço do Prato"
    const val NEW_PRICE_FOOD = "Novo preço do Prato"
    const val CREATE_FOOD = "Criar Novo Prato"
    const val NAME_FOOD = "Nome do Prato"
    const val NEW_NAME_FOOD = "Novo nome do Prato"
    const val DELETE_FOOD = "Apagar Prato"
}

fun checkBodyFoodIsNull(
    name: String,
    price: Double
): Boolean {
    return name.isEmpty() && price == 0.0
}
