package it.cocktail.core.common.log

import android.util.Log
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify

class DefaultLoggerTest :
    ShouldSpec({
        isolationMode = IsolationMode.SingleInstance

        val logger = DefaultLogger()
        val exception = RuntimeException("Error")
        val expectedTag = "${DefaultLogger.APP_TAG}/tag"

        beforeSpec {
            mockkStatic(Log::class)
            every { Log.d(any(), any(), any()) } returns 0
            every { Log.e(any(), any(), any()) } returns 0
            every { Log.i(any(), any(), any()) } returns 0
            every { Log.w(any(), any(), any()) } returns 0
        }

        afterSpec { unmockkStatic(Log::class) }

        should("delegate debug logs to Log.d") {
            logger.debug("tag", "msg", exception)
            verify(exactly = 1) { Log.d(expectedTag, "msg", exception) }
        }

        should("delegate error logs to Log.e") {
            logger.error("tag", "msg", exception)
            verify(exactly = 1) { Log.e(expectedTag, "msg", exception) }
        }

        should("delegate info logs to Log.i") {
            logger.info("tag", "msg", exception)
            verify(exactly = 1) { Log.i(expectedTag, "msg", exception) }
        }

        should("delegate warning logs to Log.w") {
            logger.warning("tag", "msg", exception)
            verify(exactly = 1) { Log.w(expectedTag, "msg", exception) }
        }
    })
