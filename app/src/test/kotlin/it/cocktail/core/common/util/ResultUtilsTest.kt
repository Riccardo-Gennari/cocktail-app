package it.cocktail.core.common.util

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CancellationException

class ResultUtilsTest :
    FunSpec({
        test("returns Result.success when block completes successfully") {
            val result = runCatchingCancelable { "success" }
            result.isSuccess shouldBe true
            result.getOrNull() shouldBe "success"
        }

        test("returns Result.failure when block throws standard exception") {
            val exception = IllegalStateException("Something failed")
            val result = runCatchingCancelable { throw exception }
            result.isFailure shouldBe true
            result.exceptionOrNull() shouldBe exception
        }

        test("re-throws CancellationException without catching it in Result.failure") {
            shouldThrow<CancellationException> {
                runCatchingCancelable {
                    throw CancellationException("Job was cancelled")
                }
            }
        }
    })
