package it.cocktail.core.common.log

import android.util.Log
import javax.inject.Inject

/**
 * Implementation of [Logger] that delegates to [Log].
 *
 * Prefixes each tag with a global app tag.
 */
class DefaultLogger
    @Inject
    constructor() : Logger {
        override fun debug(
            tag: String,
            message: String,
            exception: Throwable?,
        ) {
            Log.d("$APP_TAG/$tag", message, exception)
        }

        override fun error(
            tag: String,
            message: String,
            exception: Throwable?,
        ) {
            Log.e("$APP_TAG/$tag", message, exception)
        }

        override fun info(
            tag: String,
            message: String,
            exception: Throwable?,
        ) {
            Log.i("$APP_TAG/$tag", message, exception)
        }

        override fun warning(
            tag: String,
            message: String,
            exception: Throwable?,
        ) {
            Log.w("$APP_TAG/$tag", message, exception)
        }

        companion object {
            const val APP_TAG = "CKTL"
        }
    }
