package it.cocktail.core.ui.component

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Wraps [content] with [PullToRefreshBox], displaying a non-blocking pull-to-refresh indicator without locking the UI.
 *
 * @param isRefreshing Whether the pull-to-refresh indicator should be displayed.
 * @param onRefresh Callback invoked when a pull-to-refresh gesture is performed.
 */
@Composable
fun PullToRefreshBoxWithLoadingOverlay(
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = refreshState,
        indicator = {
            PullToRefreshDefaults.Indicator(
                isRefreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        },
        content = content,
    )
}
