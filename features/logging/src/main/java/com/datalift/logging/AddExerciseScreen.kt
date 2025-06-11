package com.datalift.logging

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.components.DataliftNumberPicker
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.ExerciseSet
import com.datalift.model.data.Workout
import com.datalift.ui.WorkoutPreviewParameterProvider

/*
    I want to add functionality where you can live-search exercises by name.

    If there are muscle groups filtered, it will only allow you to add exercises for those muscle groups.
    If there are no muscle groups filtered, it will allow you to add any exercise.

    It will give you a popup of exercises that match the search.
    You can then select an exercise from the popup.
    You can then add the exercise to the workout.

    You can then add reps and sets to the exercise.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddExerciseScreen(
    exerciseTitle: String,
    navBack: () -> Unit,
    saveExercise: () -> Unit,
    addSet: () -> Unit,
    removeSet: (ExerciseSet) -> Unit,
    sets: List<ExerciseSet>,
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = exerciseTitle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navBack) {
                        Icon(
                            imageVector = DataliftIcons.NavigateUp,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                enabled = sets.isNotEmpty(),
                onClick = { saveExercise() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ){
                Text("Save Exercise")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SetsList(
                addSet = addSet,
                removeSet = removeSet,
                sets = sets
            )
        }
    }
}

@Composable
private fun SetsList(
    addSet: () -> Unit,
    removeSet: (ExerciseSet) -> Unit,
    sets: List<ExerciseSet>,
){
    var selectedSetIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn {
        itemsIndexed(sets) { index ,set ->
            CollapsableSet(
                collapsed = selectedSetIndex != index,
                setCollapsed = {collapsed ->
                    (if(collapsed) null else index).also {
                        selectedSetIndex = it
                    }
                },
                setNumber = index+1,
                reps = set.reps,
                changeReps = {},
                deleteSet = { removeSet(set) },
            )
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                IconButton(
                    onClick = addSet,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = DataliftIcons.Add,
                        contentDescription = "Create Set"
                    )
                }
            }
        }
    }
}

@Composable
private fun CollapsableSet(
    collapsed: Boolean,
    setCollapsed: (Boolean) -> Unit,
    setNumber: Int,
    reps: Long,
    changeReps: (Long) -> Unit,
    deleteSet: () -> Unit
){

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .clickable {
                    setCollapsed(!collapsed)
                },
        ) {
            Text(
                text = "Set #$setNumber",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = { setCollapsed(!collapsed) },
            ) {
                Icon(
                    imageVector =
                        if(collapsed) { DataliftIcons.Collapsed } else DataliftIcons.Expanded,
                    contentDescription = "Expand/Dismiss Workout"
                )
            }
        }
        if(!collapsed) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Reps",
                    style = MaterialTheme.typography.headlineSmall
                )
                DataliftNumberPicker(
                    value = reps,
                    onValueChange = changeReps,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Weight (lbs)",
                    style = MaterialTheme.typography.headlineSmall
                )
                // TODO: Change from hardcoded value to a variable
                // TODO: Add weight picker
                DataliftNumberPicker(
                    value = 10,
                    onValueChange = {},
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(onClick = deleteSet) {
                        Icon(
                            imageVector = DataliftIcons.Trash,
                            contentDescription = "Delete Set"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CollapsableSetPreview(){
    DataliftTheme {
        CollapsableSet(
            collapsed = false,
            setCollapsed = {},
            setNumber = 1,
            reps = 10,
            changeReps = {},
            deleteSet = {},
        )
    }
}

@Preview
@Composable
private fun SetsListPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
){
    DataliftTheme {
        SetsList(
            sets = workouts[0].exercises[0].sets,
            addSet = {},
            removeSet = {},
        )
    }
}

@Preview
@Composable
private fun AddExerciseScreenPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
){
    DataliftTheme {
        AddExerciseScreen(
            exerciseTitle = workouts[0].exercises[0].name,
            navBack = {},
            addSet = {},
            removeSet = {},
            sets = workouts[0].exercises[0].sets,
            saveExercise = {}
        )
    }
}