package it.cocktail.feature.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.cocktail.R
import it.cocktail.feature.list.model.SearchState
import it.cocktail.feature.list.model.SearchType

@Composable
fun DrinkSearchBarSection(
    searchState: SearchState,
    onQueryChange: (String) -> Unit,
    onSearchTypeChange: (SearchType) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TextField(
            value = searchState.query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    when (searchState.type) {
                        SearchType.INGREDIENT -> stringResource(R.string.search_cocktail_ingredient)
                        SearchType.NAME -> stringResource(R.string.search_cocktail_name)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            singleLine = true,
            shape = CircleShape,
            leadingIcon = {
                IconButton(onClick = onSearch) {
                    Icon(
                        painter = painterResource(R.drawable.outline_search_24),
                        contentDescription = stringResource(R.string.search),
                    )
                }
            },
            trailingIcon = {
                if (searchState.query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_clear_24),
                            contentDescription = null,
                        )
                    }
                }
            },
            colors = searchBarColors(),
            keyboardOptions = remember { KeyboardOptions(imeAction = ImeAction.Search) },
            keyboardActions = remember(onSearch, keyboard) {
                KeyboardActions(
                    onSearch = {
                        keyboard?.hide()
                        onSearch()
                    },
                )
            },
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FilterChip(
                selected = searchState.type == SearchType.NAME,
                onClick = { onSearchTypeChange(SearchType.NAME) },
                label = { Text(stringResource(R.string.name)) },
                leadingIcon = chipLeadingIcon(searchState.type == SearchType.NAME),
                shape = CircleShape,
            )

            FilterChip(
                selected = searchState.type == SearchType.INGREDIENT,
                onClick = { onSearchTypeChange(SearchType.INGREDIENT) },
                label = { Text(stringResource(R.string.ingredient)) },
                leadingIcon = chipLeadingIcon(searchState.type == SearchType.INGREDIENT),
                shape = CircleShape,
            )
        }
    }
}

@Composable
private fun searchBarColors(colorScheme: ColorScheme = MaterialTheme.colorScheme) =
    TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedContainerColor = colorScheme.surfaceContainerHigh,
        unfocusedContainerColor = colorScheme.surfaceContainerHigh,
    )

@Composable
private fun chipLeadingIcon(isSelected: Boolean) =
    if (isSelected) {
        @Composable { Icon(painterResource(R.drawable.baseline_done_24), contentDescription = null) }
    } else {
        null
    }

@Preview
@Composable
private fun DrinkSearchBarSectionPreview() {
    DrinkSearchBarSection(
        searchState = SearchState(),
        onQueryChange = {},
        onSearchTypeChange = {},
        onSearch = {},
    )
}
