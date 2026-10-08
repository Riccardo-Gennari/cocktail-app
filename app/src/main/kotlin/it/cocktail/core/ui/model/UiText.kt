package it.cocktail.core.ui.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@Immutable
sealed interface UiText {
    @Immutable
    data class DynamicString(
        val value: String,
    ) : UiText

    @Immutable
    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any>,
    ) : UiText {
        constructor(
            @StringRes resId: Int,
            vararg args: Any,
        ) : this(
            resId = resId,
            args = args.toList(),
        )
    }

    @Composable
    @Suppress("SpreadOperator")
    fun asString(): String =
        when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *remember(args) { args.toTypedArray() })
        }
}
