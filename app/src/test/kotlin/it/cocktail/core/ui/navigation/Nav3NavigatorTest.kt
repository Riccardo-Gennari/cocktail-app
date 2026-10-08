package it.cocktail.core.ui.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.ui.detail.navigation.DrinkDetailRoute
import it.cocktail.ui.list.navigation.DrinkListRoute

class Nav3NavigatorTest :
    BehaviorSpec({
        Given("an instance") {

            When("navigateToDetail is called") {
                val backStack = NavBackStack<NavKey>(DrinkListRoute)
                val navigator = Nav3Navigator(backStack)

                navigator.navigateToDetail(123)

                Then("it should add DrinkDetailRoute to backStack") {
                    backStack.last() shouldBe DrinkDetailRoute(drinkId = 123)
                }
            }

            When("navigateBack is called when backStack size is 1") {
                val backStack = NavBackStack<NavKey>(DrinkListRoute)
                val navigator = Nav3Navigator(backStack)

                navigator.navigateBack()

                Then("it should not remove the initial route") {
                    backStack.size shouldBe 1
                    backStack.last() shouldBe DrinkListRoute
                }
            }

            When("navigateBack is called when backStack size is greater than 1") {
                val backStack = NavBackStack(DrinkListRoute, DrinkDetailRoute(drinkId = 123))
                val navigator = Nav3Navigator(backStack)

                navigator.navigateBack()

                Then("it should remove the last route") {
                    backStack.size shouldBe 1
                    backStack.last() shouldBe DrinkListRoute
                }
            }
        }
    })
