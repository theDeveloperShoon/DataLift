package com.datalift.logging

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.datalift.designsystem.icon.DataliftIcons

@Composable
internal fun ExerciseSearchScreen(
    onBackClick: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchTrigger: (String) -> Unit,
){
    Column {
        ExerciseSearchToolbar(
            onBackClick = onBackClick,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onSearchTrigger = onSearchTrigger,
        )
    }
}

@Composable
private fun ExerciseSearchToolbar(
    onBackClick: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchTrigger: (String) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = DataliftIcons.NavigateUp,
                contentDescription = null,
            )
        }
        ExerciseSearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onSearchTrigger = onSearchTrigger,
        )
    }
}

@Composable
private fun ExerciseSearchTextField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchTrigger: (String) -> Unit,
){
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTrigger(searchQuery)
    }

    TextField(
        value = searchQuery,
        onValueChange = {
            if ("\n" !in it) onSearchQueryChange(it)
        },
        leadingIcon = {
            Icon(
                imageVector = DataliftIcons.Search,
                contentDescription = null,
            )
        },
        trailingIcon = {
            if(searchQuery.isNotEmpty()){
                IconButton(
                    onClick = {
                        onSearchQueryChange("")
                    }
                ) {
                    Icon(
                        imageVector = DataliftIcons.Close,
                        contentDescription = null,
                    )
                }
            }
        },
        placeholder = { Text("Search exercises") },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(searchQuery.isBlank()) return@KeyboardActions
                onSearchExplicitlyTriggered()
            }
        )
    )
}