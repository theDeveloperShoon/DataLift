package com.datalift.logging

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.datalift.designsystem.components.DataliftLoadingIcon
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.ExerciseResource
import com.datalift.ui.ExerciseResourcePreviewParamterProvider

@Composable
internal fun ExerciseSearchScreen(
    navUp: () -> Unit,
    onSaveExercise: (ExerciseResource) -> Unit,
    exerciseViewModel: ExerciseSearchViewModel = hiltViewModel()
){
    val searchQuery by exerciseViewModel.searchQuery.collectAsStateWithLifecycle()
    val exerciseSearchUiState by exerciseViewModel.searchUiState.collectAsStateWithLifecycle()
    val recentSearchQueriesUiState by exerciseViewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()
    val selectedExercise by exerciseViewModel.selectedExercise.collectAsStateWithLifecycle()

    ExerciseSearchScreen(
        exerciseSearchUiState = exerciseSearchUiState,
        recentSearchQueriesUiState = recentSearchQueriesUiState,
        onBackClick = navUp,
        searchQuery = searchQuery,
        onSelectRecentQuery = {query ->
            exerciseViewModel.updateSearchQuery(query)
            exerciseViewModel.onSearchTrigger(query)
        },
        onSearchQueryChange = exerciseViewModel::updateSearchQuery,
        onSearchTrigger = exerciseViewModel::onSearchTrigger,  // All this is supposed to do is add to recentSearches
        selectedExercise = selectedExercise,
        selectExercise = exerciseViewModel::selectExercise,
        saveExercise = {
            selectedExercise?.let { exercise ->
                onSaveExercise(exercise)
            }
        },
    )
}

@Composable
internal fun ExerciseSearchScreen(
    modifier: Modifier = Modifier,
    exerciseSearchUiState: ExerciseSearchUiState = ExerciseSearchUiState.Loading,
    recentSearchQueriesUiState: RecentSearchQueriesUiState = RecentSearchQueriesUiState.Loading,
    onBackClick: () -> Unit,
    searchQuery: String,
    onSelectRecentQuery: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchTrigger: (String) -> Unit,
    selectedExercise: ExerciseResource?,
    selectExercise: (ExerciseResource) -> Unit,
    saveExercise: () -> Unit
){
    Scaffold(
        bottomBar = {
            Button(
                enabled = selectedExercise != null,
                onClick = { saveExercise() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ){
                Text("Select Exercise")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ExerciseSearchToolbar(
                onBackClick = onBackClick,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onSearchTrigger = onSearchTrigger,
            )
            when(exerciseSearchUiState){
                ExerciseSearchUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ){
                        DataliftLoadingIcon(
                            contentDesc = ""
                        )
                    }
                }

                ExerciseSearchUiState.LoadFailed -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Failed to load search")
                    }
                }

                ExerciseSearchUiState.EmptyQuery -> {
                    if(recentSearchQueriesUiState is RecentSearchQueriesUiState.Success){
                        RecentSearchesBody(
                            recentSearchQueries = recentSearchQueriesUiState.recentSearchQueries
                                .map { it.query },
                            onSelectRecentQuery = onSelectRecentQuery,
                        )
                    }
                }
                ExerciseSearchUiState.SearchNotReady -> SearchNotReadyBody()
                is ExerciseSearchUiState.Success -> {
                    if(exerciseSearchUiState.exercises.isEmpty()){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = "No results found")
                        }
                    } else {
                        SearchResultBody(
                            selectedExercise = selectedExercise,
                            exercises = exerciseSearchUiState.exercises,
                            selectExercise = selectExercise,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
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
        ),
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
private fun RecentSearchesBody(
    recentSearchQueries: List<String>,
    onSelectRecentQuery: (String) -> Unit,
){
    Column {
        Text(
            "Recent Searches",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )
        LazyColumn {
            items(recentSearchQueries){ query ->
                Text(
                    text = query,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onSelectRecentQuery(query)
                        }
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SearchNotReadyBody(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Sorry, we are still processing the search index. Please come back later",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun SearchResultBody(
     selectedExercise: ExerciseResource?,
     exercises: List<ExerciseResource>,
     selectExercise: (ExerciseResource) -> Unit,
     modifier: Modifier = Modifier
){
    LazyColumn(modifier = modifier) {
        items(exercises){ exercise ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (selectedExercise == exercise),
                        onClick = { selectExercise(exercise) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = (selectedExercise == exercise),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = exercise.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExerciseSearchbarPreview(){
    DataliftTheme {
        ExerciseSearchTextField(
            searchQuery = "",
            onSearchQueryChange = {},
            onSearchTrigger = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseSearchbarClearButtonPreview(){
    DataliftTheme {
        ExerciseSearchTextField(
            searchQuery = "Push-ups",
            onSearchQueryChange = {},
            onSearchTrigger = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseToolbarPreview(){
    DataliftTheme {
        ExerciseSearchToolbar(
            onBackClick = {},
            searchQuery = "Push-ups",
            onSearchQueryChange = {},
            onSearchTrigger = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecentSearchesBodyPreview() {
    DataliftTheme {
        RecentSearchesBody(
            recentSearchQueries = listOf("Push-ups", "Pull-ups", "Squats"),
            onSelectRecentQuery = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseSearchScreenPreview(){
    DataliftTheme {
        ExerciseSearchScreen(
            exerciseSearchUiState = ExerciseSearchUiState.EmptyQuery,
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Loading,
            onBackClick = {},
            searchQuery = "",
            onSelectRecentQuery = {},
            onSearchQueryChange = {},
            onSearchTrigger = {},
            selectedExercise = null,
            selectExercise = {},
            saveExercise = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseSearchScreenLoadingPreview(){
    DataliftTheme {
        ExerciseSearchScreen(
            exerciseSearchUiState = ExerciseSearchUiState.Loading,
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Loading,
            onBackClick = {},
            searchQuery = "Push-ups",
            onSelectRecentQuery = {},
            onSearchQueryChange = {},
            onSearchTrigger = {},
            selectedExercise = null,
            selectExercise = {},
            saveExercise = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseSearchScreenLoadingFailedPreview(){
    DataliftTheme {
        ExerciseSearchScreen(
            exerciseSearchUiState = ExerciseSearchUiState.LoadFailed,
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Loading,
            onBackClick = {},
            searchQuery = "Push-ups",
            onSelectRecentQuery = {},
            onSearchQueryChange = {},
            onSearchTrigger = {},
            selectedExercise = null,
            selectExercise = {},
            saveExercise = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseSearchScreenSearchNotReadyPreview(){
    DataliftTheme {
        ExerciseSearchScreen(
            exerciseSearchUiState = ExerciseSearchUiState.SearchNotReady,
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Loading,
            onBackClick = {},
            searchQuery = "Push-ups",
            onSelectRecentQuery = {},
            onSearchQueryChange = {},
            onSearchTrigger = {},
            selectedExercise = null,
            selectExercise = {},
            saveExercise = {},
        )
    }
}

@Preview
@Composable
private fun ExerciseSearchScreenSuccessPreview(
    @PreviewParameter(ExerciseResourcePreviewParamterProvider::class)
    exercises: List<ExerciseResource>
){
    DataliftTheme{
        ExerciseSearchScreen(
            exerciseSearchUiState = ExerciseSearchUiState.Success(exercises),
            recentSearchQueriesUiState = RecentSearchQueriesUiState.Loading,
            onBackClick = {},
            searchQuery = "",
            onSelectRecentQuery = {},
            onSearchQueryChange = {},
            onSearchTrigger = {},
            selectedExercise = exercises[0],
            selectExercise = {},
            saveExercise = {},
        )
    }
}