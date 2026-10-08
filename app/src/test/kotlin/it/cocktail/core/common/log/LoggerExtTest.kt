package it.cocktail.core.common.log

import io.kotest.core.spec.style.ShouldSpec
import io.mockk.mockk
import io.mockk.verify

class LoggerExtTest :
    ShouldSpec({
        val logger = mockk<Logger>(relaxed = true)

        should("delegate to logger.error with exception message when present") {
            val exception = IllegalStateException("Failed")
            logger.exception("tag", exception)
            verify(exactly = 1) { logger.error("tag", "Failed", exception) }
        }

        should("delegate to logger.error with fallback message when message is null") {
            val exception = RuntimeException(null as String?)
            logger.exception("tag", exception)
            verify(exactly = 1) { logger.error("tag", "Exception encountered", exception) }
        }
    })
