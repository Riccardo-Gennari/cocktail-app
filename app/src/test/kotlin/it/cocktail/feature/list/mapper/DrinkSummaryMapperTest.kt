package it.cocktail.feature.list.mapper

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import it.cocktail.core.ui.model.ImageUri
import it.cocktail.domain.model.DrinkSummary
import it.cocktail.feature.list.model.DrinkSummaryUiModel

class DrinkSummaryMapperTest :
    BehaviorSpec(
        {
            Given("a DrinkSummary") {
                When("toUi is called") {
                    val summary = DrinkSummary(id = 1, name = "name 1", imageUri = "image 1")

                    Then("it should map all fields to DrinkSummaryUiModel") {
                        val uiModel = summary.toUi()
                        uiModel shouldBe DrinkSummaryUiModel(
                            id = summary.id,
                            name = summary.name,
                            imageUri = ImageUri(summary.imageUri),
                        )
                    }
                }
            }
        },
    )
