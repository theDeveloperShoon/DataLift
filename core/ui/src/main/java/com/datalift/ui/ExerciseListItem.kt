package com.datalift.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Exercise
import com.datalift.model.data.Workout

fun LazyListScope.exerciseItemList(
    exercises: List<Exercise>,
    selectExercise: (Exercise) -> Unit
){
    items(exercises){ exercise ->
        ExerciseItem(
            exercise = exercise,
            selectExercise = selectExercise,
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun ExerciseItem(
    exercise: Exercise,
    selectExercise: (Exercise) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            enabled = true,
            onClick = { selectExercise(exercise) }
        )
    ) {
        Column(modifier = Modifier.weight(1f)) {
            ExerciseName(name = exercise.name)
            ExerciseRepsAndSets(
                reps = exercise.getRepsCount(),
                sets = exercise.getSetsCount()
            )
        }
        ExpandIcon()
    }
}

@Composable
private fun ExerciseName(name: String){
    Text(
        text = name,
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
private fun ExerciseRepsAndSets(
    reps: Long,
    sets: Int
){
    Text(
        text = "$reps reps in $sets sets",
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun ExpandIcon(){
    Icon(
        imageVector = DataliftIcons.ExpandMore,
        contentDescription = null,
    )
}


@Preview(showBackground = true)
@Composable
private fun ExerciseItemPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
) {
    DataliftTheme {
        ExerciseItem(
            exercise = workouts[0].exercises[0],
            selectExercise = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExerciseListPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workouts: List<Workout>
){
    DataliftTheme {
        LazyColumn {
            exerciseItemList(
                exercises = workouts[0].exercises,
                selectExercise = {}
            )
        }
    }
}