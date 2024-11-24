package br.com.digital.store.ui

import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.digital.store.components.ui.Description
import br.com.digital.store.utils.NumbersUtils

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    goToBackScreen: () -> Unit = {}
) {
    BodyPage(
      body = {
          var count = 0
          Button(
              onClick = {
                  count++
                  if (count == NumbersUtils.NUMBER_ONE) {
                      goToBackScreen()
                  }
              },
              content = {
                  Description(description = "Logout")
              }
          )
      }
    )
}