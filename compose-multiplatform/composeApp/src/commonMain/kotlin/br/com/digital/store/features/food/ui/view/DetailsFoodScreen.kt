package br.com.digital.store.features.food.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.strings.StringsUtils.ID
import br.com.digital.store.components.strings.StringsUtils.NAME
import br.com.digital.store.components.strings.StringsUtils.PRICE
import br.com.digital.store.components.ui.Description
import br.com.digital.store.components.ui.ResourceUnavailable
import br.com.digital.store.components.ui.TextField
import br.com.digital.store.features.category.ui.view.ListCategoriesAvailableResponseVO
import br.com.digital.store.features.food.data.vo.FoodResponseVO
import br.com.digital.store.features.food.utils.FoodUtils.DETAILS_FOOD
import br.com.digital.store.features.food.utils.FoodUtils.UPDATE_FOOD
import br.com.digital.store.features.networking.utils.AlternativesRoutes
import br.com.digital.store.theme.Themes
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE
import br.com.digital.store.utils.CommonUtils.WEIGHT_SIZE_2
import br.com.digital.store.utils.NumbersUtils.NUMBER_ZERO
import br.com.digital.store.utils.formatterMaskToMoney

@Composable
fun DetailsFoodScreen(
    food: FoodResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    if (food.id > NUMBER_ZERO) {
        DetailsFoodBody(
            food = food,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    } else {
        ResourceUnavailable()
    }
}

@Composable
fun DetailsFoodBody(
    food: FoodResponseVO,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(description = "${DETAILS_FOOD}:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = food.id.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = NAME,
                value = food.name,
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
            TextField(
                enabled = false,
                label = PRICE,
                value = formatterMaskToMoney(price = food.price),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
        }
        ListCategoriesAvailableResponseVO(categories = food.categories)
        Description(description = UPDATE_FOOD)
        UpdateFood(
            id = food.id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        ) {
            UpdatePriceFood(
                id = food.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                modifier = Modifier
                    .weight(weight = WEIGHT_SIZE_2),
                onRefresh = onRefresh
            )
        }
    }
}
