package br.com.digital.store.features.food.ui.view

import androidx.compose.runtime.Composable
import br.com.digital.store.features.food.data.vo.FoodResponseVO
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.utils.NumbersUtils

@Composable
fun FoodTabMain(
    index: Int,
    foodResponseVO: FoodResponseVO = FoodResponseVO(),
    onItemSelected: (FoodResponseVO) -> Unit = {},
    onToCreateNewFood: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: (Int) -> Unit = {}
) {
    when (index) {
        NumbersUtils.NUMBER_ZERO -> ListFoodsScreen(
            onItemSelected = onItemSelected,
            onToCreateNewFood = onToCreateNewFood,
            goToAlternativeRoutes = goToAlternativeRoutes
        )

        NumbersUtils.NUMBER_ONE -> DetailsFoodScreen(
            food = foodResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_ONE)
            }
        )

        NumbersUtils.NUMBER_TWO -> CreateNewFoodScreen(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = {
                onRefresh(NumbersUtils.NUMBER_TWO)
            }
        )
    }
}
