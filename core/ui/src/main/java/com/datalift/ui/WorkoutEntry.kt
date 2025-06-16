package com.datalift.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.components.IconButtonDisplayToggle
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.Workout
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WorkoutEntry(
    workout: Workout,
    onWorkoutClick: () -> Unit,
    onWorkoutDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable(
                onClick = { onWorkoutClick() }
            )
    ) {
        // TODO: Add a Icon Box to signify what type of workout it was
        Column(modifier = Modifier.weight(1f)) {
            WorkoutEntryTitle(workout.workoutName)
            WorkoutDateAndExercises(
                date = workout.date,
                exerciseCount = workout.getExerciseCount()
            )
        }
        WorkoutDropdownMenuButton(
            deleteWorkout = onWorkoutDelete
        )
    }
}

@Composable
fun WorkoutDropdownMenuButton(
    deleteWorkout: () -> Unit,
){
    var dropdownVisible by remember { mutableStateOf(false) }

    IconButtonDisplayToggle(
        icon = DataliftIcons.More,
        contentDescription = "Open workout options",
        displayVisible = dropdownVisible,
        toggleDisplay = { dropdownVisible = !dropdownVisible },
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            DropdownMenu(
                expanded = dropdownVisible,
                onDismissRequest = { dropdownVisible = false}
            ) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        deleteWorkout()
                        dropdownVisible = false
                    }
                )
            }
        }
    }
}

@Composable
fun WorkoutEntryTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun WorkoutDateAndExercises(
    date: Instant,
    exerciseCount: Int,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
    ) {
        WorkoutDate(date)
        Spacer(modifier = Modifier.width(4.dp))
        DotDivider()
        Spacer(modifier = Modifier.width(4.dp))
        WorkoutExerciseCount(exerciseCount)
    }
}

@Composable
fun WorkoutDate(
    date: Instant
){
    val dateFormatted = dateFormatted(date)
    Text(
        text = dateFormatted,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
private fun dateFormatted(publishTime: Instant): String = DateTimeFormatter
    .ofPattern("MM/dd/yyyy")
    .withLocale(Locale.getDefault())
    .withZone(LocalTimeZone.current.toJavaZoneId())
    .format(publishTime.toJavaInstant())

@Composable
fun DotDivider(){
    Text(
        text = "•",
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun WorkoutExerciseCount(
    exerciseCount: Int
){
    Text(
        text = "$exerciseCount exercises",
        style = MaterialTheme.typography.labelSmall,
    )
}

@Preview("WorkoutEntry")
@Composable
private fun WorkoutEntryPreview(
    @PreviewParameter(WorkoutPreviewParameterProvider::class)
    workout: List<Workout>
){
    CompositionLocalProvider(
        LocalInspectionMode provides true
    ) {
        DataliftTheme {
            Surface {
                WorkoutEntry (
                    workout = workout[0],
                    onWorkoutClick = {},
                    onWorkoutDelete = {}
                )
            }
        }
    }
}