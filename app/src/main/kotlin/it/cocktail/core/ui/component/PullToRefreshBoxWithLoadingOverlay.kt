package it.cocktail.core.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Wraps [content] with [PullToRefreshBox], and shows a loading indicator and scrim overlay when [isLoading] is true.
 */
@Composable
fun PullToRefreshBoxWithLoadingOverlay(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isLoading,
        onRefresh = onRefresh,
        state = refreshState,
        indicator = {
            AnimatedVisibility(isLoading, enter = fadeIn(), exit = fadeOut()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.scrim.copy(alpha = 0.15f)),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(colors.surface, CircleShape)
                            .padding(12.dp),
                    )
                }
            }

            if (!isLoading) {
                PullToRefreshDefaults.Indicator(
                    isRefreshing = false,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        },
        content = content,
    )
}
