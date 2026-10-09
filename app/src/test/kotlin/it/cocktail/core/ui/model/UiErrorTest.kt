package it.cocktail.core.ui.model

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.HttpRequestTimeoutException
import it.cocktail.core.common.log.logTag
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UiErrorTest :
    BehaviorSpec({
        Given("a throwable") {
            When("mapping to UiError") {
                withData(
                    nameFn = { (exception, expected) -> "${exception::class.logTag()} -> $expected" },
                    UnknownHostException() to UiError.NoConnection,
                    ConnectException() to UiError.NoConnection,
                    SocketException() to UiError.NoConnection,
                    SocketTimeoutException() to UiError.NoConnection,
                    HttpRequestTimeoutException("", 1L) to UiError.NoConnection,
                    IllegalStateException("", UnknownHostException("this one is nested")) to UiError.NoConnection,
                    IOException() to UiError.NoConnection,
                    IllegalStateException() to UiError.Generic,
                    IllegalArgumentException() to UiError.Generic,
                    NullPointerException() to UiError.Generic,
                ) { (throwable, expected) ->
                    throwable.toUiError() shouldBe expected
                }
            }
        }
    })
